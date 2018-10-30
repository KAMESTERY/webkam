package models

import (
	"context"
	"kamestery.com/grpc"
	pb "kamestery.com/grpc/gen"
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

	grpcConn, connErr := grpc.NewGrpcConn()
	if connErr != nil {
		user_logger.Errorf("GRPC_CONN_ERROR:::: %+v", connErr)
		return
	}
	defer grpcConn.Close()

	authKamClient := pb.NewAuthKamClient(grpcConn)

	userCredentialsReq := &pb.UserCredentialsReq{
		Email:    creds.Email,
		Password: creds.Password,
	}

	authClaimsResp, authErr := authKamClient.Authenticate(ctx, userCredentialsReq)
	if authErr != nil {
		user_logger.Errorf("AUTHENTICATION_ERROR:::: %+v", authErr)
		return
	}

	claims.Token = authClaimsResp.Token
	claims.Email = authClaimsResp.Email
	claims.UserId = authClaimsResp.UserId
	claims.Role = authClaimsResp.Role

	return
}

func Enroll(ctx context.Context, user User) (ok bool, msg string) {

	grpcConn, connErr := grpc.NewGrpcConn()
	if connErr != nil {
		user_logger.Errorf("GRPC_CONN_ERROR:::: %+v", connErr)
		return
	}
	defer grpcConn.Close()

	authKamClient := pb.NewAuthKamClient(grpcConn)

	userEnrollReq := &pb.UserEnrollReq{
		Username: user.Username,
		Email: user.Email,
		Password: user.Password,
	}

	enrollStatusResp, enrollErr := authKamClient.Enroll(ctx, userEnrollReq)
	if enrollErr != nil {
		user_logger.Errorf("ENROLLMENT_ERROR:::: %+v", enrollErr)
		return
	}

	return enrollStatusResp.Success, enrollStatusResp.Message
}
