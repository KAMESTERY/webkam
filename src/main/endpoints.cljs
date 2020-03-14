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

(defn- render-page [{:keys [title content styles scripts]}]
       (web/send :html
                 [t/default-template-ui
                  {:title   title
                   :content content
                   :styles  styles
                   :scripts scripts}]))

(defn home
      [req]
      (go
        (alt!
          (apply <list-topics (svc/topics))
          ([data]
           (render-page {:title   "Welcome to Kamestery!"
                         :content [p/home data]}))
          (on-timeout 2000)
          (do
            (log/warn "WARN::: Home Timeout")
            (render-page {:title   "Welcome to Kamestery!"
                          :content [p/home {}]
                          :scripts (path-js "main.js")})))))

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
      (render-page {:title   "User Login"
                    :content [p/login req]
                    :scripts (path-js "main.js")}))

(defn authenticate [req]
      (go
        (alt!
          (<authenticate req)
          ([resp]                                           ;; token
           (if (or (empty? resp)
                   (:error resp))
             (do
               (log/warn "WARN::: " (or (:error resp) "Could not authenticate"))
               (web/redirect :login))
             (web/redirect :home
                           :cookies [{:name (m/env-var "AUTH")
                                      :value (:token resp)
                                      :opts {:maxAge (* 1000 60 15 );; would expire after 15 minutes
                                             :httpOnly true ;; The cookie only accessible by the web server
                                             ;:signed true  ;; Indicates if the cookie should be signed (DO NOT USE FOR NOW)
                                             }}])))
          (on-timeout 2000)
          (do
            (log/warn "WARN::: Authenticate Timeout")
            (web/redirect :login)))))

(defn user-register
      [req]
      (render-page {:title   "User Registration"
                    :content [p/register {:csrf-token (:csrf-token req)}]
                    :scripts (path-js "main.js")}))

(defn enroll [req]
      (let [{:keys [body]} req]
           (if (not= (:password body) (:confirm-password body))
             (do (log/warn "WARN:::: Passwords do not match")
                 (web/redirect :register))
             (go
               (alt!
                 (<register req)
                 ([resp]
                  (if (or (empty? resp)
                          (:error resp))
                    (do
                      (log/warn "WARN::: " (or (:error resp) "Could not register"))
                      (web/redirect :register))
                    (web/redirect :login)))
                 (on-timeout 2000)
                 (do
                   (log/warn "WARN::: Document Timeout")
                   (web/redirect :register)))))))

;; content
(defn document [req]
      (go
        (let [{:keys [title topic]} (-> req :route-params)]
             (alt!
               (<get-document-and-related title topic)
               ([resp]
                (do (log/debug "RESPONSE::::" resp)
                    (render-page {:title   title
                                  :content [p/document resp]})))
               (on-timeout 2000)
               (do
                 (log/warn "WARN::: Document Timeout")
                 (render-page {:title   title
                               :content [p/document []]
                               :scripts (path-js "main.js")}))))))

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
                       (render-page {:title   topic
                                     :content [p/content data]}))))
               (on-timeout 2000)
               (do
                 (log/warn "WARN::: List Content Timeout")
                 (web/send :html
                           [t/default-template-ui
                            {:title   "List of Content"
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


