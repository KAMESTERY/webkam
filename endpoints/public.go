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

func materialDesignDemo(c *gin.Context) {
	c.HTML(http.StatusOK, "public/material-demo.html", gin.H{
		"title": "Simple Webpage using basic Material Design components.",
	})
}
