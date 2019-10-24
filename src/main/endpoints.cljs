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
            [fast-twitch.os :as os]
            [fast-twitch.web-api :as web]
            [routing :refer [path-js]]
            [ui.components.core :as c]
            [ui.pages.core :as p]
            [ui.templates.core :as t]
            [services.core :as svc :refer [<get-document
                                           <get-document-and-related
                                           <list-content
                                           <list-topics]]))

(defn home
  [req]
  (go
    (alt!
      (apply <list-topics (svc/topics))
      ([data]
       (web/send :html
                 [t/default-template-ui
                  {:title   "Welcome to Kamestery!"
                   :content [p/home data]}]))
      (timeout 2000)
      (do
        (log/warn "WARN::: Home Timeout")
        (web/send :html
                  [t/default-template-ui
                   {:title   "Welcome to Kamestery!"
                    :content [p/home []]
                    :script (path-js "main.js")}])))
    )
  ;; COMMENT OUT THE ALT! BLOCK ABOVE AND UNCOMMENT BELOW FOR CLIENTSIDE RENDERING ONLY
  ;(web/send :html
  ;          [t/default-template-ui
  ;           {:title   "Welcome to Kamestery!"
  ;            :content [p/home []]
  ;            :script  (path-js "main.js")}])
  )

(defn home-json
  [req]
  (go
    (web/send :json
              (<! (apply <list-topics (svc/topics)))
              {:headers {:Content-Type "application/json"}
               :status  200})
    ))

;; user
(defn user-login
  [req]
  (let [{:keys [csrf-token]} req
        data {:csrf-token csrf-token}]
    (web/send :html
              [t/default-template-ui
               {:title   "User Login"
                :content [p/login data]
                :script (path-js "main.js")}])))

(defn authenticate [req]
  (let [{:keys [body]} req]
    (do
      (log/debug body)
      (svc/authenticate body)
      (web/redirect :home))))

(defn user-register
  [req]
  (let [{:keys [csrf-token]} req
        data {:csrf-token csrf-token}]
    (web/send :html
              [t/default-template-ui
               {:title   "User Registration"
                :content [p/register data]
                :script (path-js "main.js")}])))

(defn enroll [req]
  (let [{:keys [body csrf-token]} req]
    (do
      (svc/enroll body)
      (web/redirect :login))))

;; content
(defn document [req]
  (go
    (let [{:keys [title topic]} (-> req :route-params)]
      (alt!
        (<get-document-and-related title topic)
        ([resp]
         (do (log/debug "RESPONSE::::" resp)
             (web/send :html
                       [t/default-template-ui
                        {:title title
                         :content [p/document resp]}])))
        (timeout 2000)
        (do
          (log/warn "WARN::: Document Timeout")
          (web/send :html
                    [t/default-template-ui
                     {:title title
                      :content [p/document []]
                      :script (path-js "main.js")}])))
         ;; COMMENT OUT THE ALT! BLOCK ABOVE AND UNCOMMENT BELOW FOR CLIENTSIDE RENDERING ONLY
         ;(web/send :html
         ;          [t/default-template-ui
         ;           {:title title
         ;            :content [p/document []]
         ;            :script (path-js "main.js")}])
         )))

(defn document-json
  [req]
  (go
    (let [route-params (:route-params m)]
      (web/send :json
                (<! (<get-document-and-related title topic))
                {:headers {:Content-Type "application/json"}
                 :status  200}))
    ))

(defn list-content [req]
  (go
    (let [topic   (-> req :route-params :topic)]
      (alt!
        (<list-content topic)
        ([resp]
         (do
           (log/debug "RESONSE::::" resp)
           (let [data {:content resp :topic topic}]
             (web/send :html
                       [t/default-template-ui
                        {:title topic
                         :content [p/content data]}]))))
        (timeout 2000)
        (do
          (log/warn "WARN::: List Content Timeout")
          (web/send :html
                    [t/default-template-ui
                     {:title "List of Content"
                      :content [p/home {}]
                      :script (path-js "main.js")}])))
     ;; COMMENT OUT THE ALT! BLOCK ABOVE AND UNCOMMENT BELOW FOR CLIENTSIDE RENDERING ONLY
     ;    (web/send :html
     ;              [t/default-template-ui
     ;               {:title "List of Content"
     ;                :content [p/content {}]
     ;                :script (path-js "main.js")}])
         )))

(defn list-content-json [req]
  (go
    (let [topic   (-> req :route-params :topic)]
      (web/send :json
                (<! (<list-content topic)
                    {:headers {:Content-Type "application/json"}
                     :status  200})))
    ))

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


