package main

import (
	"net/http"
	"os"
	"time"

	"github.com/gin-gonic/gin"

	"kamestery.com/endpoints"
	"kamestery.com/utils"
)

var main_logger = utils.NewLogger("main")

func main() {
	r := gin.Default()

	// Template Functions and Template Folder
	endpoints.AddTemplateFunctions(r)
	r.LoadHTMLGlob(utils.TemplatesDir + "/**/*") // Order matters

	// Static Files
	r.StaticFS("/static", http.Dir(utils.StaticDir))
	r.StaticFile("/favicon.ico", utils.StaticDir+"/favicon.ico")

	// Include Middleawares
	endpoints.IncludeCommonMiddlewares(r)

	// Initialize Routes
	endpoints.InitializeRoutes(r)

	// Start RPC Server
	go utils.ExecBin("worker-rpc")

	// Server Configuration
	port := os.Getenv("PORT")
	if len(port) == 0 {
		port = "8998"
	}

	main_logger.Debugf("Listening on port: %+v", port)

	s := &http.Server{
		Addr:           ":" + port,
		Handler:        r,
		ReadTimeout:    10 * time.Second,
		WriteTimeout:   10 * time.Second,
		MaxHeaderBytes: 1 << 20,
	}
	s.ListenAndServe()
}
