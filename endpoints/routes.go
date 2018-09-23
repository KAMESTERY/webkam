package endpoints

import (
	"github.com/gin-gonic/gin"
)

func InitializeRoutes(r *gin.Engine) {
	// Request Routing
	r.GET("/ping", ping)
	r.GET("/", home)
	r.GET("/material", materialDesignDemo)
}
