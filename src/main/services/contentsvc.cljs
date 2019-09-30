(ns services.contentsvc
  (:require-macros
    [cljs.core.async.macros :refer [alt! go]])
  (:require [taoensso.timbre :as log]
            [cljs-http.client :as http]
            [cljs.core.async
             :as    async
             :refer [<! put! chan close! timeout]]
            [fast-twitch.web-api :as web]
            [util.web :as web-util]))

(defn get-topics []
  ["history" "education" "language" "africa"])

(defn get-document
  "[topic title] Retrieve document by topic and title."
  [topic title]
  (go
   (let [{:keys [url namespace]} (web-util/url-config)
         query                   (str url "title/" namespace "/" topic "/" title)
         response                (<! (http/get query))]
     (log/debug "RESPONSE STATUS:::" (:status response))
     (log/debug "QUERY:::" query)
     (:body response))))

(defn list-content
  [topic]
  (go
   (let [{:keys [url namespace]} (web-util/url-config)
         query                   (str url "topic/" namespace "/" topic)
         response                (<! (http/get query))]
     (log/debug "QUERY URL:::" query)
     (log/debug "RESPONSE STATUS:::" (:status response))
     (:body response))))

