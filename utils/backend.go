package utils

import "os"

var (
	backendUrl = func() string {
		beUrl := os.Getenv("BACKEND")
		if len(beUrl) == 0 {
			beUrl = "localhost:8088"
		}
		return beUrl
	}

	BackendGQL = backendUrl() + "/graphql"
)
