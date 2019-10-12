(ns ui.core
  (:require [taoensso.timbre :as log]
            [bidi.bidi :as bidi :refer [path-for]]
            [bidi.router :as br]
            [routing :refer [routing-data]]
            [reagent.core :as r]
            [re-frame.core :as rf :refer [subscribe dispatch]]
            [ui.pages.core :as p]))

(enable-console-print!)

;;;; Event Handlers

(rf/reg-event-db
 :page
 [(when ^boolean goog.DEBUG re-frame.core/debug)]   ;;  <----  conditional!
 (fn [db [_ new-page]]
   (assoc db :page new-page )))

;;;; Subcription Handlers

(rf/reg-sub
 :page
 (fn [db _]
   (:page db)))

;;;; Pages

(defmulti page identity)

(defmethod page :home []
  [p/home []])

(defmethod page :login []
  [p/login []])

(defmethod page :register []
  [p/register []])

(defmethod page :default []
  [p/home []])

;;;; Routing
(def router
  (br/start-router! routing-data
                    {:default-location {:handler :home}
                     :on-navigate #(dispatch [:page %])}))

;;;; Rendering
(defn current-page []
  (let [activep @(subscribe [:page])]
    (println "Current Page: " activep)
    (br/set-location! router {:handler activep})
    (page activep)))

(defn start []
  (do
    (rf/dispatch-sync [:page :home])
    (r/render-component
       [current-page]
       (.getElementById js/document "main-content"))))

(start)
