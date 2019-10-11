(ns ui.core
  (:require [reagent.core :as r]
            [re-frame.core :as rf :refer [subscribe dispatch]]
            [bidi.bidi :as bidi :refer [path-for]]
            [bidi.router :as br]
            [routing :refer [routing-data]]
            [fast-twitch.nav :refer [cached-routes]]
            [ui.widgets.core :as w]
            [ui.components.core :as c]
            [ui.pages.core :as p]
            [ui.templates.core :as t]))

(enable-console-print!)

;;;; Client DB

(def client-db
  {})

(rf/reg-event-db
 ::initialize-db
 (fn  [_ _]
   client-db))

;;;; Events

(rf/reg-event-db              ;; sets up initial application state
 :initialize                 ;; usage:  (dispatch [:initialize])
 (fn [_ _]                   ;; the two parameters are not important here, so use _
   {:time (js/Date.)         ;; What it returns becomes the new application state
    :time-color "#f88"}))    ;; so the application state will initially be a map with two keys

(rf/reg-event-db
 :page
 (fn [_ current-page]
   (do
     (assoc client-db :page current-page)
     ;;(br/set-location! cached-routes {:handler current-page})
     )))

(rf/reg-event-db
 :page
 (fn [_ current-page]
   (do
     (assoc client-db :page current-page)
     ;;(br/set-location! cached-routes {:handler current-page})
     )))

;;;; Pages

;; Determines current route
(defmulti current-page #(client-db :page))

(defmethod current-page :home
  []
  [p/home []])

(defmethod current-page :default
  []
  [p/home []])

;;;; Routing

;; (br/start-route! cached-routes
;;                  :default-location {:handler :home}
;;                  :on-navigate #(current-page {:page %}))

(defn- parse-url [url]
  (bidi/match-route routing-data url))



(defn start []
  (rf/dispatch-sync [:page :home])
  (rf/dispatch [:home-data])
  (r/render
   [current-page]
   (.getElementById js/document "main-content")))

(start)
