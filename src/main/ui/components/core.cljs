(ns ui.components.core
  (:require
    [ui.components.nav :as n]
    [ui.components.footer :as f]
    [ui.components.related-docs :as r]
    [ui.components.card :as c]))

(defn top-app-bar [data]
      (n/top-app-bar data))

(defn menu []
      (n/menu))

(defn related [docs doc]
      (r/related docs doc))

(defn flat-card [document]
      (c/flat-card document))

(defn doc-card-lg [document]
      (c/doc-card-lg document))

(defn doc-card [document]
      (c/doc-card document))

(defn footer [& content]
      (apply f/footer content))
