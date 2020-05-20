(ns core
  (:require-macros [fast-twitch.macros :as m])
  (:require [cljs.nodejs :as nodejs]
            [mount.core :as mount]
            [taoensso.timbre :as log]
            [fast-twitch.web-api :as web]
            [fast-twitch.server]
            [fast-twitch.config :refer [config]]
            [dispatch :refer [handle]]
            [routing :refer [routing-data]]
            ["morgan" :as logger]
            ["serve-static" :as serve-static]
            ["xhr2" :as xhr2]
            ["body-parser" :as body-parser]
            ["cookie-parser" :as cookie-parser]
            ["csurf" :as csurf]
            ["helmet" :as helmet]))

(nodejs/enable-util-print!)

(set! js/XMLHttpRequest xhr2)

(def routes
  (web/routes
    routing-data
    handle))

(defn middlewares []
  (let [staticFolder (if-let [STATIC (m/env-var "STATIC")] STATIC "static")
        secret (if-let [SECRET (m/env-var "SECRET")] SECRET (:secret @config))]
    (log/debug "Static Folder: " staticFolder)
    (log/debug "Secret: " secret)
    [(serve-static staticFolder (clj->js {:index false}))
     (helmet)
     (logger "combined")     ;; Logger
     (body-parser/json)      ;; support json encoded bodies
     (body-parser/urlencoded (clj->js {:extended true})) ;; support encoded bodies
     (cookie-parser secret)
     (csurf (clj->js {:cookie true}))]))

(defn main []
  (-> (mount/with-args
        {:ft {:middlewares (middlewares)
              :routes routes}})
      (mount/start)))

(set! *main-cli-fn* main)
