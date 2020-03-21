(ns ui.pages.home
  (:require [clojure.string :as str]
            [taoensso.timbre :as log]
            [bidi.bidi :refer [path-for]]
            [routing :refer [routing-data]]
            [ui.widgets.core :as w]
            [ui.components.core :as c]))

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
     (if topics
       [:section.flex.flex-wrap.cf.w-100.pa2-ns
        (for [topic topics]
          ^{:key topic}
          (for [doc (-> data topic)]
            ^{:key doc}
            [c/doc-preview doc]))]
       [:div.vh-100 [:h2.mt7.tc "Loading..."]])]))


