(ns ui.components.card
  (:require [clojure.string :as str]
            [taoensso.timbre :as log]
            [bidi.bidi :refer [path-for]]
            [routing :refer [routing-data]]
            [ui.widgets.core :as w]
            [utils.content :as c]
            [utils.core :as utils]))

(defn flat-card [document]
      (let [{:keys [Title Identifier Slug UserID CreatedAt UpdatedAt Body Tags Media Topic]} document
            header-image (first (c/get-tag Media c/header-img-tag))
            path-to-doc (path-for routing-data :document :topic Identifier :title Slug)]
           [:article {:class "fl w-100 w-50-m  w-25-ns pa2-ns"}
            [:div.aspect-ratio.aspect-ratio--16x9
             (if (empty? header-image)
               [:a {:href path-to-doc}
                [:img
                 {:alt   "media image"
                  :class "db bg-center cover aspect-ratio--object"
                  :src   "//via.placeholder.com/750x300"}]]
               [:a {:href path-to-doc}
                [:img
                 {:alt   (c/media-title (:MediaID header-image) Topic)
                  :class "db bg-center cover aspect-ratio--object"
                  :src   (:FileUrl header-image)}]])]
            [:a {:href  path-to-doc
                 :class "ph2 ph0-ns pb3 link db"}
             [:h3 {:class "f5 f4-ns mb0 primary"} Title]
             [:span.mdc-chip.pv0.mt2.h-50
              [:span.mdc-chip__text.f7.primary.w-100.b Identifier]]]]))

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
                 {:alt   "media image"
                  :class ["fit-cover mx-h-10"]
                  :src   "//via.placeholder.com/750x300"}]
                [w/image
                 {:alt   (c/media-title (:MediaID header-image) Topic)
                  :class ["fit-cover mx-h-10"]
                  :src   (:FileUrl header-image)}])]
             [:div.ph4.pv2
              [:h2.f4.mb0 Title]
              [:p.mt0
               [:span.f7 UserID]
               [:span.f7
                "  |  Created: " (utils/date-from-now CreatedAt)
                "  |  Updated: " (utils/date-format UpdatedAt)]]
              [:p.tw.f6.min-h-4 (concat
                                  (take 150 (utils/decode-base64 Body)) ;; The Body is Base64 Encoded and Needs to be Decoded
                                  "...")]
              [w/tags Tags]
              [:hr.sec-hr-xs]
              [:div.mdc-card__actions.mt2.pa0
               [:div.mdc-card__action-buttons.w-100
                [:a.mdc-button.mdc-button--raised.w-100
                 {:href (path-for routing-data :document :topic Identifier :title Slug)}
                 [:span.mdc-button__label "View Document"]]]]]]]))

(defn video-media [media]
  (let [video (first (c/get-tag media c/video-tag))
        video-iframe (first (c/get-tag media c/video-iframe-tag))]
    (cond
      (not-empty video) (w/video (:FileUrl video))
      (not-empty video-iframe) (w/video-iframe (:FileUrl video-iframe)))))

(defn doc-card-lg [document]
      (let [{:keys [Title Identifier Slug UserID CreatedAt UpdatedAt Body Tags Media Topic]} document
            header-image (first (c/get-tag Media c/header-img-tag))
            video (video-media Media)]
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
                "  |  Created: " (utils/date-from-now CreatedAt)
                "  |  Updated: " (utils/date-format UpdatedAt)]]
              ;[:div
              ; [w/icon-button "audiotrack" "Audio"]
              ; [w/icon-button "play_arrow" "Video"]]
              [:div.pre-line
               [:p.tw.f5.mv3 (utils/decode-base64 Body)]
               video]   ;; The Body is Base64 Encoded and Needs to be Decoded
              [:hr.sec-hr-sm]
              [w/tags Tags]]]]))
