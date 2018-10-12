package endpoints

import (
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

	var user models.User
	if err := c.ShouldBind(&user); err != nil {
		login(c) //TODO: Works but do it better
		return
	}

	if validationErrors, err := utils.ValidateStruct(&user); validationErrors != nil || err != nil {
		login(c) //TODO: Works but do it better
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
