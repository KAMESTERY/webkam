package utils

import (
	"encoding/json"
	"io"
)

func DecodeJson(reader io.Reader, target interface{}) error {
	return json.NewDecoder(reader).Decode(target)
}

func EncodeJson(writer io.Writer, data interface{}) error {
	return json.NewEncoder(writer).Encode(data)
}
