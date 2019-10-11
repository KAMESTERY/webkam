(ns ui.templates.default
  (:require [taoensso.timbre :as log]
            [routing :refer [path-css path-js]]
            [ui.components.core :as c]
            [fast-twitch.preload :refer [quick-css quick-img quick-js]]))

(defn default-template-ui [data]
  (let [{:keys [content script title]} data]
    [:html
     {:lang "en"}
     [:header
      [:meta {:charset "utf-8"}]
      [:meta {:http-equiv "content-type" :content "text/html; charset=UTF-8"}]
      [:meta {:content "width=device-width, initial-scale=1.0", :name "viewport"}]
      [:title (str "KAMESTERY | " title)]]

     ;; Preloads
     [quick-css "//fonts.googleapis.com/icon?family=Material+Icons"]
     [quick-css "//fonts.googleapis.com/css?family=Roboto&display=swap"]
     ;;[quick-css "//unpkg.com/tachyons@4.10.0/css/tachyons.min.css"]
     [quick-css "//unpkg.com/tachyons/css/tachyons.min.css"] ;; always latest version
     [quick-css (path-css "bundle.css")]
     [quick-js (path-js "bundle.js")]

     ;; Style
     [:link {:rel "stylesheet" :href "//fonts.googleapis.com/icon?family=Material+Icons"}]
     [:link {:rel "stylesheet" :href "//fonts.googleapis.com/css?family=Roboto&display=swap"}]
     ;; [:link {:rel "stylesheet" :href "//unpkg.com/tachyons@4.10.0/css/tachyons.min.css"}]
     [:link {:rel "stylesheet" :href "//unpkg.com/tachyons/css/tachyons.min.css"}] ;; always latest version
     [:link {:rel "stylesheet" :href (path-css "bundle.css")}]

     [:body
      [c/top-app-bar]
      [c/menu]
;      [:div.mdc-drawer-scrim]
      [:div.pt4.flex-auto.relative.mdc-top-app-bar--fixed-adjust
       [:main.overflow-auto.h-100.menu-adjust {:id "main-content"} content]
       [c/footer "&copy; 2019" "OUCASTGEEK INC." "All rights reserved."]]
      [:script {:type "text/javascript" :src (path-js "bundle.js")}]
      [:script {:type "text/javascript" :src script}]
      ]]))
