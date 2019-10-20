(ns services.urls
  (:require-macros [fast-twitch.macros :as m]))

(defn url-config []
  (let [namespace (if-let [NAMEPSACE (m/env-var "NAMESPACE")] NAMEPSACE "com.kamestery.devdata")
        url       (if-let [BACKEND (m/env-var "BACKEND")] BACKEND "https://data-dev.kamestery.com/gql")]
    {:namespace namespace :url url}))

