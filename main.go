package main

import (
	"github.com/gin-gonic/gin"

	"kamestery.com/endpoints"
)

func main() {
	r := gin.Default()

	// Templates Folder
	r.LoadHTMLGlob("./templates/**/*")

	// Request Routing
	r.GET("/ping", endpoints.Ping)
	r.GET("/", endpoints.Home)
	r.Run() // listen and serve on 0.0.0.0:8080
}
