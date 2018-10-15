package endpoints

import (
	"github.com/gin-contrib/sessions"
	"github.com/gin-contrib/sessions/cookie"
	"github.com/gin-gonic/gin"
	"github.com/utrack/gin-csrf"
	"kamestery.com/utils"
)

var middlewares_logger = utils.NewLogger("entpointmiddlewares")

func IncludeCommonMiddlewares(r *gin.Engine) {
	middlewares_logger.Debug("Including Middlewares...")

	store := cookie.NewStore([]byte("secret"))
	r.Use(sessions.Sessions(SESSION_KAM, store))
	r.Use(csrf.Middleware(csrf.Options{
		Secret: "secret123",
		ErrorFunc: func(c *gin.Context) {
			render403(c, gin.H{
				"message": "CSRF token mismatch",
			})
			c.Abort()
		},
	}))

	r.NoRoute(func(c *gin.Context) {
		render404(c, gin.H{
			"message": "Page not found",
		})
		c.Abort()
	})

	r.Use(func(c *gin.Context) {
		defer func(c *gin.Context) {
			if rec := recover(); rec != nil {

				middlewares_logger.Errorf("ERROR:::: %+v", rec)

				render500(c, gin.H{
					"message": "Oops!",
				})
			}
		}(c)
		c.Next()
	})
}
