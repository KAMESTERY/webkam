(ns ui.widgets.core
  (:require [taoensso.timbre :as log]))

;;;;;;;;;;;;;;;;;;;;;; Widgets
(defn raw-str-widget-ui [data]
  (log/debug "Raw Stringing...")
  (let [{:keys [text]} data]
    ;;[:div text]
    [:div
     [:br]
     [:strong text]]))

(defn un-bouton-ui [data]
  (log/debug "Buttoning ...")
  (let [{:keys [text]} data]
    [:div
     [:hr]
     [:input
      {:type  "submit"
       :class "btn btn-default"
       :value text}]]))


(defn hello-ui [data]
  (log/debug "Rendering ...")
  (let [{:keys [upper-bound]} data]
    [:div
     "Hello world!"
     [:ul (for [n (range 1 upper-bound)]
            [:li {:key n} n])]
     [un-bouton-ui {:text "React!!"}]]))
