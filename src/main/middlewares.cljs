(ns middlewares
  (:require-macros [cljs.core.async.macros :refer [alt! go]])
  (:require [cljs.core.async
             :as async
             :refer [<! put! chan close! timeout]]
            [services.core :as svc :refer [<authenticate]]
            [fast-twitch.web-api :as web]
            [ui.templates.core :as t]
            [taoensso.timbre :as log]
            [ui.pages.core :as p]))

(defn do-authenticate [req res _]
  (let [r-chan (chan 1)]
    (go
      (<! (go
            (alt!
              (<authenticate req)
              ([resp]
               (if (not-empty resp)
                 (let [token (:token resp)]
                   (do (print (str "RESP TOKEN:::" token))
                       (.cookie res name val (clj->js {:signed true}))
                       (>! r-chan :home)))
                 (do
                   (log/debug (str "Unable to authenticate " (get-in req [:body :email])))
                   (>! r-chan :login))))
              (on-timeout 2000)
              (do
                (log/warn "WARN::: Document Timeout")
                (>! r-chan :login)))))
      (web/redirect (<! r-chan)))))
