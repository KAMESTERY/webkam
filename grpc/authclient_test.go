package grpc

import (
	"context"
	"github.com/stretchr/testify/assert"
	"github.com/stretchr/testify/suite"
	pb "kamestery.com/grpc/gen"
	"testing"
)

type AuthClientTestSuite struct {
	suite.Suite
}

func (ac *AuthClientTestSuite) SetupSuite() {
	// Do Nothing!
}

func (ac *AuthClientTestSuite) TearDownSuite() {
	// Do Nothing!
}

func TestRunAuthClientTestSuite(t *testing.T) {
	ac := new(AuthClientTestSuite)
	ac.SetT(t)
	suite.Run(t, ac)
}

func (ac *AuthClientTestSuite) TestLogin() {

	t := ac.T()

	assert := assert.New(t)

	grpcConn, connErr := NewGrpcConn()
	if connErr != nil {
		panic(connErr)
	}
	defer grpcConn.Close()

	authKamClient := pb.NewAuthKamClient(grpcConn)

	ctx := context.Background()
	userCredentialsReq := &pb.UserCredentialsReq{
		Email:    "gggg@gggg.ggg",
		Password: "gggggggg",
	}
	authClaimsResp, authErr := authKamClient.Authenticate(
		ctx,
		userCredentialsReq,
	)

	if authErr != nil {
		panic(authErr)
	}

	assert.True(len(authClaimsResp.Token) > 0)
	assert.True(len(authClaimsResp.UserId) > 0)
	assert.True(len(authClaimsResp.Email) > 0)

	assert.True(1 == 1, "one is one")
}
