(ns ui.core
  (:require-macros [cljs.core.async.macros :refer [alt! go go-loop]])
  (:require [cljs.core.async
             :as    async
             :refer [<! take! put! chan close! timeout pipeline-async to-chan]]
            [taoensso.timbre :as log]
            [bidi.bidi :as bidi :refer [path-for]]
            [bidi.router :as br]
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
   (go
     (let [{page :page data-chan :data-chan} current-page-data
           page-data (<! data-chan)]
       (println "New Page Data: " page-data)
       (assoc db :page page :page-data @d)))
   db))

;; (rf/reg-event-db
;;  :current-page
;;  [(when ^boolean goog.DEBUG re-frame.core/debug)]   ;;  <----  conditional!
;;  (fn [db [_ current-page-data]]
;;    (let [d (atom nil)]
;;      (go
;;        (let [{page :page data-chan :data-chan} current-page-data
;;              page-data (<! data-chan)]
;;          (println "New Page Data: " page-data)
;;          (reset! d page-data)))
;;      (while (nil? d)
;;        (js/setTimeout (fn []) 10))
;;      (assoc db :page page :page-data @d))))

;;;; Effects Handlers

(rf/reg-event-fx
 :render-page
 [(when ^boolean goog.DEBUG re-frame.core/debug)]   ;;  <----  conditional!
 (fn [cofx [_ page]]
   (let [fetch-target (-> (name page)
                          (str "-json")
                          keyword)]
     {:dispatch [:current-page {:page page :data-chan (<fetch fetch-target)}]}
     )))

;;;; Subcription Handlers


(rf/reg-sub
 :current-page
 (fn [db _]
   (select-keys db [:page :page-data])))

;;;; Pages

(defmulti page identity)

(defmethod page :home [data]
  (println "Home Data: " data)
  [p/home data])

(defmethod page :login []
  [p/login []])

(defmethod page :register []
  [p/register []])

(defmethod page :default []
  [p/home []])

;;;; Routing
;; (def router
;;   (br/start-router! routing-data
;;                     {:default-location {:handler :home}
;;                      :on-navigate #(dispatch [:render-page %])}))

;;;; Rendering
(defn current-page [data]
  (let [{activep :page page-data :page-data} @data]
    (println "Current Page: " activep)
    ;;(br/set-location! router {:handler activep})
    [page activep page-data]))

(defn start []
  (do
    (rf/dispatch-sync [:render-page :home])
    (r/render-component
     [current-page (subscribe [:current-page])]
     (.getElementById js/document "main-content"))))

(start)

