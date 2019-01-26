package endpoints

import (
	"context"
	contenu "github.com/KAMESTERY/middlewarekam/content"
	mutil "github.com/KAMESTERY/middlewarekam/utils"
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

func content(c *gin.Context) {

	session := sessions.Default(c)
	if token := session.Get(TOKEN_KAM); token != nil {
		public_logger.Debugf("AUTH_TOKEN:::: %s", token.(string))
	}

	contentKamClient := contenu.NewContentKamClient()

	topic := mutil.Slugify(c.Param("topic"))
	title := mutil.Slugify(c.Param("title"))
	identifier := mutil.ToNamespace(mutil.Namespace, topic, title)

	content, err := contentKamClient.One(c, identifier)
	if err != nil {
		session.AddFlash("ERROR RETRIEVING CONTENT:::: identifier: %s", identifier)
		register_error(c, nil, session.Flashes())
		return
	}

	render(c, gin.H{
		"flashes": session.Flashes(),
		"documents": content.Documents,
	}, "public/content.html")
}
func listContentByTopic(c *gin.Context) {

	session := sessions.Default(c)
	if token := session.Get(TOKEN_KAM); token != nil {
		public_logger.Debugf("AUTH_TOKEN:::: %s", token.(string))
	}

	contentKamClient := contenu.NewContentKamClient()

	topic := mutil.Slugify(c.Param("topic"))
	content, err := contentKamClient.Latest(c, topic, 0)

	if err != nil {
		session.AddFlash("Could not Enroll You!") //TODO: Revisit this!
		register_error(c, nil, session.Flashes())
		return
	}

	render(c, gin.H{
		"flashes":   session.Flashes(),
		"title": topic,
		"documents": content.Documents,
	}, "public/content-topic-list.html")
}

func listContentByTag(c *gin.Context) {

	contentKamClient := contenu.NewContentKamClient()
	content_map := make(map[string]contenu.Content)

	for _, topic := range contenu.TOPICS {
		content, err := contentKamClient.ByTag(context.Background(), topic, 6, c.Param("tag"))
		if err != nil {
			templating_logger.Warnf("WARNING:::: No content found for topic: %+s", topic)
		}
		content_map[topic] = *content
	}

	render(c, gin.H{
		"contentMap": content_map,
	}, "public/content-tag-list.html")
}