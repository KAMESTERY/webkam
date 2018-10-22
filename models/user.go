package models

import (
	"bytes"
	"fmt"
	"kamestery.com/utils"
	"net/http"
	"strings"
)

const (
	registerQuery = `
mutation RegisterUser {
  register(
    userId: "%s",
    email: "%s",
    username: "%s",
    password: "%s"
  )
}
`
	loginQuery = `
query LoginUser{
  login(
    userId: "%s",
    email: "%s",
    password: "%s"
  )
}
`
	getClaims = `
query GetClaims{
  getClaims(
    token: "%s"
  ) {
    userId,
    email,
    role
  }
}
`
)

var user_logger = utils.NewLogger("modelsuser")

type Claims struct {
	Userid string `json:"userId"`
	Email  string `json:"email"`
	Role   string `json:"role"`
}

type Credentials struct {
	Email    string `form:"email" binding:"required" validate:"required,min=10,max=120"`
	Password string `form:"password" binding:"required" validate:"required,min=8,max=120"`
}

// TODO: Revisit Validations, Leave json tags as is for now
type User struct {
	Userid    string `json:"userId"`
	Email     string `json:"email" form:"email" binding:"required" validate:"required,min=10,max=120"`
	Username  string `json:"username" form:"username" binding:"required" validate:"required,min=4,max=40"`
	Role      int    `json:"role"`
	Confirmed int    `json:"confirmed"`
	//Passwordhash    string `json:"passwordhash"`
	Lastseen        string `json:"lastseen"`
	Password        string `form:"password" binding:"required" validate:"required,min=8,max=120"`
	ConfirmPassword string `form:"password" binding:"required" validate:"required,min=8,max=120"`
}

func GetClaims(token string) (claims Claims) {
	c := &http.Client{
		Timeout: HTTP_CLIENT_TIMEOUT,
	}
	qryData := fmt.Sprintf(
		getClaims,
		token,
	)
	resp, err := c.Post(utils.BackendGQL, "application/json", newQuery(qryData))
	if err == nil {
		resp_struct := &struct {
			Data struct {
				Claims Claims `json:"getClaims"`
			} `json:"data"`
		}{}
		utils.DecodeJson(resp.Body, resp_struct)
		claims = resp_struct.Data.Claims
	}
	return
}

func Authenticate(creds Credentials) (token string) {
	c := &http.Client{
		Timeout: HTTP_CLIENT_TIMEOUT,
	}
	qryData := fmt.Sprintf(
		loginQuery,
		creds.Email,
		creds.Email,
		creds.Password,
	)
	resp, err := c.Post(utils.BackendGQL, "application/json", newQuery(qryData))
	if err == nil {
		resp_struct := &struct {
			Data struct {
				Token string `json:"login"`
			} `json:"data"`
		}{}
		utils.DecodeJson(resp.Body, resp_struct)
		token = resp_struct.Data.Token
	}
	return
}

func Enroll(user User) (ok bool) {
	ok = true
	c := &http.Client{
		Timeout: HTTP_CLIENT_TIMEOUT,
	}
	user.Userid = user.Email //TODO: Revisit this!! Backend also uses Email value as Userid
	qryData := fmt.Sprintf(
		registerQuery,
		user.Userid,
		user.Email,
		user.Username,
		user.Password,
	)
	resp, err := c.Post(utils.BackendGQL, "application/json", newQuery(qryData))
	if err == nil {
		resp_struct := &struct {
			Data struct {
				Register string `json:"register"`
			} `json:"data"`
		}{}
		utils.DecodeJson(resp.Body, resp_struct)
		if status := resp_struct.Data.Register; status == "" {
			ok = false
		}
	}
	return
}

func newQuery(queryString string) *strings.Reader {
	buffer := new(bytes.Buffer)
	query := struct {
		Query string `json:"query"`
	}{
		Query: queryString,
	}
	utils.EncodeJson(buffer, query)
	json := buffer.String()
	user_logger.Debugf("GQL_QUERY:::: %s", json)
	return strings.NewReader(json)
}
