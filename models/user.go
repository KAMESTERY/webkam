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

type User struct {
	Email    string `validate:"required,min=50"`
	Password string `validate:"required,min=10"`
}

type TokenData struct {
	Data struct {
		Token string `json:"token"`
	}
}

func Login(user_id, email, password string) (token string) {
	c := &http.Client{
		Timeout: 15 * time.Second,
	}
	resp, err := c.Post(utils.BackendGQL, "application/json", newQuery(fmt.Sprintf(loginQuery, user_id, email, password)))
	if err == nil {
		token_data := &TokenData{}
		utils.DecodeJson(resp.Body, token_data)
		token = token_data.Data.Token
	}
	return
}

func newQuery(queryString string) *strings.Reader {
	buffer := new(bytes.Buffer)
	query := queryData {
		Query: queryString,
	}
	utils.EncodeJson(buffer, query)
	json := buffer.String()
	return strings.NewReader(json)
}
