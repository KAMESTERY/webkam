(ns services.contentsvc
  (:require-macros [cljs.core.async.macros :refer [alt! go]])
  (:require [taoensso.timbre :as log]
            [cljs-http.client :as http]
            [graphql-query.core :refer [graphql-query]]
            [cljs.core.async
             :as async
             :refer [<! put! chan close! timeout pipeline-async to-chan!]]
            [fast-twitch.web-api :as web]
            [services.urls :as urls]
            [utils.core :refer [to-slug]]))

(defn get-topics []
  ["history" "education" "language" "africa"])

(defn <get-document
  "[topic title] Retrieve document by topic and title."
  [topic title]
  (log/debug "Retrieving Content from Topic: " topic " with Title: " title)
  (let [{:keys [url namespace]} (urls/url-config)
        query-str (graphql-query {:queries
                                  [[:getdocument
                                    {:topic      (str namespace ":##:" (to-slug topic))
                                     :documentID (str namespace ":##:"
                                                      (to-slug topic) ":##:" (to-slug title))}
                                    [:topic :documentID :userID :identifier :slug
                                     :publish :tags :filtreVisuel :niveau :score
                                     :title :version :body :createdAt :updatedAt [:media [:mediaID
                                                                                          :fileUrl
                                                                                          :parentDocumentID
                                                                                          :type
                                                                                          :tags
                                                                                          :version
                                                                                          :score
                                                                                          :userID
                                                                                          :updatedAt
                                                                                          :createdAt]]]]]})]

    (log/debug "Query: " query-str)
    (go
      (-> (http/post
            url
            {:json-params
             {:query query-str}})
          <!
          :body
          :data
          :getdocument))))


(defn <get-document-and-related
  "[title topic] Retrieve document and related by topic and title."
  [title topic]
  (log/debug "Retrieving Content from Topic: " topic " with Title: " title)
  (let [{:keys [url namespace]} (urls/url-config)
        query-str (graphql-query {:queries
                                  [{:query/alias :doc
                                    :query/data
                                                 [:getdocument
                                                  {:topic      (str namespace ":##:" (to-slug topic))
                                                   :documentID (str namespace ":##:"
                                                                    (to-slug topic) ":##:" (to-slug title))}
                                                  [:topic :documentID :userID :identifier :slug
                                                   :publish :tags :filtreVisuel :niveau :score
                                                   :title :version :body :createdAt :updatedAt [:media [:mediaID
                                                                                                        :fileUrl
                                                                                                        :parentDocumentID
                                                                                                        :type
                                                                                                        :tags
                                                                                                        :version
                                                                                                        :score
                                                                                                        :userID
                                                                                                        :updatedAt
                                                                                                        :createdAt]]]]}
                                   {:query/alias :related
                                    :query/data
                                                 [:getdocumentsbytopic
                                                  {:topic (str namespace ":##:" (to-slug topic))}
                                                  [:topic :documentID :userID :identifier :slug
                                                   :publish :tags :filtreVisuel :niveau :score
                                                   :title :version :body :createdAt :updatedAt [:media [:mediaID
                                                                                                        :fileUrl
                                                                                                        :parentDocumentID
                                                                                                        :type
                                                                                                        :tags
                                                                                                        :version
                                                                                                        :score
                                                                                                        :userID
                                                                                                        :updatedAt
                                                                                                        :createdAt]]]]}]})]

    (log/debug "Query: " query-str)
    (go
      (-> (http/post
            url
            {:json-params
             {:query query-str}})
          <!
          :body
          :data))))


(defn <list-content
  [topic]
  (log/debug "Listing Content from Topic: " topic)
  (let [{:keys [url namespace]} (urls/url-config)
        query-str (graphql-query {:queries
                                  [[:getdocumentsbytopic
                                    {:topic (str namespace ":##:" (to-slug topic))}
                                    [:topic :documentID :userID :identifier :slug
                                     :publish :tags :filtreVisuel :niveau :score
                                     :title :version :body :createdAt :updatedAt [:media [:mediaID
                                                                                          :fileUrl
                                                                                          :parentDocumentID
                                                                                          :type
                                                                                          :tags
                                                                                          :version
                                                                                          :score
                                                                                          :userID
                                                                                          :updatedAt
                                                                                          :createdAt]]]]]})]

    (log/debug "Query: " query-str)
    (go
      (-> (http/post
            url
            {:json-params
             {:query query-str}})
          <!
          :body
          :data
          :querydocument))))

(defn <list-topics [& topics]
  (log/debug "Listing Content from Topics: " topics)
  (let [{:keys [url namespace]} (urls/url-config)
        query-str (graphql-query {:queries
                                  (into []
                                        (map #(hash-map
                                                :query/alias (keyword %)
                                                :query/data
                                                [:getdocumentsbytopics
                                                 {:topics [(str namespace ":##:" (to-slug %))]}
                                                 [:topic :documentID :userID :identifier :slug
                                                  :publish :tags :filtreVisuel :niveau :score
                                                  :title :version :body :createdAt :updatedAt [:media [:mediaID
                                                                                                       :fileUrl
                                                                                                       :parentDocumentID
                                                                                                       :type
                                                                                                       :tags
                                                                                                       :version
                                                                                                       :score
                                                                                                       :userID
                                                                                                       :updatedAt
                                                                                                       :createdAt]]]]) topics))})]

    (log/debug "Query: " query-str)
    (go
      (-> (http/post
            url
            {:json-params
             {:query query-str}})
          <!
          :body
          :data))))


