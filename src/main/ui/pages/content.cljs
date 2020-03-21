(ns ui.pages.content
  (:require [clojure.string :as str]
            [taoensso.timbre :as log]
            [bidi.bidi :refer [path-for]]
            [routing :refer [routing-data]]
            [ui.components.core :as c]
            [ui.widgets.core :as w]
            [utils.core :as utils]))


(defn document-ui [data]
      (let [{:keys [doc related]} data]
           (do (log/debug data)
               [:<>
                (if doc
                  [:div.ph1.mb4
                   [:section.mt4-l
                    [c/doc-card-lg doc]]
                   [:h3.tc.primary.mt5 "Related Content"]
            [c/doc-preview-list (filter #(not= % doc) related)]]
                  [:div.vh-100 [:h2.mt7.tc "Loading..."]])])))

(defn content-list-ui [data]
  (let [{:keys [content topic]} data
        contains-topic? (some? topic)
        title (str/upper-case (if contains-topic? (name topic) "title"))]
    [:<>
     (if content
       [:div
        [:article.mw7.center.tc.br2.pt5.pb3.mb
         [:h1.fw6.f3.lh-title.mt0.mb3.primary title]]
        [:div.tc.mb2
         [w/button {:value "LATEST" :class ["primary mr3 ba"] :href "#"}]
         [w/button {:value "TRENDING" :class ["primary mr3 ba"] :href "#"}]
         [w/button {:value "FAVORITE" :class ["primary mr3 ba"] :href "#"}]]
        [c/doc-preview-list content]]
       [:div.vh-100 [:h2.mt7.tc "Loading..."]])]))

