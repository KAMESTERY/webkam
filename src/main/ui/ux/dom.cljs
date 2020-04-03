(ns ui.ux.dom
  (:require [goog.dom :as gdom]))

(defn load-external-js [url]
      (let [head (. (gdom/getElementsByTagName gdom/TagName.HEAD) (item 0))
            script (gdom/createDom gdom/TagName.SCRIPT (clj->js {:src url}))]
           (gdom/appendChild head script)))
