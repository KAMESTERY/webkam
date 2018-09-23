package endpoints

import (
	"net/http"

	"github.com/gin-gonic/gin"
)

func render(c *gin.Context, data gin.H, template string) {
	switch c.Request.Header.Get("Accept") {
	case "application/json":
		c.JSON(http.StatusOK, data["payload"])
	case "applicaiton/xml":
		c.XML(http.StatusOK, data["payload"])
	default:
		c.HTML(http.StatusOK, template, data["payload"])
	}
}
