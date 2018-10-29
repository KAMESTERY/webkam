package endpoints

import (
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
		"LastDeployed": func() string {
			lDpl := os.Getenv("LAST_DEPLOYED")
			if len(lDpl) == 0 {
				lDpl = "Mon, 02 Jan 2006 15:04:05 -0700"
			}
			return lDpl
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
	})
}
