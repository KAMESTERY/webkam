package endpoints

import (
	"net/http"

	"github.com/gin-gonic/gin"
)

func render(c *gin.Context, data gin.H, template string) {
	switch c.Request.Header.Get("Accept") {
	case "application/json":
		c.JSON(http.StatusOK, data)
	case "applicaiton/xml":
		c.XML(http.StatusOK, data)
	default:
		c.HTML(http.StatusOK, template, data)
	}
}

func render403(c *gin.Context, data gin.H) {
	switch c.Request.Header.Get("Accept") {
	case "application/json":
		c.JSON(http.StatusBadRequest, data)
	case "applicaiton/xml":
		c.XML(http.StatusBadRequest, data)
	default:
		c.HTML(http.StatusBadRequest, "error/403.html", data)
	}
}

func render404(c *gin.Context, data gin.H) {
	switch c.Request.Header.Get("Accept") {
	case "application/json":
		c.JSON(http.StatusNotFound, data)
	case "applicaiton/xml":
		c.XML(http.StatusNotFound, data)
	default:
		c.HTML(http.StatusNotFound, "error/404.html", data)
	}
}

func render500(c *gin.Context, data gin.H) {
	switch c.Request.Header.Get("Accept") {
	case "application/json":
		c.JSON(http.StatusInternalServerError, data)
	case "applicaiton/xml":
		c.XML(http.StatusInternalServerError, data)
	default:
		c.HTML(http.StatusInternalServerError, "error/500.html", data)
	}
}
