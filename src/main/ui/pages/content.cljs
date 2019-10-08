(ns ui.pages.content
  (:require [taoensso.timbre :as log]
            [ui.components.core :as c]
            [bidi.bidi :refer [path-for]]
            [clojure.string :as str]
            [fast-twitch.nav :refer [cached-routes]]
            [utils.core :as utils]))

(defn document-ui [data]
  (let [{:keys [doc related]} data
        {:keys [Title Identifier Slug UserID CreatedAt UpdatedAt Body Tags]} doc]
    (do (log/debug data)
      [:<>
       [:div.ph1
        [:section.mt4
         [c/doc-card-lg doc]]
        [:hr.sec-hr-xl]
        [:section
         [:h3.tc.primary.mt0 "Related Content"]
         [:div.flex.flex-wrap.justify-center.ph1
          (for [sibling related
                :when   (not= sibling doc)]
            ^{:key sibling}
            [c/doc-card
             sibling])]]]])))

(defn content-list-ui [data]
  (let [{:keys [content topic]} data]
    [:<>
     [:div.mv5.ph1
      [:section
       [:h2.tc.primary (str/capitalize (name topic))]
       [:div.flex.flex-wrap.justify-center
        (for [doc content]
          ^{:key doc}
          [c/doc-card
           doc])]]]]))
