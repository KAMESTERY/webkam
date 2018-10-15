package endpoints

import (
	"github.com/gin-contrib/sessions"
	"github.com/gin-gonic/gin"
	"github.com/utrack/gin-csrf"
	"kamestery.com/models"
	"kamestery.com/utils"
	"net/http"
)

var user_logger = utils.NewLogger("entpointuser")

/////////////// Authentication

func login(c *gin.Context) {
	render(c, gin.H{
		"csrf":  csrf.GetToken(c),
		"title": "Authenticate",
	}, "public/login.html")
}

func login_error(c *gin.Context, flashes []interface{}) {
	render(c, gin.H{
		"csrf":    csrf.GetToken(c),
		"title":   "Authenticate",
		"flashes": flashes,
	}, "public/login.html")
}

func authenticate(c *gin.Context) {

	user_logger.Debugf("LOGGING IN using BACKEND URL: %+v", utils.BackendGQL)

	session := sessions.Default(c)

	var user models.Credentials
	if err := c.ShouldBind(&user); err != nil {
		session.AddFlash("Figure out which Error you Made!") //TODO: Revisit this!
		login_error(c, session.Flashes())
		return
	}

	if validationErrors, err := utils.ValidateStruct(&user); validationErrors != nil || err != nil {
		session.AddFlash("Figure out which Error you Made!") //TODO: Revisit this!
		login_error(c, session.Flashes())
		return
	}

	//TODO: Not Ready for Prime Time Yet
	token := models.Authenticate(user)

	if token == "" {
		session.AddFlash("Could not Log You in!") //TODO: Revisit this!
		login_error(c, session.Flashes())
		return
	}
	user_logger.Debugf("AUTH_TOKEN:::: %s", token)

	session.Set(TOKEN_KAM, token)
	session.Save()

	session.AddFlash("You are now Logged in!")

	c.Redirect(http.StatusFound, "/")
}

/////////////// Registration

func register(c *gin.Context) {
	render(c, gin.H{
		"csrf":  csrf.GetToken(c),
		"title": "Register",
	}, "public/register.html")
}

func register_error(c *gin.Context, flashes []interface{}) {
	render(c, gin.H{
		"csrf":    csrf.GetToken(c),
		"title":   "Register",
		"flashes": flashes,
	}, "public/register.html")
}

func enroll(c *gin.Context) {

	user_logger.Debugf("ENROLLING using BACKEND URL: %+v", utils.BackendGQL)

	session := sessions.Default(c)

	var user models.User
	if err := c.ShouldBind(&user); err != nil {
		session.AddFlash("Figure out which Error you Made!") //TODO: Revisit this!
		register_error(c, session.Flashes())
		return
	}

	if validationErrors, err := utils.ValidateStruct(&user); validationErrors != nil || err != nil {
		session.AddFlash("Figure out which Error you Made!") //TODO: Revisit this!
		register_error(c, session.Flashes())
		return
	}

	//TODO: Not Ready for Prime Time Yet
	ok := models.Enroll(user)

	if !ok {
		session.AddFlash("Could not Enroll You!") //TODO: Revisit this!
		register_error(c, session.Flashes())
		return
	}
	user_logger.Debugf("ENROLLMENT_STATUS:::: %+v", ok)

	c.Redirect(http.StatusFound, "/")
}
