(ns ui.pages.home
  (:require [clojure.string :as str]
            [taoensso.timbre :as log]
            [bidi.bidi :refer [path-for]]
            [routing :refer [routing-data]]
            [ui.widgets.core :as w]
            [ui.components.core :as c]
            [utils.content :as c-util]))

(defn- hero []
  [:div.near-white.stl-bg {:id "hero"}
   [:article.mw7.center.tc.br2.pt5.pb3
    [:h1.fw6.f3.lh-title.mt0.mb3 "Welcome to Kamestery!"]
    [:h2.fw2.f4.lh-copy.mt0.mb3.center.mw6
     "We want you to be involved. This text needs to\n    be longer for testing sake."]
    [:p.fw1.f5.mt0.mb4 "Sign up for beta access or learn more about x."]
    [:div
     [w/button {:value "SIGN UP" :class ["mr3 bg-primary"] :href (path-for routing-data :register)}]
     [w/button {:value "LEARN MORE" :class ["near-white mr3 ba"] :href "#"}]]]])

(defn home-ui [data]
  (let [topics (keys data)]
    [:<>
     [hero]
     [:section {:class "cf w-100 pa2-ns"}
      (if topics
        (for [topic topics]
          ^{:key topic}
          (for [doc (-> data topic)]
            ^{:key doc}
            (let [{:keys [Title Identifier Slug UserID CreatedAt UpdatedAt Body Tags Media Topic]} doc
                  header-image (first (c-util/get-tag Media c-util/header-img-tag))
                  path-to-doc (path-for routing-data :document :topic Identifier :title Slug)]
              [:article {:class "fl w-100 w-50-m  w-25-ns pa2-ns"}
               [:div.aspect-ratio.aspect-ratio--1x1
                (if (empty? header-image)
                  [:a {:href path-to-doc}
                   [:img
                    {:alt   "media image"
                     :class "db bg-center cover aspect-ratio--object"
                     :src   "//via.placeholder.com/750x300"}]]
                  [:a {:href path-to-doc}
                   [:img
                    {:alt   (c-util/media-title (:MediaID header-image) Topic)
                     :class "db bg-center cover aspect-ratio--object"
                     :src   (:FileUrl header-image)}]])]
               [:a {:href  path-to-doc
                    :class "ph2 ph0-ns pb3 link db"}
                [:h3 {:class "f5 f4-ns mb0 primary"} Title]
                [:h3 {:class "f6 f5 fw4 mt2 primary"} Identifier]]])))
        [:div.vh-100 [:h2.mt7.tc "Loading..."]])]]))


