package models

import (
	"context"
	"github.com/KAMESTERY/middlewarekam/auth"
	"kamestery.com/utils"
)

var user_logger = utils.NewLogger("modelsuser")

type Claims struct {
	Token  string
	UserId string `json:"userId"`
	Email  string `json:"email"`
	Role   int32  `json:"role"`
}

func (c *Claims) Ok() (ok bool) {
	ok = c.Token != "" && c.UserId != "" && c.Email != ""
	return
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
	Password        string `form:"password" binding:"required" validate:"eqfield=ConfirmPassword,required,min=8,max=120"`
	ConfirmPassword string `form:"confirm-password" binding:"required" validate:"required,min=8,max=120"`
}

func Authenticate(ctx context.Context, creds Credentials) (claims Claims) {

	authClient := auth.NewAuthKamClient()
	user_creds_req := auth.UserCredentialsReq{
		creds.Email,
		creds.Password,
	}
	auth_claims_resp, _ := authClient.Authenticate(ctx, &user_creds_req) //TODO: Revisit this!!!!

	claims.Token = auth_claims_resp.Token
	claims.Email = auth_claims_resp.Email
	claims.UserId = auth_claims_resp.UserId
	claims.Role = auth_claims_resp.Role

	return
}

func Enroll(ctx context.Context, user User) (ok bool) {

	authClient := auth.NewAuthKamClient()
	user_enroll_req := auth.UserEnrollReq{
		user.Username,
		user.Email,
		user.Password,
	}
	enroll_status_resp, _ := authClient.Enroll(ctx, &user_enroll_req) //TODO: Revisit this!!!!
	ok = enroll_status_resp.Success //TODO: Revisit this!!!!

	return
}
