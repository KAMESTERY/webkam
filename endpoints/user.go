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
	email := c.PostForm("email")
	password := c.PostForm("password")
	token := models.Login(email, email, password)
	user_logger.Debugf("AUTH_TOKEN:::: %s", token)
	c.Redirect(http.StatusFound, "/")
}
