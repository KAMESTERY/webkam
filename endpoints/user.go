package endpoints

import (
	"github.com/gin-contrib/sessions"
	"github.com/gin-gonic/gin"
	"gopkg.in/go-playground/validator.v9"
	"kamestery.com/models"
	"kamestery.com/utils"
	"net/http"
)

var user_logger = utils.NewLogger("entpointuser")

/////////////// Authentication

func login(c *gin.Context) {
	render(c, gin.H{
		"title": "Authenticate",
	}, "public/login.html")
}

func login_error(c *gin.Context, validationErrors []validator.FieldError, flashes []interface{}) {
	render(c, gin.H{
		"title":            "Authenticate",
		"validationErrors": validationErrors,
		"flashes":          flashes,
	}, "public/login.html")
}

func authenticate(c *gin.Context) {

	user_logger.Debugf("LOGGING IN using BACKEND URL: %+v", utils.BackendGQL)

	session := sessions.Default(c)

	var user models.Credentials
	if err := c.ShouldBind(&user); err != nil {
		session.AddFlash("Could not Log You in!") //TODO: Revisit this!
		login_error(c, nil, session.Flashes())
		return
	}

	if validationErrors, err := utils.ValidateStruct(&user); validationErrors != nil || err != nil {
		session.AddFlash("Could not Log You in!") //TODO: Revisit this!
		login_error(c, validationErrors, session.Flashes())
		return
	}

	//TODO: Not Ready for Prime Time Yet
	token := models.Authenticate(user)

	if token == "" {
		session.AddFlash("Could not Log You in!") //TODO: Revisit this!
		login_error(c, nil, session.Flashes())
		return
	}

	user_logger.Debugf("AUTH_TOKEN:::: %s", token)

	claims := models.GetClaims(token)

	if !claims.Ok() {
		session.AddFlash("Could not Log You in!") //TODO: Revisit this!
		login_error(c, nil, session.Flashes())
		return
	}

	user_logger.Debugf("CLAIMS:::: %+v", claims)

	session.Set(TOKEN_KAM, token)
	jsonClaims := utils.ToJsonString(claims)
	session.Set(USER_KAM, jsonClaims)
	session.Save()

	session.AddFlash("You are now Logged in!")

	c.Redirect(http.StatusFound, "/")
}

func logout(c *gin.Context) {
	session := sessions.Default(c)
	session.Clear() // Required: Removes all keys
	session.Save()  // Required: Save the changes to the session
	session.AddFlash("You are now Logged Out!")
	c.Redirect(http.StatusFound, "/")
}

/////////////// Registration

func register(c *gin.Context) {
	render(c, gin.H{
		"title": "Register",
	}, "public/register.html")
}

func register_error(c *gin.Context, validationErrors []validator.FieldError, flashes []interface{}) {
	render(c, gin.H{
		"title":            "Register",
		"validationErrors": validationErrors,
		"flashes":          flashes,
	}, "public/register.html")
}

func enroll(c *gin.Context) {

	user_logger.Debugf("ENROLLING using BACKEND URL: %+v", utils.BackendGQL)

	session := sessions.Default(c)

	var user models.User
	if err := c.ShouldBind(&user); err != nil {
		session.AddFlash("Could not Enroll You!") //TODO: Revisit this!
		register_error(c, nil, session.Flashes())
		return
	}

	if validationErrors, err := utils.ValidateStruct(&user); validationErrors != nil || err != nil {
		session.AddFlash("Could not Enroll You!") //TODO: Revisit this!
		register_error(c, validationErrors, session.Flashes())
		return
	}

	//TODO: Not Ready for Prime Time Yet
	ok := models.Enroll(user)

	if !ok {
		session.AddFlash("Could not Enroll You!") //TODO: Revisit this!
		register_error(c, nil, session.Flashes())
		return
	}
	user_logger.Debugf("ENROLLMENT_STATUS:::: %+v", ok)

	c.Redirect(http.StatusFound, "/")
}
