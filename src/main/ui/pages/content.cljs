(ns ui.pages.content
  (:require [taoensso.timbre :as log]
            [ui.components.core :as c]
            [bidi.bidi :refer [path-for]]
            [fast-twitch.nav :refer [cached-routes]]))

(defn document-ui [data]
  (let [{:keys [Title Body]} data
        document {:Title "africa" :Identifier "talk-is-cheap"}]
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
          [:p Body]]]]
       [:hr.section-divider-50]
       [:section
        [:h3.section-title "Related Content"]
        [:div.card-container
         [c/doc-card document]
         [c/doc-card document]
         [c/doc-card document]
         [c/doc-card document]]]])))

(defn content-list-ui [data]
  (let []
    (do (log/debug "DATA FROM CONTENT LIST UI:::" data)
      [:div.content-container
       [:section
        [:h3.section-title "Topic"]
        [:div.card-container
         (for [doc data]
           ^{:key doc}
           [c/doc-card doc])]]])))

