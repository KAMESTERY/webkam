(ns util.web
  (:require-macros [fast-twitch.macros :as m]))

(defn handle-response [response grab-data-fn]
  (let [status (:status response)]
    (prn "Status: " status)
    (condp = status
      200 (grab-data-fn response)
      401 ["Ouch!!!!"]
      403 ["Remote Service Denied Access"]
      404 ["Could not Find Anything"]
      500 ["Something Broke"])))

(defn url-config []
  (let [namespace (if-let [NAMEPSACE (m/env-var "NAMESPACE")] NAMEPSACE "com.kamestery.devdata")
        url       (if-let [BACKEND (m/env-var "BACKEND")] BACKEND "https://data-dev.kamestery.com/gql")]
    {:namespace namespace :url url}))

