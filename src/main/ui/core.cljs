(ns ui.core
    (:require [reagent.core :as r]
              [ui.templates :as tmpl]))

(enable-console-print!)

(defn start []
  (r/render-component
   [tmpl/hello-ui {:upper-bound 8}]
   (.getElementById js/document "app")))

(start)
