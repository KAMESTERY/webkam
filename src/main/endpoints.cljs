(ns endpoints
  (:require-macros [cljs.core.async.macros :refer [alt! go]]
                   [fast-twitch.macros :as m])
  (:require [cljs-http.client :as http]
            [cljs.core.async
             :as async
             :refer [<! put! chan close! timeout]]
            [clojure.pprint :refer [pprint]]
            [taoensso.timbre :as log]
            [com.rpl.specter :as s]
            [cljs.nodejs :as nodejs]
            [fast-twitch.os :as os]
            [fast-twitch.web-api :as web]
            [routing :refer [path-js]]
            [ui.components.core :as c]
            [ui.pages.core :as p]
            [ui.templates.core :as t]
            [services.core :as svc :refer [<get-document
                                           <get-document-and-related
                                           <list-content
                                           <list-topics
                                           <register
                                           <authenticate]]))

(defn- on-timeout [default]
  "" " Control the Timeout from an Environment Variable " ""
  (let [t (if-let [TIMEOUT (m/env-var "TIMEOUT")] TIMEOUT default)]
    (timeout t)))

(defn bind-csrf [req resp]
  (merge {:csrf-token (:csrf-token req)} resp))


(defn navigate [{:keys [title content script]}]
  (web/send :html
            [t/default-template-ui
             {:title   title
              :content content
              :script  script}]))

(defn home
  [req]
  (go
    (alt!
      (apply <list-topics (svc/topics))
      ([data]
       (navigate {:title   "Welcome to Kamestery!"
                  :content [p/home data]}))
      (on-timeout 2000)
      (do
        (log/warn "WARN::: Home Timeout")
        (navigate {:title   "Welcome to Kamestery!"
                   :content [p/home data]
                   :scripts  (path-js "main.js")})))))

(defn home-json
  [req]
  (go
    (web/send :json
              (<! (apply <list-topics (svc/topics)))
              {:headers {:Content-Type "application/json"}
               :status  200})))


;; user
(defn user-login
  [req]
  (let [data (bind-csrf req {})]
    (navigate {:title   "User Login"
               :content [p/login data]
               :scripts  (path-js "main.js")})))

(defn authenticate [req]
  (go
    (alt!
      (<authenticate req)
      ([resp] ;; token
       (if (not-empty resp)
         (do (print (str "RESP TOKEN:::" resp))
             (navigate {:title   "Welcome to Kamestery!"
                        :content [p/home {}]}))
         (let [msg {:msg (str "Unable to authenticate " (get-in req [:body :email]))}
               data (bind-csrf req msg)]
           (do (log/debug (:msg msg))
               (navigate {:title   "User Registration"
                          :content [p/login data]})))))
      (on-timeout 2000)
      (do
        (log/warn "WARN::: Document Timeout")
        (navigate {:title   "User Registration"
                   :content [p/register []]
                   :scripts  (path-js "main.js")})))))

(defn user-register
  [req]
  (let [data (bind-csrf req {})]
    (navigate {:title   "User Registration"
               :content [p/register data]
               :scripts  (path-js "main.js")})))

(defn handle-enroll [req resp]
  (if (not-empty resp)
    (let [msg {:msg (str "Successfully registered " (get-in req [:body :email]))}
          data (bind-csrf req msg)]
      (do (log/debug (:msg msg))
          (navigate {:title   "User Login"
                     :content [p/login data]})))
    (let [msg {:msg (str "Unable to register " (get-in req [:body :email]))}
          data (bind-csrf req msg)]
      (do (log/debug (:msg msg))
          (navigate {:title   "User Registration"
                     :content [p/register data]})))))

(defn enroll! [req]
  (go
    (alt!
      (<register req)
      ([resp]
       (handle-enroll req resp))
      (on-timeout 2000)
      (do
        (log/warn "WARN::: Document Timeout")
        (navigate {:title   "User Registration"
                   :content [p/register []]
                   :scripts  (path-js "main.js")})))))

(defn validate-and-enroll [req]
  (let [{:keys [body]} req]
    (cond (not= (:password body) (:confirm-password body))
          (let [msg {:msg (str "Passwords must match.")}
                data (bind-csrf req msg)]
            (do (log/debug (:msg msg))
                (navigate {:title   "User Registration"
                           :content [p/register data]})))
          :default (enroll! req))))

(defn enroll [req]
  (validate-and-enroll req))

;; content
(defn document [req]
  (go
    (let [{:keys [title topic]} (-> req :route-params)]
      (alt!
        (<get-document-and-related title topic)
        ([resp]
         (do (log/debug "RESPONSE::::" resp)
             (navigate {:title   title
                        :content [p/document resp]})))
        (on-timeout 2000)
        (do
          (log/warn "WARN::: Document Timeout")
          (navigate {:title   title
                     :content [p/document []]
                     :scripts  (path-js "main.js")}))))))

(defn document-json
  [req]
  (go
    (let [{:keys [title topic]} (-> req :route-params)]
      (web/send :json
                (<! (<get-document-and-related title topic))
                {:headers {:Content-Type "application/json"}
                 :status  200}))))


(defn list-content [req]
  (go
    (let [topic (-> req :route-params :topic)]
      (alt!
        (<list-content topic)
        ([resp]
         (do
           (log/debug "RESONSE::::" resp)
           (let [data {:content resp :topic topic}]
             (navigate {:title   topic
                        :content [p/content data]}))))
        (on-timeout 2000)
        (do
          (log/warn "WARN::: List Content Timeout")
          (web/send :html
                    [t/default-template-ui
                     {:title "List of Content"
                      :content [p/content {}]
                      :scripts (path-js "main.js")}]))))))


(defn list-content-json [req]
  (go
    (let [topic (-> req :route-params :topic)]
      (web/send :json
                (<! (<list-content topic))
                {:headers {:Content-Type "application/json"}
                 :status  200}))))


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


