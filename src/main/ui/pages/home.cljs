(ns ui.pages.home
  (:require [taoensso.timbre :as log]
            [bidi.bidi :refer [path-for]]
            [fast-twitch.nav :refer [cached-routes]]
            [ui.components.core :as c]
            [services.core :as s]))

(defn home-ui [data]
  (let [topics (keys data)]
    [:<>
     [c/side-bar]
     (for [topic topics]
       ^{:key topic}
       [:div.center.mt0.mb5.mw8.pl5
        [:section
         [:h1.section-title "Title"]
         [:div.card-container
          (for [doc (-> data topic)]
            ^{:key doc}
            [c/doc-card doc])]]])]))

