(ns ui.core
  (:require-macros [cljs.core.async.macros :refer [alt! go go-loop]])
  (:require [cljs.core.async
             :as    async
             :refer [<! take! put! chan close! timeout pipeline-async to-chan]]
            [taoensso.timbre :as log]
            [bidi.bidi :as bidi :refer [path-for
                                        match-route*]]
            [routing :refer [routing-data]]
            [reagent.core :as r]
            [re-frame.core :as rf :refer [subscribe dispatch]]
            [services.uisvc :as uisvc :refer [<fetch]]
            [ui.pages.core :as p]))

(enable-console-print!)

;;;; Event Handlers

(rf/reg-event-db
 :current-page
 [(when ^boolean goog.DEBUG re-frame.core/debug)]   ;;  <----  conditional!
 (fn [db [_ current-page-data]]
   (let [{page :page data :data} current-page-data]
     (assoc db :page page :data data))))

;;;; Subcription Handlers


(rf/reg-sub
 :current-page
 (fn [db _]
   (select-keys db [:page :data])))

;;;; Pages

;; UI

(defmulti page (fn [m] (:page m)))

(defmethod page :home [m]
  [p/home (:data m)])

(defmethod page :login [m]
  [p/login []])

(defmethod page :register [m]
  [p/register []])

(defmethod page :list-content-by-topic [m]
  [p/content (:data m)])

(defmethod page :document [m]
           [p/document (:data m)])

(defmethod page :default [m]
  [p/home (:data m)])

;; Rendering

(defmulti render-page (fn [m] (:handler m)))

(defmethod render-page :home [m]
  (go
    (let [data (<! (<fetch :home-json))]
      (rf/dispatch [:current-page {:page :home
                                   :data data}])
      )))

(defmethod render-page :list-content-by-topic [m]
           (go
             (let [res (<! (<fetch :list-content-by-topic-json :topic (-> m :route-params :topic)))
                   data {:content res :topic topic}]
                  (rf/dispatch [:current-page {:page :list-content-by-topic
                                               :data data}])
                  )))

(defmethod render-page :list-content-by-tag [m]
           (go
             (let [data (<! (<fetch :list-content-by-tag-json :tag (-> m :route-params :tag)))]
                  (rf/dispatch [:current-page {:page :list-content-by-tag
                                               :data data}])
                  )))

(defmethod render-page :document [m]
           (go
             (let [{:keys [title topic]} (:route-params m)
                   data (<! (<fetch :document-json :title title :topic topic))]
                  (rf/dispatch [:current-page {:page :document
                                               :data data}])
                  )))

(defmethod render-page :login [m]
  (rf/dispatch [:current-page {:page :login}]))

(defmethod render-page :register [m]
  (rf/dispatch [:current-page {:page :register}]))

(defmethod render-page :default [m]
  (go
    (let [data (<! (<fetch :home-json))]
      (rf/dispatch [:current-page {:page :home
                                   :data data}])
      )))

;;;; Location

(defn current-route []
  (let [path (.. js/window -location -pathname)
        route (match-route* routing-data path {:request-method :get})]
     (println "Matched Route: " route)
    (or
     route {:handler :home})))

(defn start []
  (go
    (<! (render-page
         (current-route)))
    (r/render-component
     [page @(subscribe [:current-page])]
     (.getElementById js/document "main-content"))))

(start)

