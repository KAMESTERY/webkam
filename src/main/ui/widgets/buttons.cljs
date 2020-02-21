(ns ui.widgets.buttons
  (:require [utils.core :as u]
            [ui.styles.core :as s]))

(def btn-base "f6 link dim br1 ph3 pv2 mb2 dib grow")

(defn btn [input-attr]
  (let [attr (u/override-attr input-attr [btn-base])
        value (:value input-attr)]
    [:a attr value]))

