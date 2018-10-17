package utils

import (
	"gopkg.in/go-playground/validator.v9"
)

var validation_logger = NewLogger("utilsvalidation")

func ValidateStruct(data interface{}) (validationErrors validator.ValidationErrors, err error) {
	validate := validator.New()
	err = validate.Struct(data)
	if err != nil {

		// this check is only needed when your code could produce
		// an invalid value for validation such as interface with nil
		// value most including myself do not usually have code like this.
		if _, ok := err.(*validator.InvalidValidationError); ok {
			validation_logger.Errorf("ERROR:::: %+v", err)
			return
		}

		validationErrors = err.(validator.ValidationErrors)

		validation_logger.Debugf("VALIDATION_ERRORS:::: %+v", validationErrors)
	}

	return
}
