package models

import (
	"bytes"
	"fmt"
	"kamestery.com/utils"
	"net/http"
	"strings"
	"time"
)

const (
	loginQuery = `
query LoginUser{
  login(
    userId: "%s",
    email: "%s",
    password: "%s"
  )
}
`
)

var user_logger = utils.NewLogger("modelsuser")

type queryData struct {
	Query string `json:"query"`
}

// TODO: Revisit Validations
type Credentials struct {
	Email    string `form:"email" binding:"required" validate:"required,min=50"`
	Password string `form:"password" binding:"required" validate:"required,min=10"`
}

// TODO: Revisit Validations
type User struct {
	Username        string `form:"username" binding:"required" validate:"required,min=50"`
	Email           string `form:"email" binding:"required" validate:"required,min=50"`
	Password        string `form:"password" binding:"required" validate:"required,min=10"`
	ConfirmPassword string `form:"password" binding:"required" validate:"required,min=10"`
}

type TokenData struct {
	Data struct {
		Token string `json:"token"`
	}
}

func Authenticate(creds Credentials) (token string) {
	c := &http.Client{
		Timeout: 15 * time.Second,
	}
	queryData := fmt.Sprintf(
		loginQuery,
		creds.Email,
		creds.Email,
		creds.Password,
	)
	resp, err := c.Post(utils.BackendGQL, "application/json", newQuery(queryData))
	if err == nil {
		token_data := &TokenData{}
		utils.DecodeJson(resp.Body, token_data)
		token = token_data.Data.Token
	}
	return
}

func Enroll(_user User) (status string) {
	status = "SUCCESS"
	return
}

func newQuery(queryString string) *strings.Reader {
	buffer := new(bytes.Buffer)
	query := queryData{
		Query: queryString,
	}
	utils.EncodeJson(buffer, query)
	json := buffer.String()
	return strings.NewReader(json)
}
