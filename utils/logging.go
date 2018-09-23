package utils

import (
	"fmt"
	"log"
	"os"
	"strings"
)

const (
	delim = " - "

	FATAL = "[FATAL]"
	ERROR = "[ERROR]"
	WARN  = "[WARN ]"
	DEBUG = "[DEBUG]"
	INFO  = "[INFO ]"

	levlen = len(INFO)
	len2   = len(delim) + levlen
	len3   = 2*len(delim) + levlen

	prefix = "[webkam][%s]"
)

var logLevels map[string]int

func init() {
	logLevels = map[string]int{
		"FATAL": 1,
		"ERROR": 2,
		"INFO":  3,
		"WARN":  4,
		"DEBUG": 5,
	}
}

type Logger struct {
	Logger *log.Logger
}

func NewLogger(filectx string) (logger *Logger) {
	logger = &Logger{}
	logger.Logger = log.New(os.Stdout, fmt.Sprintf(prefix, filectx), log.Ldate|log.Ltime)
	return
}

func join2(level, msg string) string {
	n := len(msg) + len2
	j := make([]byte, n)
	o := copy(j, level)
	o += copy(j[o:], delim)
	copy(j[o:], msg)
	return string(j)
}

func join3(level, meta, msg string) string {
	n := len(meta) + len(msg) + len3
	j := make([]byte, n)
	o := copy(j, level)
	o += copy(j[o:], delim)
	o += copy(j[o:], meta)
	o += copy(j[o:], delim)
	copy(j[o:], msg)
	return string(j)
}

func (logger *Logger) output(level int, msg string) {
	logLevel := 2 // Error Level By Default
	//logLevel := 3 // Info Level By Default
	envLogLevel := os.Getenv("LOG_LEVEL")
	if val, ok := logLevels[strings.ToUpper(envLogLevel)]; ok {
		logLevel = val
	}
	//fmt.Printf("LOG LEVEL: %s==%d\n", envLogLevel, logLevel)
	if level <= logLevel {
		logger.Logger.Println(msg)
	}
}

// NOTE - the semantics here are different from go's logger.Fatal
// It will neither panic nor exit

// Fatal Logs Fatal
func (logger *Logger) Fatal(meta string, e error) {
	logger.output(1, join3(FATAL, meta, e.Error()))
}

// NOTE - the semantics here are different from go's logger.Logger.Fatal
// It will neither panic nor exit

// Fatalf Logs Fatal and Format
func (logger *Logger) Fatalf(format string, v ...interface{}) {
	logger.output(1, join2(FATAL, fmt.Sprintf(format, v...)))
}

// Error Logs Error
func (logger *Logger) Error(meta string, e error) {
	logger.output(2, join3(ERROR, meta, e.Error()))
}

// Errorf Logs Error and Format
func (logger *Logger) Errorf(format string, v ...interface{}) {
	logger.output(2, join2(ERROR, fmt.Sprintf(format, v...)))
}

// Debug Logs Debug
func (logger *Logger) Debug(msg string) {
	logger.output(5, join2(DEBUG, msg))
}

// Debugf Logs Debug and Format
func (logger *Logger) Debugf(format string, v ...interface{}) {
	logger.output(5, join2(DEBUG, fmt.Sprintf(format, v...)))
}

// Warn Logs Warn
func (logger *Logger) Warn(msg string) {
	logger.output(4, join2(WARN, msg))
}

// Warnf Logs Warn and Format
func (logger *Logger) Warnf(format string, v ...interface{}) {
	logger.output(4, join2(WARN, fmt.Sprintf(format, v...)))
}

// Info Logs Info
func (logger *Logger) Info(msg string) {
	logger.output(3, join2(INFO, msg))
}

// Infof Logs Info and Format
func (logger *Logger) Infof(format string, v ...interface{}) {
	logger.output(3, join2(INFO, fmt.Sprintf(format, v...)))
}
