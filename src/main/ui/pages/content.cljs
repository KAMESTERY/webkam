(ns ui.pages.content
  (:require [taoensso.timbre :as log]
            [ui.components.core :as c]
            [bidi.bidi :refer [path-for]]
            [clojure.string :as str]
            [fast-twitch.nav :refer [cached-routes]]
            [utils.core :as utils]))


(defn tags [tags]
  [:div.mdc-chip-set.m0
   (for [tag tags]
     ^{:key tag}
     [:button.mdc-chip.pv0.h-50.ml0
      [:span.mdc-chip__text.f7.primary.w-100 tag]])])

(defn icon-button [icon label]
  [:button.mdc-fab.mdc-fab--extended.bg-primary.h-50.mr2 {:aria-label label}
   [:span.mdc-fab__icon.material-icons icon]
   [:span.mdc-fab__label label]]
  )

(defn document-ui [data]
  (let [{:keys [doc related]} data
        {:keys [Title Identifier Slug UserID CreatedAt UpdatedAt Body Tags]} doc]
    (do (log/debug data)
        [:<>
         [:div
          [:section.mt4.mw-vw-100
           [:div.mdc-card.mh2
            [:div
             [:img.w-100.h-auto
              {:alt "media image",
               :src "https://via.placeholder.com/750x300"}]
             [:div.ph4.pv3
              [:h2.f2.mb2 Title]
              [:p.mt0.mb4
               [:span.f5.gray UserID]
               [:span.f5.gray "  |  Created: " (utils/date-from-now CreatedAt) "  |  Updated: " (utils/date-format UpdatedAt)]]
              [:div.mv2
               [icon-button "audiotrack" "Audio"]
               [icon-button "play_arrow" "Video"]]
              [:div [:p.tw.f5.mt4.mb4 (utils/decode-base64 Body)]] ;; The Body is Base64 Encoded and Needs to be Decoded
              [tags Tags]]]]]
          [:section.mv3.pv3.subtl-bg
           [:h3.tc.primary "Related Content"]
           [:div
            (for [sibling related :when (not= sibling doc)]
              ^{:key sibling}
              [c/doc-card sibling])]]]
         ])))

(defn content-list-ui [data]
  (let [{:keys [content topic]} data]
    [:<>
     ;     [c/side-bar]
     [:div.mv5.mw-vw-100
      [:section
       [:h2.tc.primary (str/capitalize (name topic))]
       [:div.flex.flex-wrap.justify-center
        (for [doc content]
          ^{:key doc}
          [c/doc-card
           doc])]]]]))
