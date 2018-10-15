package models

import (
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

	token := Authenticate(creds)

	assert.True(len(token) > 0)
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

	ok := Enroll(user)

	assert.True(!ok)
}
