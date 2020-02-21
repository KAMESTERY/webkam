(ns ui.components.core
  (:require
    [ui.components.nav :as n]
    [ui.components.footer :as f]
    [ui.components.related-docs :as r]
    [ui.components.card :as c]))

(defn top-app-bar []
  (n/top-app-bar))

(defn menu []
  (n/menu))

(defn related [docs doc]
  (r/related docs doc))

(defn doc-card-lg [document]
  (c/doc-card-lg document))

(defn doc-card [document]
  (c/doc-card document))

(defn footer [& content]
  (apply f/footer content))
