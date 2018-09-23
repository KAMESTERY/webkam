package main

import (
	"net/http"
	"os"
	"time"

	"github.com/gin-gonic/gin"

	"kamestery.com/endpoints"
	"kamestery.com/utils"
)

func main() {
	r := gin.Default()

	// Templates Folder
	r.LoadHTMLGlob(utils.TemplatesDir + "/**/*")

	// Static Files
	r.StaticFS("/static", http.Dir(utils.StaticDir))
	r.StaticFile("/favicon.ico", utils.StaticDir+"/favicon.ico")

	// Request Routing
	r.GET("/ping", endpoints.Ping)
	r.GET("/", endpoints.Home)
	r.GET("/material", endpoints.MaterialDesignDemo)

	// Server Configuration
	port := os.Getenv("PORT")
	if len(port) == 0 {
		port = "8998"
	}
	s := &http.Server{
		Addr:           ":" + port,
		Handler:        r,
		ReadTimeout:    10 * time.Second,
		WriteTimeout:   10 * time.Second,
		MaxHeaderBytes: 1 << 20,
	}
	s.ListenAndServe()
}
