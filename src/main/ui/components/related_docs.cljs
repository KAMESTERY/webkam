(ns ui.components.related-docs
  (:require [ui.components.card :as c]))

(defn related [related doc]
  [:section
   [:h3.tc.primary.mt5 "Related Content"]
   [:div.flex.flex-wrap.justify-center.ph1.mw8.center
    (for [sibling related
          :when (not= sibling doc)]
      ^{:key sibling}
      [c/doc-card sibling])]])