(ns endpoints
  (:require-macros [cljs.core.async.macros :refer [alt! go]])
  (:require [cljs-http.client :as http]
            [cljs.core.async :as async :refer [<! put! chan close! timeout]]
            [clojure.pprint :refer [pprint]]
            [taoensso.timbre :as log]
            [com.rpl.specter :as s]
            [cljs.nodejs :as nodejs]
            [util.os :as os]
            [express.web-api :as web]
            [ui.templates :as tmpl]))

(defn handle-response [response grab-data-fn]
  (let [status (:status response)]
    (prn "Status: " status)
    (condp = status
      200 (grab-data-fn response)
      401 ["Ouch!!!!"]
      403 ["Remote Service Denied Access"]
      404 ["Could not Find Anything"]
      500 ["Something Broke"])))

(defn render-widget
  [req]
  (web/send :html [tmpl/default-template-ui
                   {:title "SS Reacting"
                    :content [tmpl/hello-ui {:upper-bound 8}]
                    :script "/js/main.js"
                    }]))

;; public
(defn home!
  [req]
  (do
    (pprint req)
    (web/send :html [tmpl/default-template-ui
                     {:title "Welcome to Kamestery!"
                      :content [tmpl/home-ui]
                      }])))

;; user
(defn user-login
  [req]
  (let [{:keys [csrf-token]} req]
    (web/send :html
              [tmpl/default-template-ui
               {:title   "User Login"
                :content [tmpl/login-ui csrf-token]}])))


(defn authenticate [req]
  (let [{:keys [body]} req]
    (do
      (log/debug body)
      (web/send :proceed "Home" {:headers {:location (web/path-for :home)}
                                 :status 301}))))

;; Application LifeCycle
(defn app-start
  [req]
  (web/send "Started"))

(defn check-health
  [req]
  (web/send "Healthy!"))

(defn app-stop
  [req]
  (web/send "Stopping..."))


