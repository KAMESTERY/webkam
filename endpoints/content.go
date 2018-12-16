package endpoints

import (
	ct "github.com/KAMESTERY/middlewarekam/content"
	"github.com/gin-contrib/sessions"
	"github.com/gin-gonic/gin"
	"kamestery.com/models"
	"kamestery.com/utils"
)


func content(c *gin.Context) {
	user_logger.Debugf("Pulling Content from storage..", utils.BackendGQL)

	session := sessions.Default(c)

	var contentModel ct.Content
	if err := c.ShouldBind(&contentModel); err != nil {
		session.AddFlash("Could not Enroll You!") //TODO: Revisit this!
		register_error(c, nil, session.Flashes())
		return
	}

	userContent := models.GetUserContent(c)
	user_logger.Infof("USER CONTENT:::: %+v", userContent)

	render(c, gin.H{
		"title": "Content",
		"flashes": session.Flashes(),
		"content": userContent,
	}, "public/content.html")
}