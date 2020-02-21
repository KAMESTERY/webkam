(ns ui.widgets.img
  (:require [utils.core :as u]))

(defn img [input-attr]
  [:img (u/override-attr input-attr ["w-100"])])