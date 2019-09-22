(ns ui.components.content
  (:require [taoensso.timbre :as log]
            [ui.components.common :as common]
            [bidi.bidi :refer [path-for]]
            [express.web-api :refer [cached-routes]]))

(defn document-ui [document]
  (let [{:keys [title body]} document]
    [:div.content-container
     [:section.article-main.mdc-layout-grid.spacer-top-25
      [:div.article-container.mdc-layout-grid__inner
       [:div.mdc-layout-grid__cell.mdc-layout-grid__cell--span-8]
       [:div.article-content.mdc-layout-grid__cell.mdc-layout-grid__cell--span-8
        [:div#article-header-img
         [:img.responsive-image
          {:alt "media image",
           :src "https://via.placeholder.com/750x350"}]]
        [:h1 title]
        [:p body]]]]
     [:hr.section-divider-50]
     [:section
      [:h3.section-title "Related Content"]
      [:div.card-container
       [common/doc-card]
       [common/doc-card]
       [common/doc-card]
       [common/doc-card]]]]))