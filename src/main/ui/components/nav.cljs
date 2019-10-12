(ns ui.components.nav
  (:require [taoensso.timbre :as log]
            [bidi.bidi :refer [path-for]]
            [routing :refer [routing-data]]
            [services.core :as s]))

(defn top-app-bar []
  [:header.mdc-top-app-bar.z-999.top-0.left-0
   {:id "app-bar"}
   [:div.mdc-top-app-bar__row
    [:section.mdc-top-app-bar__section.mdc-top-app-bar__section--align-start
     [:a.material-icons.mdc-top-app-bar__navigation-icon.dn-l
      {:href "#" :id "menu-btn"}
      "menu"]
     [:a.mdc-top-app-bar__title.pl0-l
      {:href (path-for routing-data :home)}
      [:strong "KAMESTERY"]]]
    [:section.mdc-top-app-bar__section.mdc-top-app-bar__section--align-end
     {:role "toolbar"}
     [:a.material-icons.mdc-top-app-bar__action-item.mdc-icon-button
      {:href (path-for routing-data :login) :aria-label "Login"}
      "account_circle"]
     [:a.material-icons.mdc-top-app-bar__action-item.mdc-icon-button
      {:href (path-for routing-data :register) :aria-label "Register"}
      "person_add"]]]])

(defn menu-list [topics]
  [:<>
   [:div.mdc-drawer__header
    [:h3.mt3.mr0.mb0.mt4 "user-name"]
    [:h6.mdc-drawer__subtitle "user@example.com"]]
   [:div.mdc-drawer__content.mt3
    [:h3.mt3.mr0.mb1.ml3 "Topics"]
    [:div.ph3.mv2.mw4-5 {:class "mdc-text-field mdc-text-field--with-trailing-icon"}
     [:input {:type "text", :id "my-input", :class "mdc-text-field__input"}]
     [:label {:for "my-input", :class "mdc-floating-label"} "Find a topic"]
     [:i {:class "material-icons mdc-text-field__icon", :tabindex "0", :role "button"} "search"]
     [:div {:class "mdc-line-ripple"}]]
    [:div.mdc-list
     (for [t topics]
       ^{:key t}
       [:a.mdc-list-item.mv0
        {:href (path-for routing-data :list-content-by-topic :topic t)}
        [:span.mdc-list-item__text t]])]]])

(defn menu-mobile [topics]
  [:aside.mdc-drawer.mdc-drawer--dismissible.mdc-top-app-bar--fixed-adjust.dn-l.bg-near-white
   [menu-list topics]])

(defn menu-fixed [topics]
  [:div.dn.db-l
   [:aside.mdc-drawer.mdc-top-app-bar--fixed-adjust.fixed.bg-near-white
    {:id "side-bar-fixed"}
    [menu-list topics]]])


(defn menu []
  (let [topics (s/topics)]
    [:<>
     [menu-mobile topics]
     [menu-fixed topics]]))

