(ns ui.templates.default
  (:require [taoensso.timbre :as log]
            [bidi.bidi :refer [path-for]]
            [ui.components.core :as c]
            ))

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
      [c/top-app-bar]
      [:div.pt5.vh-100
       content
       [c/footer]]
      [:script {:type "text/javascript" :src script}]
      [:script {:type "text/javascript" :src "/js/bundle.js"}]]]))