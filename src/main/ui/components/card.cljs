(ns ui.components.card
  (:require [taoensso.timbre :as log]
            [bidi.bidi :refer [path-for]]
            [clojure.string :as str]
            [fast-twitch.nav :refer [cached-routes]]
            [utils.core :as utils]))

(defn tags [tags]
  [:div.mdc-chip-set.m0
   (for [tag tags]
     ^{:key tag}
     [:button.mdc-chip.pv0.h-50
      [:span.mdc-chip__text.f7.primary.w-100 tag]])])

(defn doc-card
  [document]
  (let [{:keys [Title Identifier Slug UserID CreatedAt UpdatedAt Body Tags]} document]
    [:div.mdc-card.mh4.mv4.mw4-5
     [:input {:type "hidden" :name "topic" :value Identifier}]
     [:div
      [:img.w-100.h-auto
       {:alt "media image",
        :src "https://via.placeholder.com/350x150"}]
      [:div.ph4.pv2
       [:h2.f4.mb0 Title]
       [:p.mt0
        [:span.f7 UserID]
        [:span.f7 "  |  Created: " (utils/date-from-now CreatedAt) "  |  Updated: " (utils/date-format UpdatedAt)]]
       [:p.tw.f6 (utils/decode-base64 Body)] ;; The Body is Base64 Encoded and Needs to be Decoded
       [tags Tags]
       [:div.mdc-card__actions.mt2
        [:div.mdc-card__action-buttons.w-100
         [:a.mdc-button.mdc-button--raised.w-100
          {:href (path-for @cached-routes :document :topic Identifier :title Slug)}
          [:span.mdc-button__label "View Document"]]]]]]]))

(defn topic-card
  [topic]
  [:div.mdc-card.mh4.mv3.mw4-5
   [:div.ph5.pv2.tc
    [:h2 topic]
    [:p "Some very interesting details that would make one want to click."]
    [:div.mdc-card__actions
     [:div.mdc-card__action-buttons.w-100
      [:a.mdc-button.mdc-button--raised.w-100
       {:href (path-for @cached-routes :list-content-by-topic :topic topic)}
       [:span.mdc-button__label "View Topic"]]]]]])
