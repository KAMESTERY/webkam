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
  (let [namespace (if-let [DATA_NAMEPSACE (m/env-var "DATA_NAMESPACE")] DATA_NAMEPSACE "com.kamestery.devdata")
        url       (if-let [URL_ROOT (m/env-var "URL_ROOT")] URL_ROOT "https://data-dev.kamestery.com/")]
    {:namespace namespace :url url}))
