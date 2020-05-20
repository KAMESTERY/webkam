(ns dispatch
  (:require [auth :as auth :refer [check-auth]]
            [endpoints :as ep]
            [fast-twitch.web-api :as web]))

(defmulti handle (fn [req-data] (:endpoint req-data)))

;; protected
(defmethod handle :some-protected-page [req-data]
           (check-auth
             (:req req-data)
             ep/document))

;; public
(defmethod handle :home [req-data]
           (ep/home (:req req-data)))

(defmethod handle :home-json [req-data]
           (ep/home-json (:req req-data)))

;; user
(defmethod handle :authenticate [req-data]
           (ep/authenticate (:req req-data)))

(defmethod handle :logout [req-data]
           (ep/user-logout (:req req-data)))

(defmethod handle :login [req-data]
           (ep/user-login (:req req-data)))

(defmethod handle :register [req-data]
           (ep/user-register (:req req-data)))

(defmethod handle :enroll [req-data]
           (ep/enroll (:req req-data)))

;; content
(defmethod handle :document [req-data]
           (ep/document (:req req-data)))

(defmethod handle :document-json [req-data]
           (ep/document-json (:req req-data)))

(defmethod handle :list-content-by-topic [req-data]
           (ep/list-content (:req req-data)))

(defmethod handle :list-content-by-topic-json [req-data]
           (ep/list-content-json (:req req-data)))

;; default
(defmethod handle :default [_]
           (web/send "Not Found"))

