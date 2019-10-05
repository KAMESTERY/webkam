(ns ui.components.nav
  (:require [taoensso.timbre :as log]
            [bidi.bidi :refer [path-for]]
            [services.core :as s]
            [fast-twitch.nav :refer [cached-routes]]))

(defn top-app-bar []
  [:header.mdc-top-app-bar.z-999.top-0.left-0 {:id "app-bar"}
   [:div.mdc-top-app-bar__row
    [:section.mdc-top-app-bar__section.mdc-top-app-bar__section--align-start
     [:a.material-icons.mdc-top-app-bar__navigation-icon {:href "#" :id "menu-btn"} "menu"]
     [:a.mdc-top-app-bar__title {:href (path-for @cached-routes :home)} [:strong "KAMESTERY"]]]
    [:section.mdc-top-app-bar__section.mdc-top-app-bar__section--align-end
     {:role "toolbar"}
     [:a.material-icons.mdc-top-app-bar__action-item.mdc-icon-button
      {:href (path-for @cached-routes :login) :aria-label "Login"}
      "account_circle"]
     [:a.material-icons.mdc-top-app-bar__action-item.mdc-icon-button
      {:href (path-for @cached-routes :register) :aria-label "Register"}
      "person_add"]]]])

(defn menu []
  (let [topics (s/topics)]
    [:aside.mdc-drawer.mdc-drawer--dismissible.mdc-top-app-bar--fixed-adjust.bn
     [:div.mdc-drawer__content
      [:div.mdc-list
       [:h3.mt3.mr0.mb0.ml3 "Topics"]
       (for [t topics]
         ^{:key t}
         [:a.mdc-list-item
          {:href (path-for @cached-routes :list-content-by-topic :topic t)}
          [:span.mdc-list-item__text t]])]]]))
