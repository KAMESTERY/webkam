package endpoints

import (
	"net/http"

	"github.com/gin-gonic/gin"
)

func ping(c *gin.Context) {
	c.JSON(http.StatusOK, gin.H{
		"message": "pong",
	})
}

func home(c *gin.Context) {
	render(c, gin.H{
		"title": "Kamestery Web App :-)",
	}, "public/home.html")
}
