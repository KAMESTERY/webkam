(ns ui.core
    (:require [reagent.core :as r]
              [ui.templates.common :as common]))

(enable-console-print!)

(defn start []
  (r/render-component
   [common/hello-ui {:upper-bound 8}]
   (.getElementById js/document "app")))

(start)
