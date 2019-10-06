(ns ui.components.card
  (:require [taoensso.timbre :as log]
            [bidi.bidi :refer [path-for]]
            [clojure.string :as str]
            [fast-twitch.nav :refer [cached-routes]]
            [utils.core :as utils]))

(defn tags [tags]
  [:div.mdc-chip-set.ma0.pa0.invisible-scrollbar.mht-1
   (for [tag tags]
     ^{:key tag}
     [:button.mdc-chip.pv0.h-50
      [:span.mdc-chip__text.f7.primary.w-100.b (str "#" tag)]])])

(defn icon-button [icon label]
  [:button.mdc-button
   [:i.material-icons.mdc-button__icon {:aria-label label} icon]
   [:span.mdc-button__label label]])

(defn doc-card
  [document]
  (let [{:keys [Title Identifier Slug UserID CreatedAt UpdatedAt Body Tags]} document]
    [:div.mdc-card.mv4.mw4-5.mh2
     [:input {:type "hidden" :name "topic" :value Identifier}]
     [:div
      [:img.w-100.h-auto
       {:alt "media image",
        :src "https://via.placeholder.com/350x150"}]
      [:div.ph3.pv2
       [:h2.f4.mb0 Title]
       [:p.mt0
        [:span.f7 UserID]
        [:span.f7
         "  |  Created: " (utils/date-from-now CreatedAt)
         "  |  Updated: " (utils/date-format UpdatedAt)]]
       [:p.tw.f6 (utils/decode-base64 Body)] ;; The Body is Base64 Encoded and Needs to be Decoded
       [tags Tags]
       [:hr.sec-hr-xs]
       [:div.mdc-card__actions.mt2.pa0
        [:div.mdc-card__action-buttons.w-100
         [:a.mdc-button.mdc-button--raised.w-100
          {:href (path-for @cached-routes :document :topic Identifier :title Slug)}
          [:span.mdc-button__label "View Document"]]]]]]]))

(defn doc-card-lg [document]
  (let [{:keys [Title Identifier Slug UserID CreatedAt UpdatedAt Body Tags]} document]
    [:div.mdc-card.mw7.center.mt5
     [:div
      [:img.w-100.h-auto
       {:alt "media image",
        :src "https://via.placeholder.com/750x300"}]
      [:div.ph3.pv3
       [:h2.f2.mt0.mb1 Title]
       [:p.mt0.mb2
        [:a.f6.gray {:href "#"} UserID]
        [:span.f6.gray
         "  |  Created: "
         (utils/date-from-now CreatedAt)
         "  |  Updated: "
         (utils/date-format UpdatedAt)]]
       [:div
        [icon-button "audiotrack" "Audio"]
        [icon-button "play_arrow" "Video"]]
       [:div [:p.tw.f5.mv3 (utils/decode-base64 Body)]] ;; The Body is Base64 Encoded and Needs to be Decoded
       [:hr.sec-hr-sm]
       [tags Tags]]]]))

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
