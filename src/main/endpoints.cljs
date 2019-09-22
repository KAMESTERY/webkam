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
            [ui.components.core :as cmpt]
            [services.core :as svc]))

(defn handle-response [response grab-data-fn]
  (let [status (:status response)]
    (prn "Status: " status)
    (condp = status
      200 (grab-data-fn response)
      401 ["Ouch!!!!"]
      403 ["Remote Service Denied Access"]
      404 ["Could not Find Anything"]
      500 ["Something Broke"])))

;; public
(defn home!
  [req]
  (do
    (web/send :html
              [cmpt/default-template
               {:title   "Welcome to Kamestery!"
                :content [cmpt/home]}])))

;; user
(defn user-login
  [req]
  (let [{:keys [csrf-token]} req]
    (web/send :html
              [cmpt/default-template
               {:title   "User Login"
                :content [cmpt/login csrf-token]}])))


(defn authenticate [req]
  (let [{:keys [body]} req]
    (do
      (log/debug body)
      (svc/authenticate body)
      (web/redirect :home))))

(defn user-register
  [req]
  (let [{:keys [csrf-token]} req]
    (web/send :html
              [cmpt/default-template
               {:title   "User Registration"
                :content [cmpt/register csrf-token]}])))

(defn enroll [req]
  (let [{:keys [body csrf-token]} req]
    (do
      (svc/enroll body)
      (web/redirect :login))))

;; content
(defn document [req]
  (let [title   (-> req :route-params :title)
        topic   (-> req :route-params :topic)
        content (svc/get-document title topic)
        document (-> content :documents first)]
    (do
      (log/debug "CONTENT:::" content)
      (log/debug "DOC:::" document)
      (web/send :html
                [cmpt/default-template
                 {:title   title
                  :content [cmpt/document document]}]))))

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


