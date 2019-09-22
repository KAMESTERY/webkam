(ns ui.components.common
  (:require [taoensso.timbre :as log]
            [bidi.bidi :refer [path-for]]
            [express.web-api :refer [cached-routes]]))

(defn top-app-bar []
  [:header.mdc-top-app-bar.z-999.top-0.left-0
   [:div.mdc-top-app-bar__row
    [:section.mdc-top-app-bar__section.mdc-top-app-bar__section--align-start
     [:a.material-icons.mdc-top-app-bar__navigation-icon.mdc-icon-button
      {:href "#"}
      "menu"]
     [:a.mdc-top-app-bar__title {:href (path-for @cached-routes :home)} [:strong "KAMESTERY"]]]
    [:section.mdc-top-app-bar__section.mdc-top-app-bar__section--align-end
     {:role "toolbar"}
     [:a.material-icons.mdc-top-app-bar__action-item.mdc-icon-button
      {:href (path-for @cached-routes :login) :aria-label "Login"}
      "account_circle"]
     [:a.material-icons.mdc-top-app-bar__action-item.mdc-icon-button
      {:href (path-for @cached-routes :register) :aria-label "Register"}
      "person_add"]]]])

(defn side-bar []
 (let [link {:class ["mv6" "mh0"]}]
  [:div.flex
   [:aside.mdc-drawer.pl2.pt2
    [:div.mdc-drawer__content.fixed
     [:h3.mt3.mr0.mb0.ml3 "Topics"]
     [:ul.mdc-list
      [:li.mdc-list-item
       (merge {:tab-index 0} link)
       [:span.mdc-list-item__text "history"]]
      [:li.mdc-list-item
       link
       [:span.mdc-list-item__text "africa"]]
      [:li.mdc-list-item
       link
       [:span.mdc-list-item__text "language"]]
      [:li.mdc-list-item
       link
       [:span.mdc-list-item__text "tachyons"]]]
     [:hr.section-divider-10]
     [:h3.mt3.mr0.mb0.ml3 "Tags"]
     [:ul.mdc-list
      [:li.mdc-list-item.mv2.mh0
       {:tab-index 0}
       [:span.mdc-list-item__text "trending"]]
      [:li.mdc-list-item.mv2.mh0
       [:span.mdc-list-item__text "pan-african"]]
      [:li.mdc-list-item.mv2.mh0
       [:span.mdc-list-item__text "latest"]]
      [:li.mdc-list-item.mv2.mh0
       [:span.mdc-list-item__text "wubba lubba dub dub"]]]]]]))

