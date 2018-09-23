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
	r.GET("/material", materialDesignDemo)
}
