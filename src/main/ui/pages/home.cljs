(ns ui.pages.home
  (:require [taoensso.timbre :as log]
            [bidi.bidi :refer [path-for]]
            [fast-twitch.nav :refer [cached-routes]]
            [ui.components.core :as c]
            [services.core :as s]))

(defn home-ui [data]
  ;; todo pass in content and iterate over it
  (let [document {:Identifier "africa" :Title "talk-is-cheap"}]
    [:<>
     [c/side-bar]
     [:div.center.mt0.mb5.mw8.pl5
      [:section
       [:h1.section-title "Title"]
       [:div.card-container
        [c/doc-card document]
        [c/doc-card document]
        [c/doc-card document]
        [c/doc-card document]
        [c/doc-card document]
        [c/doc-card document]]]]]))
