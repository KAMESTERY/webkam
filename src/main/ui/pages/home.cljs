(ns ui.pages.home
  (:require [taoensso.timbre :as log]
            [bidi.bidi :refer [path-for]]
            [fast-twitch.nav :refer [cached-routes]]
            [clojure.string :as str]
            [ui.components.core :as c]))

(defn- hero []
[:div.bg-white.stl-bg.near-white
 [:article.mw7.center.tc.br2.pt5.pb5.mb4
  [:h1.fw6.f3.lh-title.mt0.mb3 "Welcome to Kamestery!"]
  [:h2.fw2.f4.lh-copy.mt0.mb3.center.mw6
   "We want you to be involved. This text needs to\n    be longer for testing sake."]
  [:p.fw1.f5.mt0.mb4 "Sign up for beta access or learn more about x."]
  [:div
   [:a.f6.br-pill.bg-primary.no-underline.ba.b--purple.grow.pv2.ph3.dib.mr3
    {:href "#"}
    "Sign Up"]
   [:a.f6.br-pill.bg-light.no-underline.ba.b--purple.grow.pv2.ph3.dib.mr3.primary
    {:href "#"}
    "Learn More"]]]])

(defn home-ui [data]
  (let [topics (keys data)]
    [:<>
     [hero]
     (for [topic topics]
       ^{:key topic}
       [:div.center.mt0.pb3.mw8
        [:section
         [:h2.tc.primary.mv0 (str/capitalize (name topic))]
         [:div.flex.flex-wrap.justify-center.ph1
          (for [doc (-> data topic)]
            ^{:key doc}
            [c/doc-card doc])
          [:hr.sec-hr-xs]]]])]))
