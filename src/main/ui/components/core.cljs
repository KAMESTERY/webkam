(ns ui.components.core
  (:require
    [ui.components.nav :as n]
    [ui.components.footer :as f]
    [ui.components.doc :as d]
    [ui.components.card :as c]))

(defn top-app-bar [data]
      (n/top-app-bar data))

(defn menu []
      (n/menu))

(defn doc-card-lg [document]
      (c/doc-card-lg document))

(defn doc-card [document]
      (c/doc-card document))

(defn doc-preview [document]
  (d/doc-preview document))

(defn doc-preview-list [docs]
  (d/doc-preview-list docs))

(defn footer [& content]
      (apply f/footer content))