(defn footer []
 [:footer.pa4.pa5-l.white-50.bt.b--black-10.absolute.bg-dark-gray.z-9999
  [:section {:class "cf mb5"}
   [:div.mb4.mb0-ns.w-100.w-50-l.fr
    [:a.white-50.f3.f2-ns.fw6.tl.link.dim.dib.pv3.mt2.mb4.mb0-l {, :href "mailto:hello@impossible.com"} "mail@kamestery.com"]]
   [:div.mb4.mb0-ns.fl.w-100.w-50-l
    [:p.white.f4.fw6.mb2.f6.mt0 "Sign up for our newsletter."]
    [:input {:placeholder "Email Address", :class "mw-100 w-100 w5-ns f5 input-reset ba b--black-20 pv3 ph4 border-box"}]
    [:input {:type "submit", :class "input-reset w-100 w-auto-ns bg-black-80 white f5 pv2 pv3-ns ph4 ba b--black-80 bg-hover-mid-gray"}]]]
  [:div.dt.dt--fixed.w-100
   [:div.dn.dtc-ns.v-mid
    [:p.f6.white-50.dib.pr3.mb3
     {:dangerouslySetInnerHTML {:__html "Copyright &copy; 2019 OUCASTGEEK INC. All rights reserved."}}]]
   [:div.db.dtc-ns.black-70.v-mid
    [:a {:href "https://www.facebook.com/", :class "link dim dib mr3 white-50", :title "Impossible Labs on Facebook"}
     [:svg.db.w2.h2 {, :data-icon "facebook", :viewbox "0 0 32 32", :fill "currentColor"}
      [:title "facebook icon"]
      [:path {:d "M8 12 L13 12 L13 8 C13 2 17 1 24 2 L24 7 C20 7 19 7 19 10 L19 12 L24 12 L23 18 L19 18 L19 30 L13 30 L13 18 L8 18 z"}]]]
    [:a {:href "https://twitter.com/", :class "link dim dib mr3 white-50"}
     [:svg.db.w2.h2 {, :data-icon "twitter", :viewbox "0 0 32 32", :fill "currentColor"}
      [:title "twitter icon"]
      [:path {:d "M2 4 C6 8 10 12 15 11 A6 6 0 0 1 22 4 A6 6 0 0 1 26 6 A8 8 0 0 0 31 4 A8 8 0 0 1 28 8 A8 8 0 0 0 32 7 A8 8 0 0 1 28 11 A18 18 0 0 1 10 30 A18 18 0 0 1 0 27 A12 12 0 0 0 8 24 A8 8 0 0 1 3 20 A8 8 0 0 0 6 19.5 A8 8 0 0 1 0 12 A8 8 0 0 0 3 13 A8 8 0 0 1 2 4"}]]]
    [:a {:href "https://medium.com/", :class "link dim dib mr3 white-50", :title "Impossible Labs on Medium"}
     [:svg.db.w2.h2 {, :x "0px", :y "0px", :viewbox "0 0 290 248.6", :fill "currentColor"}
      [:g
       [:path {:fill "currentColor", :class "st0", :d "M287.8,46.3L196,0.3c-0.4-0.2-0.9-0.3-1.3-0.3c0,0-0.1,0-0.1,0c-1.1,0-2.2,0.6-2.8,1.5l-56.6,92l63.2,102.7 l90.4-146.9C289.4,48.3,289,46.8,287.8,46.3z"}]
       [:polygon {:fill "currentColor", :points "105.2,61.2 105.2,160.3 193.3,204.4 \t"}]
       [:path {:fill "currentColor", :d "M201,208.2l80.9,40.5c4.4,2.2,8,0,8-5v-180L201,208.2z"}]
       [:path {:fill "currentColor", :d "M95.5,46.7L10.7,4.3L5.4,1.7C4.6,1.3,3.8,1.1,3.2,1.1c-0.9,0-1.7,0.4-2.3,1.1C0.3,2.8,0,3.8,0,5v193.4 c0,3.3,2.4,7.2,5.4,8.7l83.3,41.6c1.2,0.6,2.3,0.9,3.3,0.9c2.8,0,4.8-2.2,4.8-5.8V48.7C96.7,47.8,96.2,47.1,95.5,46.7z"}]]]]
    [:a {:href "https://www.linkedin.com/company/", :class "link dim dib white-50"}
     [:svg.db.w2.h2 {, :x "0px", :y "0px", :viewbox "0 0 48 48"}
      [:lineargradient {:gradientunits "userSpaceOnUse", :x1 "23.9995", :y1 "0", :x2 "23.9995", :y2 "48.0005"}
       [:stop {:offset "0"}]
       [:stop {:offset "1"}]]
      [:path {:fill "currentColor", :d "M48,42c0,3.313-2.687,6-6,6H6c-3.313,0-6-2.687-6-6V6 c0-3.313,2.687-6,6-6h36c3.313,0,6,2.687,6,6V42z"}]
      [:g
       [:g
        [:path {:fill "#FFFFFF", :d "M15.731,11.633c-1.608,0-2.658,1.083-2.625,2.527c-0.033,1.378,1.018,2.494,2.593,2.494 c1.641,0,2.691-1.116,2.691-2.494C18.357,12.716,17.339,11.633,15.731,11.633z M13.237,35.557h4.988V18.508h-4.988V35.557z M31.712,18.748c-1.595,0-3.222-0.329-4.956,2.36h-0.099l-0.087-2.599h-4.417c0.065,1.411,0.074,3.518,0.074,5.52v11.529h4.988 v-9.854c0-0.46,0.065-0.919,0.196-1.248c0.328-0.919,1.149-1.871,2.527-1.871c1.805,0,2.527,1.411,2.527,3.479v9.494h4.988V25.439 C37.455,20.713,34.993,18.748,31.712,18.748z"}]]]]]]]])

(defn default-template-ui [data]
  (let [{:keys [content script title]} data]
    [:html
     {:lang "en"}
     [:header
      [:meta {:charset "utf-8"}]
      [:meta {:http-equiv "content-type" :content "text/html; charset=UTF-8"}]
      [:meta {:content "width=device-width, initial-scale=1.0", :name "viewport"}]
      [:title (str "KAMESTERY | " title)]]
     [:link {:rel "stylesheet" :href "/css/bundle.css"}]
     [:link {:rel "stylesheet" :href "https://fonts.googleapis.com/icon?family=Material+Icons"}]
     [:link {:rel "stylesheet", :href "https://fonts.googleapis.com/css?family=Roboto&display=swap"}]
     [:link {:rel "stylesheet", :href "https://unpkg.com/tachyons@4.10.0/css/tachyons.min.css"}]
     [:body
      [top-app-bar]
      [:div.pt5.vh-100
       [side-bar]
       content
       [footer]]
      [:script {:type "text/javascript" :src script}]
      [:script {:type "text/javascript" :src "/js/bundle.js"}]]]))

;;;;;;;;;;;;;;;;;;;;;; Widgets
(defn raw-str-widget-ui [data]
  (log/debug "Raw Stringing...")
  (let [{:keys [text]} data]
    ;;[:div text]
    [:div
     [:br]
     [:strong text]]))

(defn un-bouton-ui [data]
  (log/debug "Buttoning ...")
  (let [{:keys [text]} data]
    [:div
     [:hr]
     [:input
      {:type  "submit"
       :class "btn btn-default"
       :value text}]]))


(defn hello-ui [data]
  (log/debug "Rendering ...")
  (let [{:keys [upper-bound]} data]
    [:div
     "Hello world!"
     [:ul (for [n (range 1 upper-bound)]
            [:li {:key n} n])]
     [un-bouton-ui {:text "React!!"}]]))

(defn doc-card []
  [:div.mdc-card.document-card
   [:div
    [:div.document-card-content
     [:h2 "Document Title"]
     [:p "author: name"]
     [:div "body"]
     [:div.mdc-card__actions.spacer-top-25
      [:div.mdc-card__action-buttons
       [:a.mdc-button.mdc-card__action.mdc-card__action--button {:href (path-for @cached-routes :document :topic "foo" :title "bar")}
        [:span.mdc-button__label "Read Article"]]]]]]])