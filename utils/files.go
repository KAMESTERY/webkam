package utils

import (
	"os"
	"path/filepath"
)

var (
	baseDir = func() string {
		bd := os.Getenv("BASEDIR")
		if len(bd) == 0 {
			dir, err := filepath.Abs(filepath.Dir(os.Args[0]))
			if err != nil {
				dir = "."
			}
			bd = dir
		}
		return bd
	}()

	TemplatesDir = baseDir + "/templates"
)
