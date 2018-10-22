package models

type PageBase struct {
	Title               string `json:"title"`
	Slug                string `json:"slug"`
	Publish             bool   `json:"publish"`
	Body                string `json:"body"`
	Language            string `json:"language"`
	TileSize            uint64 `json:"title_size"`
	InstagramFilterTile string `json:"instagram_filter_tile"`
	MetaData
}

type MediaBase struct {
	Name        string `json:"name"`
	Audio       string `json:"audio"`
	Document    string `json:"document"`
	Image       string `json:"image"`
	Video       string `json:"video"`
	AudioUrl    string `json:"audio_url"`
	DocumentUrl string `json:"document_url"`
	ImageUrl    string `json:"image_url"`
	VideoUrl    string `json:"video_url"`
	Description string `json:"description"`
	//	TimeStamps
	MetaData
}
