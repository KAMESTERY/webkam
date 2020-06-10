(ns ui.ux.core
  (:require [ui.ux.dom :as dom]
            [ui.ux.language :as lang]
            [ui.ux.promotion :as p]))

(defn load-external-js [url]
      (dom/load-external-js url))

(defn google-translate-element-init [params]
      (lang/google-translate-element-init params))

(defn blm-badge-init []
      (p/blm-badge-init))
