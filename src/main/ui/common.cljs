(ns ui.common
  (:require-macros [cljs.core.async.macros :refer [alt! go]])
  (:require [cljs.core.async :as async :refer [<! put! chan close! timeout]]
            [ui.ux.core :as ux]))

(defn ^:export initTranslate []
      (ux/google-translate-element-init "gtrnslt_el"))

(defn ^:export initBLMBadge [] ;; work in progress, need to call this once script is loaded
      (ux/blm-badge-init))

(defn start []
      (doseq [js ["//use.fontawesome.com/releases/v5.12.1/js/all.js"
                  "//translate.google.com/translate_a/element.js?cb=ui.common.initTranslate"]]
                  ;; "//makerbadge.s3.amazonaws.com/blmbadge.js"]] work in progress]]
        (go (ux/load-external-js js))))
