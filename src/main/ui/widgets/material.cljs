(ns ui.widgets.material
  (:require [utils.core :as u]))

(defn mdc-icon [icon label]
  [:i.material-icons.mdc-button__icon {:aria-label label} icon])

(defn icon-button [icon label]
  [:button.mdc-button
   [mdc-icon icon label]
   [:span.mdc-button__label label]])

(defn mdc-button [atr]
  (let [{:keys [class value]} atr
         base-styles ["mdc-button mdc-button--raised"]
         styles (u/merge-styles base-styles class)]
    [:button styles [:span.b.mdc-button__label value]]))