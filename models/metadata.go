package models

type MetaData struct {
	Userid     string   `json:"userId"`
	Identifier string   `json:"contentId"`
	Tags       []string `json:"tags"`
	TimeStamps
}

type TimeStamps struct {
	Created string `json:"created"`
	Updated string `json:"updated"`
}
