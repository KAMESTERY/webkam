package endpoints

import (
	"github.com/gin-contrib/sessions"
	"github.com/gin-gonic/gin"
	"kamestery.com/utils"
	"net/http"
	"os"
)

var (
	public_logger = utils.NewLogger("entpointpublic")
	lastDeployed = func() string {
		lDpl := os.Getenv("LAST_DEPLOYED")
		if len(lDpl) == 0 {
			lDpl = "Mon, 02 Jan 2006 15:04:05 -0700"
		}
		return lDpl
	}
)

func appinfo(c *gin.Context) {
	c.JSON(http.StatusOK, gin.H{
		"message": "pong",
		"lastDeployed": lastDeployed(),
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
