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
	c.HTML(http.StatusOK, "public/home.html", gin.H{
		"title": "Kamestery Web App :-)",
	})
}

func login(c *gin.Context) {
	c.HTML(http.StatusOK, "public/login.html", gin.H{
		"title": "Simple Webpage using basic Material Design components.",
	})
}
