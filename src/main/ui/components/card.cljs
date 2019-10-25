(ns ui.components.card
  (:require [clojure.string :as str]
            [taoensso.timbre :as log]
            [bidi.bidi :refer [path-for]]
            [routing :refer [routing-data]]
            [ui.widgets.core :as w]
            [utils.core :as utils]))

(defn doc-card
  [document]
  (let [{:keys [Title Identifier Slug UserID CreatedAt UpdatedAt Body Tags]} document]
    [:div.mdc-card.mv3.w-40-ns.mh3-ns
     [:input {:type "hidden" :name "topic" :value Identifier}]
     [:div
      [w/image
       {:alt "media image",
        :src "//via.placeholder.com/300x100"}]
      [:div.ph4.pv2
       [:h2.f4.mb0 Title]
       [:p.mt0
        [:span.f7 UserID]
        [:span.f7
         "  |  Created: " (utils/date-from-now CreatedAt)
         "  |  Updated: " (utils/date-format UpdatedAt)]]
       [:p.tw.f6 (utils/decode-base64 Body)] ;; The Body is Base64 Encoded and Needs to be Decoded
       [w/tags Tags]
       [:hr.sec-hr-xs]
       [:div.mdc-card__actions.mt2.pa0
        [:div.mdc-card__action-buttons.w-100
         [:a.mdc-button.mdc-button--raised.w-100
          {:href (path-for routing-data :document :topic Identifier :title Slug)}
          [:span.mdc-button__label "View Document"]]]]]]]))

(defn doc-card-lg [document]
  (let [{:keys [Title Identifier Slug UserID CreatedAt UpdatedAt Body Tags]} document]
    [:div.mdc-card.mw7.center.mt4.mt5-ns
     [:div
      [w/image
       {:alt "media image",
        :src "//via.placeholder.com/750x300"}]
      [:div.ph4.pv3
       [:h2.f2.mt0.mb1 Title]
       [:p.mt0.mb2
        [:a.f6.gray {:href "#"} UserID]
        [:span.f6.gray
         "  |  Created: "
         (utils/date-from-now CreatedAt)
         "  |  Updated: "
         (utils/date-format UpdatedAt)]]
       [:div
        [w/icon-button "audiotrack" "Audio"]
        [w/icon-button "play_arrow" "Video"]]
       [:div [:p.tw.f5.mv3 (utils/decode-base64 Body)]] ;; The Body is Base64 Encoded and Needs to be Decoded
       [:hr.sec-hr-sm]
       [w/tags Tags]]]]))
