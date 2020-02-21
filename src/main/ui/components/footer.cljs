(ns ui.components.footer
  (:require [taoensso.timbre :as log]
            [bidi.bidi :refer [path-for]]
            [routing :refer [routing-data]]
            [ui.widgets.core :as w]))

(defn footer [copyright company notice]
  [:footer.pa4.pa5-l.white-50.bt.b--black-10.absolute.z-999.bg-black.mt4
   [:section.cf.mb5
    [:div.mb4.mb0-ns.w-100.w-50-l.fr
     [:a.white-50.f3.f2-ns.fw6.tl.link.dim.dib.pv3.mt2.mb4.mb0-l {:href "mailto:hello@impossible.com"} "mail@kamestery.com"]]
    [:div.mb4.mb0-ns.fl.w-100.w-50-l
     [:p.white.f4.fw6.mb2.f6.mt0 "Sign up for our newsletter."]
     [:input {:placeholder "Email Address", :class "mw-100 w-100 w5-ns f5 input-reset ba b--black-20 pv3 ph4 border-box"}]
     [:input {:type "submit", :class "input-reset w-100 w-auto-ns bg-black-80 white f5 pv2 pv3-ns ph4 ba b--black-80 bg-hover-mid-gray"}]]]
   [:div.dt.dt--fixed.w-100
    [:div.dn.dtc-ns.v-mid
     [:p.f6.white-50.dib.pr3.mb3
      {:dangerouslySetInnerHTML {:__html (str copyright " " company " " notice)}}]]
    [:div.db.dtc-ns.black-70.v-mid
     [:a.link.dim.dib.mr3.white-50 {:href "https://www.facebook.com/", :title "Impossible Labs on Facebook"}
      [w/facebook-icon "currentColor"]]
     [:a {:href "https://twitter.com/", :class "link dim dib mr3 white-50"}
      [w/twitter-icon "currentColor"]]
     [:a {:href "https://medium.com/", :class "link dim dib mr3 white-50", :title "Impossible Labs on Medium"}
      [w/medium-icon "currentColor"]]
     [:a {:href "https://www.linkedin.com/company/", :class "link dim dib white-50"}
      [w/linkedin-icon "currentColor"]]]]])
