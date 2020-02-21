(ns ui.components.card
  (:require [clojure.string :as str]
            [taoensso.timbre :as log]
            [bidi.bidi :refer [path-for]]
            [routing :refer [routing-data]]
            [ui.widgets.core :as w]
            [utils.content :as c]
            [utils.core :as utils]))


(defn doc-card
  [document]
  (let [{:keys [Title Identifier Slug UserID CreatedAt UpdatedAt Body Tags Media Topic]} document
        header-image (first (c/get-tag Media c/header-img-tag))]
    [:div.mdc-card.mv3.w-40-ns.mh3-ns
     [:input {:type "hidden" :name "topic" :value Identifier}]
     [:div
      [:div
       (if (empty? header-image)
         [w/image
          {:alt "media image"
           :class ["fit-cover mx-h-10"]
           :src "//via.placeholder.com/750x300"}]
         [w/image
          {:alt (c/media-title (:MediaID header-image) Topic)
           :class ["fit-cover mx-h-10"]
           :src (:FileUrl header-image)}])]
      [:div.ph4.pv2
       [:h2.f4.mb0 Title]
       [:p.mt0
        [:span.f7 UserID]
        [:span.f7
         "  |  Created: " (utils/date-from-now CreatedAt)
         "  |  Updated: " (utils/date-format UpdatedAt)]]
       [:p.tw.f6.min-h-4 (concat
                           (take 150 (utils/decode-base64 Body))  ;; The Body is Base64 Encoded and Needs to be Decoded
                           "...")]
       [w/tags Tags]
       [:hr.sec-hr-xs]
       [:div.mdc-card__actions.mt2.pa0
        [:div.mdc-card__action-buttons.w-100
         [:a.mdc-button.mdc-button--raised.w-100
          {:href (path-for routing-data :document :topic Identifier :title Slug)}
          [:span.mdc-button__label "View Document"]]]]]]]))

(defn doc-card-lg [document]
  (let [{:keys [Title Identifier Slug UserID CreatedAt UpdatedAt Body Tags Media Topic]} document
        header-image (first (c/get-tag Media c/header-img-tag))]
    [:div.mdc-card.mw7.center.mt4.mt5-ns
     [:div
      [:div
       (if (empty? header-image)
         [w/image
          {:alt "media image"
           :src "//via.placeholder.com/750x300"}]
         [:img.w-100
          {:alt (c/media-title (:MediaID header-image) Topic)
           :src (:FileUrl header-image)}])]
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
       [:div.pre-line
        [:p.tw.f5.mv3 (utils/decode-base64 Body)]]     ;; The Body is Base64 Encoded and Needs to be Decoded
       [:hr.sec-hr-sm]
       [w/tags Tags]]]]))
