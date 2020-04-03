(ns ui.common
  (:require-macros [cljs.core.async.macros :refer [alt! go]])
  (:require [cljs.core.async :as async :refer [<! put! chan close! timeout]]
            [ui.ux.core :as ux]))

(defn ^:export initTranslate []
      (ux/google-translate-element-init "gtrnslt_el"))

(defn start []
      (doseq [js ["//use.fontawesome.com/releases/v5.12.1/js/all.js"
                  "//translate.google.com/translate_a/element.js?cb=ui.common.initTranslate"]]
        (go (ux/load-external-js js))))
