(ns ui.components.core
  (:require [ui.components.common :as g]
            [ui.components.home :as h]
            [ui.components.user :as u]
            [ui.components.content :as c]))

;; common
(defn top-app-bar [data]
  (g/top-app-bar))

(defn side-bar [data]
  (g/side-bar))

(defn footer [data]
  (g/footer))

(defn default-template [data]
  (g/default-template-ui data))

(defn doc-card [data]
  (g/doc-card))

;; home
(defn home [data]
  (h/home-ui))

;; user
(defn login [data]
  (u/login-ui data))

(defn register [data]
  (u/register-ui data))

;; content
(defn document [data]
  (c/document-ui))