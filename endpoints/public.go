package endpoints

import (
	"github.com/gin-contrib/sessions"
	"kamestery.com/utils"
	"net/http"

	"github.com/gin-gonic/gin"
)

var public_logger = utils.NewLogger("entpointpublic")

func ping(c *gin.Context) {
	c.JSON(http.StatusOK, gin.H{
		"message": "pong",
	})
}

func home(c *gin.Context) {

	session := sessions.Default(c)
	if token := session.Get(TOKEN_KAM); token != nil {
		public_logger.Debugf("AUTH_TOKEN:::: %s", token.(string))
	}

	render(c, gin.H{
		"title": "Kamestery Web App :-)",
		"flashes": session.Flashes(),
	}, "public/home.html")
}

func article(c *gin.Context) {

	session := sessions.Default(c)
	if token := session.Get(TOKEN_KAM); token != nil {
		public_logger.Debugf("AUTH_TOKEN:::: %s", token.(string))
	}

	render(c, gin.H{
		"title": "Article",
		"flashes": session.Flashes(),
	}, "public/article.html")
}
