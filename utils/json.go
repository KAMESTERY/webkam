package utils

import (
	"bytes"
	"encoding/json"
	"io"
)

func DecodeJson(reader io.Reader, target interface{}) error {
	return json.NewDecoder(reader).Decode(target)
}

func EncodeJson(writer io.Writer, data interface{}) error {
	return json.NewEncoder(writer).Encode(data)
}

func ToJsonString(data interface{}) (jsonString string) {
	buffer := new(bytes.Buffer)
	EncodeJson(buffer, data)
	jsonString = buffer.String()
	return
}
