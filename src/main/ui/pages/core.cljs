(ns ui.pages.core
  (:require
    [ui.pages.user :as u]
    [ui.pages.content :as c]
    [ui.pages.home :as h]))

(defn home [data]
  (h/home-ui data))

(defn content [data]
  (c/content-list-ui data))

(defn document [data]
  (c/document-ui data))

(defn login [data]
  (u/login-ui data))

(defn register [data]
  (u/register-ui data))