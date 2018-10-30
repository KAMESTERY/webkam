package grpc

import (
	"google.golang.org/grpc"
	"kamestery.com/utils"
)

var authclient_logger = utils.NewLogger("entpointauthclient")

const (
	address = "localhost:9099"
)

func NewGrpcConn() (grpcConn *grpc.ClientConn, err error) {
	grpcConn, err = grpc.Dial(address, grpc.WithInsecure())
	if err != nil {
		authclient_logger.Fatalf("GRPC_CONNECTION_ERROR:::: %v", err)
	}
	return
}
