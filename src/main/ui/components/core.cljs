(ns ui.components.core
  (:require
    [ui.components.nav :as n]
    [ui.components.footer :as f]
    [ui.components.card :as c]))

(defn top-app-bar []
  (n/top-app-bar))

(defn side-bar []
  (n/side-bar))

(defn doc-card [document]
  (c/doc-card document))

(defn footer [& content]
  (apply f/footer content))
