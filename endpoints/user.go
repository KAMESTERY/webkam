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

func login(c *gin.Context) {
	render(c, gin.H{
		"csrf": csrf.GetToken(c),
		"title": "Login",
	}, "public/login.html")
}

func register(c *gin.Context) {
	render(c, gin.H{
		"csrf": csrf.GetToken(c),
		"title": "Register",
	}, "public/register.html")
}

func authenticate(c *gin.Context) {

	session := sessions.Default(c)

	var user models.User
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
	//token := models.Login(user)
	//
	//if token == "" {
	//	c.Redirect(http.StatusUnprocessableEntity, "/user/login")
	//	return
	//}
	//user_logger.Debugf("AUTH_TOKEN:::: %s", token)

	c.Redirect(http.StatusFound, "/")
}

func login_error(c *gin.Context, flashes []interface{}) {
	render(c, gin.H{
		"csrf": csrf.GetToken(c),
		"title": "Login",
		"flashes": flashes,
	}, "public/login.html")
}

