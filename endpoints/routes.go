package endpoints

import (
	"github.com/gin-gonic/gin"
	"kamestery.com/utils"
)

var routes_logger = utils.NewLogger("entpointroutes")

func InitializeRoutes(r *gin.Engine) {
	routes_logger.Debug("Attaching routes...")
	
	// Request Routing
	r.GET("/appinfo.json", appinfo)
	r.GET("/", home)
	r.GET("/content/:topic/:title", content)
	r.GET("/content-list-topic/:topic", listContentByTopic)
	r.GET("/content-list-tag/:topic/:tag", listContentByTag)

	// User Routes
	user_routes := r.Group("/user")
	{
		user_routes.GET("/login", login)
		user_routes.POST("/login", authenticate)
		user_routes.POST("/logout", logout)
		user_routes.GET("/register", register)
		user_routes.POST("/register", enroll)
	}
}
