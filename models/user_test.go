package models

import (
	"context"
	"github.com/stretchr/testify/assert"
	"github.com/stretchr/testify/suite"
	"testing"
)

type UserTestSuite struct {
	suite.Suite
}

func (uts *UserTestSuite) SetupSuite() {
	// Do nothing
}

func (dqts *UserTestSuite) TearDownSuite() {
	// Do nothing
}

func TestRunUserTestSuite(t *testing.T) {
	uts := new(UserTestSuite)
	uts.SetT(t)
	suite.Run(t, uts)
}

func (uts *UserTestSuite) TestLogin() {

	t := uts.T()

	assert := assert.New(t)

	creds := Credentials{
		Email:    "hhhh@hhhh.hhh",
		Password: "hhhhhhhh",
	}

	claims := Authenticate(context.Background(), creds)

	assert.True(claims.Email == creds.Email)
	assert.True(claims.Role < 0)
	assert.True(len(claims.UserId) > 0)
	assert.True(len(claims.Token) > 0)
}

func (uts *UserTestSuite) TestEnroll() {

	t := uts.T()

	assert := assert.New(t)

	user := User{
		Userid:    "tttt@tttt.ttt",
		Email:    "tttt@tttt.ttt",
		Username:    "tttt",
		Password: "tttttttt",
	}

	ok := Enroll(context.Background(), user)

	assert.True(!ok)
}
