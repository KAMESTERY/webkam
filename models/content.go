package models

import (
	"context"
	ct "github.com/KAMESTERY/middlewarekam/content"
	"kamestery.com/utils"
)

var content_logger = utils.NewLogger("modelscontent")

func GetUserContent(ctx context.Context) (claims ct.Content) {

	contentKamClient := ct.NewContentKamClient()

	contentId := []*ct.Identification{
		{
			UserId:     "\"user_id\":\"derektaylor.us@gmail.com\"",
			Identifier: "\"identifier\":\"com.kamestery.devdata.derektaylor.us@gmail.com.sample-title-document\"",
		},
	}

	contentHandles := &ct.ContentHandles{
		ItemIds: contentId,
	}

	content, contentErr := contentKamClient.Get(ctx, "derektaylor.us@gmail.com", "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiIsImtpZCI6Ik1VTFRJX0dFTklVU19LSUQifQ.eyJ0b2tlbiI6bnVsbCwidXNlcl9pZCI6ImRlcmVrdGF5bG9yLnVzQGdtYWlsLmNvbSIsImVtYWlsIjoiZGVyZWt0YXlsb3IudXNAZ21haWwuY29tIiwicm9sZSI6LTk5OTl9.Divhp4awnNVk2_UDiU4bDPHbOV6geQDlhGhu-P7hMmU7clovoSuTE3TS4q0uc09pjfOfUuS3mWxwmiFT_Q7OJg", contentHandles)

	if contentErr != nil {
		content_logger.Errorf("CONTENT_ERROR:::: %+v", contentErr)
		return
	}

	return *content
}
