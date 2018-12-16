package models

import (
	"context"
	"github.com/KAMESTERY/middlewarekam/auth"
	ct "github.com/KAMESTERY/middlewarekam/content"
	"kamestery.com/utils"
)

var content_logger = utils.NewLogger("modelscontent")

const content_id = "com.kamestery.devdata.derektaylor.us@gmail.com.sample-title-document"
const email = "derektaylor.us@gmail.com"
const password = "12345678"

//func GetUserContent(ctx context.Context, creds Credentials) (content *ct.Content) {
func GetUserContent(ctx context.Context) (content *ct.Content) {

	authClient := auth.NewAuthKamClient()

	user_creds_req := auth.UserCredentialsReq{
		Email:    email,
		Password: password,
		//creds.Email,
		//creds.Password,
	}
	auth_claims_resp, _ := authClient.Authenticate(context.Background(), &user_creds_req)
	token := auth_claims_resp.Token

	contentKamClient := ct.NewContentKamClient()

	contentId := []*ct.Identification{
		{
			UserId:     email,
			Identifier: content_id,
		},
	}

	contentHandles := &ct.ContentHandles{
		ItemIds: contentId,
	}

	content, contentErr := contentKamClient.Get(ctx, email, token, contentHandles)

	if contentErr != nil {
		content_logger.Errorf("CONTENT_ERROR:::: %+v", contentErr)
	}

	return
}
