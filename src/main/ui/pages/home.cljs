(ns ui.pages.home
  (:require [taoensso.timbre :as log]
            [bidi.bidi :refer [path-for]]
            [fast-twitch.nav :refer [cached-routes]]
            [clojure.string :as str]
            [ui.components.core :as c]))

(defn- hero []
  [:article.mw7.center.ph3.ph5-ns.tc.br2.pv5.mb4.stl-bg
   [:h1.fw6.f3.f2-ns.lh-title.mt0.mb3 "Welcome to Kamestery!"]
   [:h2.fw2.f4.lh-copy.mt0.mb3
    "We want you to be involved. This text needs to\n    be longer for testing sake."]
   [:p.fw1.f5.mt0.mb3 "Sign up for beta access or learn more about x."]
   [:div
    [:a.mdc-button.mdc-button--raised.cta-bg.f6.grow.pv2.ph3.dib.mr3
     {:href "#"}
     "Sign Up"]
    [:a.mdc-button.mdc-button--outlined.f6.grow.pv2.ph3.dib.mr3
     {:href "#"}
     "Learn More"]]])

(defn home-ui [data]
  (let [topics (keys data)]
    [:<>
     [hero]
     (for [topic topics]
       ^{:key topic}
       [:div.center.mt0.pb4.mw8.pt3
        [:section
         [:h2.tc.primary (str/capitalize (name topic))]
         [:div.flex.flex-wrap.justify-center
          (for [doc (-> data topic)]
            ^{:key doc}
            [c/doc-card doc])]]])]))
