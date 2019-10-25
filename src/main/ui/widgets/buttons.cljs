(ns ui.widgets.buttons
  (:require [utils.core :as u]
            [ui.styles.core :as s]))

(def btn-base "f6 link dim br1 ph3 pv2 mb2 dib grow")

(defn btn [input-attr]
  (let [{:keys [class value href]} input-attr
        base-styles [btn-base]
        styles (u/merge-styles base-styles class)
        attr (merge input-attr styles)]
    [:a attr value]))

