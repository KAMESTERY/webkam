(ns services.authsvc
  (:require-macros [cljs.core.async.macros :refer [alt! go]])
  (:require [taoensso.timbre :as log]
            [cljs-http.client :as http]
            [graphql-query.core :refer [graphql-query]]
            [utils.graphql :refer [inline-fragment]]
            [cljs.core.async
             :as async
             :refer [<! put! chan close! timeout pipeline-async to-chan]]
            [cljs.spec.alpha :as s]
            [contractskam.specs.common-spec :as cspk]
            [services.urls :as urls]
            ["slugify" :as slugify]))

(defn <authenticate
      "[{:email e :password p :user-id u}] Authenticate user given username and password."
      [{:keys [body]}]
      (log/debug "Attempting login for user: " (:email body))
      (let [{:keys [email user password]} body
            {:keys [url]} (urls/url-config)
            query-str (graphql-query {:queries [[:authenticate {:creds {:Email    email
                                                                        :UserID   email
                                                                        :Password password}}
                                                 [(inline-fragment :Token [:token])]]]})]

           (log/debug "Query: " query-str)
           (go
             (if (and
                   (s/valid? ::cspk/email-type email)
                   (s/valid? ::cspk/email-type user)
                   (s/valid? ::cspk/non-null-string-type password)
                   )
               (-> (http/post
                     url
                     {:json-params
                      {:query query-str}})
                   <!
                   :body
                   :data
                   :authenticate)
               {:error "Invalid Email / Password"}))))

(defn <register
      "[{:email e :password p}] Register user given email username and password."
      [{:keys [body]}]
      (log/debug "Attempting registration for user: " (:email body))
      (let [{:keys [email password]} body
            {:keys [url]} (urls/url-config)
            query-str (graphql-query {:operation {:operation/type :mutation
                                                  :operation/name "Enroll"}
                                      :queries   [[:enroll
                                                   {:creds {:Email    email
                                                            :UserID   email
                                                            :Password password}}
                                                   [(inline-fragment :Msg [:msg])]]]})]

           (log/debug "Query: " query-str)
           (go
             (if (and
                   (s/valid? ::cspk/email-type email)
                   (s/valid? ::cspk/non-null-string-type password))
               (-> (http/post
                     url
                     {:json-params
                      {:query query-str}})
                   <!
                   :body
                   :data
                   :enroll)
               {:error "Invalid Email / Password"}))))