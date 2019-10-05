(ns ui.components.core
  (:require
    [ui.components.nav :as n]
    [ui.components.footer :as f]
    [ui.components.card :as c]))

(defn top-app-bar []
  (n/top-app-bar))

(defn menu []
  (n/menu))

(defn doc-card [document]
  (c/doc-card document))

(defn topic-card [topic]
  (c/topic-card topic))

(defn footer [& content]
  (apply f/footer content))