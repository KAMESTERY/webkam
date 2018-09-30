package endpoints

import (
	"github.com/gin-gonic/gin"
	"kamestery.com/utils"
)

var routes_logger = utils.NewLogger("entpointroutes")

func InitializeRoutes(r *gin.Engine) {
	routes_logger.Debug("Attaching routes...")
	
	// Request Routing
	r.GET("/ping", ping)
	r.GET("/", home)

	// User Routes
	user_routes := r.Group("/user")
	{
		user_routes.GET("/login", login)
		user_routes.POST("/login", authenticate)
		user_routes.GET("/register", register)
		user_routes.POST("/register", authenticate)
	}
}
