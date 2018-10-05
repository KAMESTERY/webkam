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

	assert.True(len(Login("hhhh@hhhh.hhh", "hhhh@hhhh.hhh", "hhhhhhhh")) > 0)
}
