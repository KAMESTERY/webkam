(ns ui.templates.common
  (:require [taoensso.timbre :as log]
            [express.web-api :refer [path-for]]))

(defn top-app-bar []
  [:header.mdc-top-app-bar
   {:id "app-bar"}
   [:div.mdc-top-app-bar__row
    [:section.mdc-top-app-bar__section.mdc-top-app-bar__section--align-start
     [:a.material-icons.mdc-top-app-bar__navigation-icon.mdc-icon-button
      {:href "#"}
      "menu"]
     [:a.mdc-top-app-bar__title {:href (path-for :home)} [:strong "KAMESTERY"]]]
    [:section.mdc-top-app-bar__section.mdc-top-app-bar__section--align-end
     {:role "toolbar"}
     [:a.material-icons.mdc-top-app-bar__action-item.mdc-icon-button
      {:href (path-for :login) :aria-label "Login"}
      "account_circle"]
     [:a.material-icons.mdc-top-app-bar__action-item.mdc-icon-button
      {:href (path-for :register) :aria-label "Register"}
      "person_add"]]]])

(defn side-bar []
  [:div.menu-container
   [:aside.mdc-drawer
    {:id "side-bar"}
    [:div.mdc-drawer__content.page-menu-content
     [:h3.side-bar-title "Topics"]
     [:ul.mdc-list
      [:li.mdc-list-item
       {:tab-index 0}
       [:span.mdc-list-item__text "history"]]
      [:li.mdc-list-item
       [:span.mdc-list-item__text "africa"]]
      [:li.mdc-list-item
       [:span.mdc-list-item__text "language"]]
      [:li.mdc-list-item
       [:span.mdc-list-item__text "education"]]]]]])

(defn default-template-ui [data]
  (let [{:keys [content script title]} data]
    [:html
     {:lang "en"}
     [:header
      [:meta {:charset "utf-8"}]
      [:meta {:http-equiv "content-type" :content "text/html; charset=UTF-8"}]
      [:title (str "KAMESTERY | " title)]]
     [:link {:rel "stylesheet" :href "/css/bundle.css"}]
     [:link {:rel "stylesheet" :href "https://fonts.googleapis.com/icon?family=Material+Icons"}]
     [:link {:rel "stylesheet", :href "https://fonts.googleapis.com/css?family=Roboto&display=swap"}]
     [:body
      [top-app-bar]
      [:div.main-container
       [side-bar]
       ;[:div {:id "app"} "JS Loading..."]
       content]
      [:footer
       [:div
        [:p
         {:dangerouslySetInnerHTML {:__html "Copyright &copy; 2019 OUCASTGEEK INC. All rights reserved."}}]]]
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

