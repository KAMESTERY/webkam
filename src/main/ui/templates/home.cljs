(ns ui.templates.home
  (:require [taoensso.timbre :as log]
            [express.web-api :refer [path-for]]
            [ui.templates.common :as common]))

(defn doc-card []
 [:div.mdc-card.document-card
  [:div
   [:div.document-card-content
    [:h2 "Document Title"]
    [:p "author: name"]
    [:div "body"]
    [:div.mdc-card__actions.spacer-top-25
     [:div.mdc-card__action-buttons
      [:a.mdc-button.mdc-card__action.mdc-card__action--button {:href (path-for :home)}
       [:span.mdc-button__label "Read Article"]]]]]]])

(defn home-ui []
 [:div.content-container
  [:section
   [:h1.section-title "title"]
   [:div.card-container
    [doc-card]
    [doc-card]
    [doc-card]
    [doc-card]]]
  [:section
   [:h1.section-title "title"]
   [:div.card-container
    [doc-card]
    [doc-card]
    [doc-card]
    [doc-card]]]
  [:section
   [:h1.section-title "title"]
   [:div.card-container
    [doc-card]
    [doc-card]
    [doc-card]
    [doc-card]]]
  [:section
   [:h1.section-title "title"]
   [:div.card-container
    [doc-card]
    [doc-card]
    [doc-card]
    [doc-card]]]])