package endpoints

import (
	"context"
	contenu "github.com/KAMESTERY/middlewarekam/content"
	"github.com/gin-contrib/sessions"
	"github.com/gin-gonic/gin"
	"html/template"
	"kamestery.com/models"
	"kamestery.com/utils"
	"os"
	"strings"
	"time"
)

var templating_logger = utils.NewLogger("entpointtemplating")

func AddTemplateFunctions(r *gin.Engine) {
	r.SetFuncMap(template.FuncMap{
		"env": func(envVarName string) (envVarValue string) {
			if envVarValue = os.Getenv(envVarName); len(envVarValue) == 0 {
				envVarValue = "No Found"
			}
			return
		},
		"Now": func(format string) string {
			now := time.Now()
			now_str := now.Format(format)
			return now_str
		},
		"UserKm": func(c *gin.Context) interface{} {
			session := sessions.Default(c)
			if claimsJson := session.Get(USER_KAM); claimsJson != nil {
				templating_logger.Debugf("CLAIMS_JSON:::: %+s", claimsJson)
				var claims models.Claims
				utils.DecodeJson(strings.NewReader(claimsJson.(string)), &claims)
				return claims
			}
			return nil // Return nothing here, otherwise it may seem that user is logged in
		},
		"listLatestContentByTopic": func(topics ...string) (content_list []contenu.Content) {
			contentKamClient := contenu.NewContentKamClient()

			for _, topic := range topics {
				content, err := contentKamClient.Latest(context.Background(), topic, 6)
				if err != nil {
					templating_logger.Warnf("WARNING:::: No content found for topic: %+s", topic)
				}
				content_list = append(content_list, *content)
			}
			return
		},
		"getLatestContentMapByTopic": func(topics [5]string) (content_map map[string]contenu.Content) {
			contentKamClient := contenu.NewContentKamClient()
			content_map = make(map[string]contenu.Content)
			for _, topic := range topics {
				content, err := contentKamClient.Latest(context.Background(), topic, 6)
				if err != nil {
					templating_logger.Warnf("WARNING:::: No content found for topic: %+s", topic)
				}
				content_map[topic] = *content
			}
			return
		},
		"getTopics": func() (topic_list [5]string) {
			topic_list = contenu.TOPICS
			return
		},
		"getTopicSectionId": func(topic string) (topic_section string) {
			topic_section = topic + "-section"
			return
		},
	})
}
