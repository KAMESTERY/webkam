package endpoints

import (
	"github.com/gin-gonic/gin"
	"github.com/utrack/gin-csrf"
	"net/http"
)

func login(c *gin.Context) {
	render(c, gin.H{
		"csrf": csrf.GetToken(c),
		"title": "Simple Webpage using basic Material Design components.",
	}, "public/login.html")
}

func authenticate(c *gin.Context) {
	//username := c.PostForm("username")
	//password := c.PostForm("password")
	c.Redirect(http.StatusFound, "/")
}
