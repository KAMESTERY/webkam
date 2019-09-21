(ns endpoints
  (:require-macros [cljs.core.async.macros :refer [alt! go]])
  (:require [cljs-http.client :as http]
            [cljs.core.async
             :as    async
             :refer [<! put! chan close! timeout]]
            [clojure.pprint :refer [pprint]]
            [taoensso.timbre :as log]
            [com.rpl.specter :as s]
            [cljs.nodejs :as nodejs]
            [util.os :as os]
            [express.web-api :as web]
            [ui.templates.common :as common]
            [ui.templates.home :as homeviews]
            [ui.templates.user :as userviews]
            [ui.templates.content :as contentviews]
            [services.usersvc :as usersvc]
            [services.contentsvc :as contentsvc]
            [services.authsvc :as authsvc]))

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
  (web/send :html
            [common/default-template-ui
             {:title   "SS Reacting"
              :content [common/hello-ui {:upper-bound 8}]
              :script  "/js/main.js"}]))

;; public
(defn home!
  [req]
  (do
    (web/send :html
              [common/default-template-ui
               {:title   "Welcome to Kamestery!"
                :content [homeviews/home-ui]}])))

;; user
(defn user-login
  [req]
  (let [{:keys [csrf-token]} req]
    (web/send :html
              [common/default-template-ui
               {:title   "User Login"
                :content [userviews/login-ui csrf-token]}])))


(defn authenticate [req]
  (let [{:keys [body]} req]
    (do
      (log/debug body)
      (usersvc/authenticate body)
      (web/redirect :home))))

(defn user-register
  [req]
  (let [{:keys [csrf-token]} req]
    (web/send :html
              [common/default-template-ui
               {:title   "User Registration"
                :content [userviews/register-ui csrf-token]}])))

(defn enroll [req]
  (let [{:keys [body csrf-token]} req]
    (do
      (usersvc/enroll body)
      (web/redirect :login))))

;; content
(defn document [req]
  (let [title   (-> req :route-params :title)
        topic   (-> req :route-params :topic)
        content (contentsvc/get-document title topic)
        document (-> content :documents first)]
    (do
      (log/debug "CONTENT:::" content)
      (log/debug "DOC:::" document)
      (web/send :html
                [common/default-template-ui
                 {:title   title
                  :content [contentviews/document-ui document]}]))))

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


