(ns ui.pages.content
  (:require [clojure.string :as str]
            [taoensso.timbre :as log]
            [bidi.bidi :refer [path-for]]
            [routing :refer [routing-data]]
            [ui.components.core :as c]
            [ui.widgets.core :as w]
            [utils.core :as utils]))


(defn document-ui [data]
  (let [{:keys [doc related]} data
        {:keys [Title Identifier Slug UserID CreatedAt UpdatedAt Body Tags]} doc]
    (do (log/debug data)
      [:<>
       (if doc
         [:div.ph1.mb4
          [:section.mt4-l
           [c/doc-card-lg doc]]
          [:hr.sec-hr-xl]
          [:section
           [:h3.tc.primary.mt0 "Related Content"]
           [:div.flex.flex-wrap.justify-center.ph1.mw8.center
            (for [sibling related
                  :when   (not= sibling doc)]
                 ^{:key sibling}
                 [c/doc-card
                  sibling])]]]
         [:div.vh-100 [:h2.mt7.tc "Loading..."]])])))

(defn content-list-ui [data]
  (let [{:keys [content topic]} data]
    [:<>
     (if content
       [:div
        [:article.mw7.center.tc.br2.pt5.pb3.mb
         [:h1.fw6.f3.lh-title.mt0.mb3.primary (str/upper-case (name topic))]]
        [:div.tc.mb2
         [w/button {:value "LATEST" :class ["primary mr3 ba"] :href "#"}]
         [w/button {:value "TRENDING" :class ["primary mr3 ba"] :href "#"}]
         [w/button {:value "FAVORITE" :class ["primary mr3 ba"] :href "#"}]]
        [:div.ph1.mb4.mw8.center
         [:section
          [:div.flex.flex-wrap.justify-center
           (for [doc content]
                ^{:key doc}
                [c/doc-card
                 doc])]]]]
       [:div.vh-100 [:h2.mt7.tc "Loading..."]])
     ]))
