(ns ui.templates.default
  (:require [taoensso.timbre :as log]
            [tick.alpha.api :as t]
            [routing :refer [path-css path-js]]
            [fast-twitch.preload :refer [quick-css quick-img quick-js
                                         preload-styles preload-scripts
                                         load-styles load-scripts]]
            [ui.components.core :as c]))

(defn default-template-ui [data]
      (let [{:keys [content scripts styles title]} data]
           [:html
            {:lang "en"}
            [:header
             [:meta {:charset "utf-8"}]
             [:meta {:http-equiv "content-type" :content "text/html; charset=UTF-8"}]
             [:meta {:content "width=device-width, initial-scale=1.0", :name "viewport"}]
             [:title (str "KAMESTERY | " title)]


             ;; Preloads
             [quick-css (path-css "bundle.css")]
             [quick-css "//fonts.googleapis.com/icon?family=Material+Icons"]
             [quick-css "//fonts.googleapis.com/css?family=Roboto&display=swap"]
             ;;[quick-css "//unpkg.com/tachyons@4.10.0/css/tachyons.min.css"]
             [quick-css "//unpkg.com/tachyons/css/tachyons.min.css"] ;; always latest version

             (preload-styles styles)

             [quick-js (path-js "bundle.js")]

             (preload-scripts scripts)

             ;; Style
             [:link {:rel "stylesheet" :href (path-css "bundle.css")}]
             [:link {:rel "stylesheet" :href "//fonts.googleapis.com/icon?family=Material+Icons"}]
             [:link {:rel "stylesheet" :href "//fonts.googleapis.com/css?family=Roboto&display=swap"}]
             ;; [:link {:rel "stylesheet" :href "//unpkg.com/tachyons@4.10.0/css/tachyons.min.css"}]
             [:link {:rel "stylesheet" :href "//unpkg.com/tachyons/css/tachyons.min.css"}] ;; always latest version
             (load-styles styles)]

            [:body
             [c/top-app-bar]
             [c/menu]
             [:div.pt4.flex-auto.relative.mdc-top-app-bar--fixed-adjust
              [:main.overflow-auto.h-100 {:id "main-content"} content]]

             [c/footer "&copy; 2019" "OUCASTGEEK INC." "All rights reserved."]
             [:script {:type "text/javascript" :src (path-js "bundle.js")}]
             [:script {:type "text/javascript" :src script}]

             (load-scripts scripts)
             ]]
           ))

