(ns ui.widgets.user_actions
  (:require [clojure.string :as str]
            [taoensso.timbre :as log]
            [bidi.bidi :refer [path-for]]
            [routing :refer [routing-data]]
            [utils.core :as utils]))


(defn user-actions []
  [:<>
   [:a.material-icons.mdc-top-app-bar__action-item.mdc-icon-button
    {:href (path-for routing-data :login) :aria-label "Login"}
    "account_circle"]
   [:a.material-icons.mdc-top-app-bar__action-item.mdc-icon-button
    {:href (path-for routing-data :register) :aria-label "Register"}
    "person_add"]])