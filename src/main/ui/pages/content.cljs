(ns ui.pages.content
  (:require [taoensso.timbre :as log]
            [ui.components.core :as c]
            [bidi.bidi :refer [path-for]]
            [fast-twitch.nav :refer [cached-routes]]
            [utils.core :as utils]))

(defn document-ui [data]
  (let [{:keys [doc related]} data
        {:keys [Title Body]} doc]
    (do (log/debug data)
      [:div.content-container
       [:section.article-main.mdc-layout-grid.spacer-top-25
        [:div.article-container.mdc-layout-grid__inner
         [:div.mdc-layout-grid__cell.mdc-layout-grid__cell--span-8]
         [:div.article-content.mdc-layout-grid__cell.mdc-layout-grid__cell--span-8
          [:div#article-header-img
           [:img.responsive-image
            {:alt "media image",
             :src "https://via.placeholder.com/750x350"}]]
          [:h1 Title]
          [:p (utils/decode-base64 Body)]]]]
       [:hr.section-divider-50]
       [:section
        [:h3.section-title "Related Content"]
        [:div.card-container
         (for [sibling related :when (not= sibling doc)]
           ^{:key sibling}
           [c/doc-card sibling])]]
       ])))

(defn content-list-ui [data]
  (let []
    [:div.content-container
     [:section
      [:h3.section-title "Topic"]
      [:div.card-container
       (for [doc data]
         ^{:key doc}
         [c/doc-card doc])]]]))

