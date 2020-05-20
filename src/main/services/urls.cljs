(ns services.urls
  (:require-macros [fast-twitch.macros :as m])
  (:require [mount.core :as mount :refer [defstate]]
            [fast-twitch.config :refer [config]]))

(defn url-config []
  (let [namespace (if-let [NAMEPSACE (m/env-var "NAMESPACE")] NAMEPSACE (:namespace @config))
        url       (if-let [BACKEND (m/env-var "BACKEND")] BACKEND (:backend @config))]
    {:namespace namespace :url url}))

