(ns ui.components.card
  (:require [taoensso.timbre :as log]
            [bidi.bidi :refer [path-for]]
            [fast-twitch.nav :refer [cached-routes]]))

(defn doc-card
  [document]
  (let [{:keys [Title Identifier]} document]
    [:div.mdc-card.document-card
     [:input {:type "hidden" :name "topic" :value Identifier}]
     [:div
      [:div.document-card-content
       [:h2 Title]
       [:p "author: name"]
       [:div "body"]
       [:div.mdc-card__actions.spacer-top-25
        [:div.mdc-card__action-buttons
         [:a.mdc-button.mdc-card__action.mdc-card__action--button
          {:href (path-for @cached-routes :document :topic Identifier :title Title)}
          [:span.mdc-button__label "Read Article"]]]]]]]))