(ns ui.components.card
  (:require [clojure.string :as str]
            [taoensso.timbre :as log]
            [bidi.bidi :refer [path-for]]
            [routing :refer [routing-data]]
            [ui.widgets.core :as w]
            [utils.content :as c]
            [utils.core :as utils]))

(defn flat-card [document]
      (let [{:keys [title identifier slug userID createdAt updatedAt body tags media topic]} document
            header-image (first (c/get-tag media c/header-img-tag))
            path-to-doc (path-for routing-data :document :topic identifier :title slug)]
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
                 {:alt   (c/media-title (:mediaID header-image) topic)
                  :class "db bg-center cover aspect-ratio--object"
                  :src   (:FileUrl header-image)}]])]
            [:a {:href  path-to-doc
                 :class "ph2 ph0-ns pb3 link db"}
             [:h3 {:class "f5 f4-ns mb0 primary"} title]
             [:span.mdc-chip.pv0.mt2.h-50
              [:span.mdc-chip__text.f7.primary.w-100.b identifier]]]]))

(defn doc-card
      [document]
      (let [{:keys [title identifier slug userID createdAt updatedAt body tags media topic]} document
            header-image (first (c/get-tag media c/header-img-tag))]
           [:div.mdc-card.mv3.w-40-ns.mh3-ns
            [:input {:type "hidden" :name "topic" :value identifier}]
            [:div
             [:div
              (if (empty? header-image)
                [w/image
                 {:alt   "media image"
                  :class ["fit-cover mx-h-10"]
                  :src   "//via.placeholder.com/750x300"}]
                [w/image
                 {:alt   (c/media-title (:mediaID header-image) topic)
                  :class ["fit-cover mx-h-10"]
                  :src   (:fileUrl header-image)}])]
             [:div.ph4.pv2
              [:h2.f4.mb0 title]
              [:p.mt0
               [:span.f7 userID]
               [:span.f7
                "  |  Created: " (utils/date-from-now createdAt)
                "  |  Updated: " (utils/date-format updatedAt)]]
              [:p.tw.f6.min-h-4 (concat
                                  (take 150 (utils/decode-base64 body)) ;; The Body is Base64 Encoded and Needs to be Decoded
                                  "...")]
              [w/tags tags]
              [:hr.sec-hr-xs]
              [:div.mdc-card__actions.mt2.pa0
               [:div.mdc-card__action-buttons.w-100
                [:a.mdc-button.mdc-button--raised.w-100
                 {:href (path-for routing-data :document :topic identifier :title slug)}
                 [:span.mdc-button__label "View Document"]]]]]]]))

(defn video-media [media]
  (let [video (first (c/get-tag media c/video-tag))
        video-iframe (first (c/get-tag media c/video-iframe-tag))]
    (cond
      (not-empty video) (w/video (:fileUrl video))
      (not-empty video-iframe) (w/video-iframe (:fileUrl video-iframe)))))

(defn doc-card-lg [document]
      (let [{:keys [title identifier slug userID createdAt updatedAt body tags media topic]} document
            header-image (first (c/get-tag media c/header-img-tag))
            video (video-media media)]
        [:div.mdc-card.mw7.center.mt4.mt5-ns
            [:div
             [:div
              (if (empty? header-image)
                [w/image
                 {:alt "media image"
                  :src "//via.placeholder.com/750x300"}]
                [:img.w-100
                 {:alt (c/media-title (:mediaID header-image) topic)
                  :src (:fileUrl header-image)}])]
             [:div.ph4.pv3
              [:h2.f2.mt0.mb1 title]
              [:p.mt0.mb2
               [:a.f6.gray {:href "#"} userID]
               [:span.f6.gray
                "  |  Created: " (utils/date-from-now createdAt)
                "  |  Updated: " (utils/date-format updatedAt)]]
              ;[:div
              ; [w/icon-button "audiotrack" "Audio"]
              ; [w/icon-button "play_arrow" "Video"]]
              [:div.pre-line
               [:p.tw.f5.mv3 (utils/decode-base64 body)]
               video]   ;; The Body is Base64 Encoded and Needs to be Decoded
              [:hr.sec-hr-sm]
              [w/tags tags]]]]))
