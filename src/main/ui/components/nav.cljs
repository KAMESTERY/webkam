(ns ui.components.nav
  (:require [taoensso.timbre :as log]
            [bidi.bidi :refer [path-for]]
            [services.core :as s]
            [fast-twitch.nav :refer [cached-routes]]))

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
  (let [link {:class ["" "mh0"]}
        topics (s/topics)]
    [:div.flex
     [:aside.mdc-drawer.pl2.pt2
      [:div.mdc-drawer__content.fixed
       [:h3.mt3.mr0.mb0.ml3 "Topics"]

       [:nav.mdc-list
        (for [t topics] ^{:key t}
                        [:a.mdc-list-item
                         (merge {:href (path-for @cached-routes :list-content-by-topic :topic t)} link)
                         [:span.mdc-list-item__text t]])]

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

