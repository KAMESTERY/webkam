(ns ui.components.doc
  (:require [clojure.string :as str]
            [taoensso.timbre :as log]
            [bidi.bidi :refer [path-for]]
            [routing :refer [routing-data]]
            [ui.widgets.core :as w]
            [utils.content :as c-util]))

(defn doc-preview [doc]
  (let [{:keys [Title Identifier Slug UserID CreatedAt UpdatedAt Body Tags Media Topic]} doc
        header-image (first (c-util/get-tag Media c-util/header-img-tag))
        path-to-doc (path-for routing-data :document :topic Identifier :title Slug)]
    [:article {:class "fl w-100 w-50-m w-25-ns pa2-ns"}
     [:div.aspect-ratio.aspect-ratio--16x9
      (if (empty? header-image)
        [:a {:href path-to-doc}
         [:img
          {:alt   "media image"
           :class "db bg-center fit-cover aspect-ratio--object"
           :src   "//via.placeholder.com/750x300"}]]
        [:a {:href path-to-doc}
         [:img
          {:alt   (c-util/media-title (:MediaID header-image) Topic)
           :class "db bg-center fit-cover aspect-ratio--object"
           :src   (:FileUrl header-image)}]])]
     [:a.ph2.ph0-ns.pb3.link.db {:href path-to-doc}
      [:h3 {:class "f6 f4-ns mb0 primary"} Title]
      [:span.mdc-chip.pv0.mt2.h-50
       [:span.mdc-chip__text.f7.primary.w-100.b Identifier]]]]))

(defn doc-preview-list [docs]
  [:section.cf.w-100.pa2-ns.flex.flex-wrap.justify-center
   (for [doc docs]
     ^{:key doc}
     [doc-preview doc])])