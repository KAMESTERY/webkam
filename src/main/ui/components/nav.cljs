(ns ui.components.nav
  (:require [taoensso.timbre :as log]
            [bidi.bidi :refer [path-for]]
            [ui.widgets.core :as w]
            [routing :refer [routing-data]]
            [services.core :as s]))

(defn top-app-bar [data]
      [:header.mdc-top-app-bar.z-999.top-unset.left-0
       {:id "app-bar"}
       [:div.mdc-top-app-bar__row
        [:section.mdc-top-app-bar__section.mdc-top-app-bar__section--align-start
         [:a.material-icons.mdc-top-app-bar__navigation-icon
          {:href "#" :id "menu-btn"}
          "menu"]
         [:a.mdc-top-app-bar__title.pl0-l.ml3
          {:href (path-for routing-data :home)}
          [:strong "KAMESTERY"]]]
        [:section.mdc-top-app-bar__section.mdc-top-app-bar__section--align-end
         {:role "toolbar"}
         [w/user-actions data]]]])

(defn menu-list [topics]
      [:<>
       [:div.mdc-drawer__header
        [:h3.mt3.mr0.mb0.mt4 "user-name"]
        [:h6.mdc-drawer__subtitle "user@example.com"]]
       [:div.mdc-drawer__header
        [:h3.mt3.mr0.mb0.mt4 "Language"]
        [::h6.mdc-drawer__subtitle {:id "gtrnslt_el"}]]
       [:div.mdc-drawer__content.mt3
        [:h3.mt3.mr0.mb1.ml3 "Topics"]
        [w/search-input "topic-menu-search" "Find a topic" ["ph3" "mv2" "mw4-5"]]
        [:div.mdc-list
         (for [t topics]
              ^{:key t}
              [:a.mdc-list-item.mv0
               {:href (path-for routing-data :list-content-by-topic :topic t)}
               [:span.mdc-list-item__text t]])]]])

(defn menu-mobile [topics]
      [:aside.mdc-drawer.mdc-drawer--dismissible.mdc-top-app-bar--fixed-adjust.w-30-l.w-40-m.w-100.z-index-200
       [menu-list topics]])

(defn menu-fixed [topics]
      [:div
       [:aside.mdc-drawer.mdc-top-app-bar--fixed-adjust.fixed
        {:id "side-bar-fixed"}
        [menu-list topics]]])

(defn menu []
      (let [topics (s/topics)]
           [:<>
            [menu-mobile topics]]))
;[menu-fixed topics]]))

