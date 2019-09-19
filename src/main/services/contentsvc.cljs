(ns services.contentsvc
  (:require [taoensso.timbre :as log]))

(defn get-document [title topic]
  (do
    (log/debug "Retrieving Document topic: " topic ", title: " title)
    {:documents   [{:title    "document title"
                    :slug     "document-title"
                    :body     "this is the body of the document : )"
                    :metadata {:identification {:user_id    "user"
                                                :identifier "document-title::topic"
                                                :tags       ["doctag1 doctag2"]}}}]
     :media_items [{:name      "media title"
                    :categorie 1
                    :file_url  "media_title.txt"
                    :metadata  {:identification {:user_id    "user"
                                                 :identifier "media-titlle::document-title"
                                                 :tags       ["mediatag1 mediatag2"]}}}]}))


