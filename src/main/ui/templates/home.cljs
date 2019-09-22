(ns ui.templates.home
  (:require [taoensso.timbre :as log]
            [bidi.bidi :refer [path-for]]
            [express.web-api :refer [cached-routes]]
            [ui.templates.common :as common]))

(defn home-ui []
 [:div.center.mt0.mb5.mw8.pl5
  [:section
   [:h1.section-title "title"]
   [:div.card-container
    [common/doc-card]
    [common/doc-card]
    [common/doc-card]
    [common/doc-card]
    [common/doc-card]
    [common/doc-card]]]
  [:section
   [:h1.section-title "title"]
   [:div.card-container
    [common/doc-card]
    [common/doc-card]
    [common/doc-card]
    [common/doc-card]
    [common/doc-card]
    [common/doc-card]]]
  [:section
   [:h1.section-title "title"]
   [:div.card-container
    [common/doc-card]
    [common/doc-card]
    [common/doc-card]
    [common/doc-card]
    [common/doc-card]
    [common/doc-card]]]
  [:section
   [:h1.section-title "title"]
   [:div.card-container
    [common/doc-card]
    [common/doc-card]
    [common/doc-card]
    [common/doc-card]
    [common/doc-card]
    [common/doc-card]]]])