(ns ui.pages.content
  (:require [clojure.string :as str]
            [taoensso.timbre :as log]
            [bidi.bidi :refer [path-for]]
            [routing :refer [routing-data]]
            [ui.components.core :as c]
            [utils.core :as utils]))

(defn document-ui [data]
  (let [{:keys [doc related]} data
        {:keys [Title Identifier Slug UserID CreatedAt UpdatedAt Body Tags]} doc]
    (do (log/debug data)
      [:<>
       [:div.ph3
        [:section.mt4
         [c/doc-card-lg doc]]
        [:hr.sec-hr-xl]
        [:section
         [:h3.tc.primary.mt0 "Related Content"]
         [:div.flex.flex-wrap.justify-center.ph3
          (for [sibling related
                :when   (not= sibling doc)]
            ^{:key sibling}
            [c/doc-card
             sibling])]]]])))

(defn content-list-ui [data]
  (let [{:keys [content topic]} data]
    [:<>
     [:div.mv5
      [:section
       [:h2.tc.primary (str/capitalize (name topic))]
       [:div.flex.flex-wrap.justify-center.ph3
        (for [doc content]
          ^{:key doc}
          [c/doc-card
           doc])]]]]))
