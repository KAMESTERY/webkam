(ns ui.widgets.core
  (:require [taoensso.timbre :as log]
            [utils.core :as util]
            [ui.widgets.material :as m]
            [ui.widgets.icons :as icons]
            [ui.widgets.user_actions :as u]
            [ui.widgets.buttons :as btn]
            [ui.widgets.img :refer [img]]))

(defn tags [tags]
  [:div.mdc-chip-set.ma0.pa0.invisible-scrollbar.mht-1
   (for [tag tags]
     ^{:key tag}
     [:button.mdc-chip.pv0.h-50
      [:span.mdc-chip__text.f7.primary.w-100.b (str "#" tag)]])])

(defn search-input [id display override-styles]
  (let [base-styles ["mdc-text-field" "mdc-text-field--with-trailing-icon" "ml3"]
        styles (util/merge-styles base-styles override-styles)]
    [:div styles
     [:input.mdc-text-field__input {:type "text", :id id}]
     [:label.mdc-floating-label {:for id} display]
     [:i.material-icons.mdc-text-field__icon
      {:tab-index "0", :role "button"}
      "search"]
     [:div {:class "mdc-line-ripple"}]]))

(defn user-actions [data]
  (u/user-actions data))

; SVG - ICONS
(defn facebook-icon [fill]
  (icons/facebook fill))

(defn medium-icon [fill]
  (icons/medium fill))

(defn linkedin-icon [fill]
  (icons/linkedin fill))

(defn twitter-icon [fill]
  (icons/twitter fill))

; MATERIAL
(defn icon-button [& content]
  (apply m/icon-button content))

(defn mdc-icon [& content]
  (apply m/mdc-icon content))

(defn mdc-button [attr]
  (m/mdc-button attr))

;; IMG
(defn image [atr]
  (img atr))

;; BUTTONS
(defn button [attr]
  (btn/btn attr))