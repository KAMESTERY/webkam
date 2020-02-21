(ns services.core
  (:require [taoensso.timbre :as log]
            [services.usersvc :as u]
            [services.contentsvc :as c]
            [services.authsvc :as a]))

(defn authenticate [data]
  (u/authenticate data))

(defn enroll [data]
  (u/enroll data))

(defn <register [data]
  (a/<register data))

(defn <authenticate [data]
  (a/<authenticate data))

(defn set-claims! [data]
  (u/set-claims! data))

(defn logged-in? []
  (u/logged-in?))

(defn <get-document [title topic]
  (c/<get-document title topic))

(defn <get-document-and-related [title topic]
  (c/<get-document-and-related title topic))

(defn <list-content [topic]
  (c/<list-content topic))

(defn <list-topics [& topics]
  (apply c/<list-topics topics))

(defn topics []
  (c/get-topics))
