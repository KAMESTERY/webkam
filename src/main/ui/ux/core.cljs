(ns ui.ux.core
  (:require [ui.ux.dom :as dom]
            [ui.ux.language :as lang]))

(defn load-external-js [url]
      (dom/load-external-js url))

(defn google-translate-element-init [params]
      (lang/google-translate-element-init params))
