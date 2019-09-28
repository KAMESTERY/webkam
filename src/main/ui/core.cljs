(ns ui.core
    (:require [reagent.core :as r]
              [ui.widgets.core :as w]))

(enable-console-print!)

(defn start []
  (r/render-component
   [w/hello-ui {:upper-bound 8}]
   (.getElementById js/document "app")))

(start)
