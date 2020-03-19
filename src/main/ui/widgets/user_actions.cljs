(ns ui.widgets.user_actions
  (:require [clojure.string :as str]
            [taoensso.timbre :as log]
            [bidi.bidi :refer [path-for]]
            [routing :refer [routing-data]]
            [utils.core :as utils]))


(defn user-actions [data]
      (let [{:keys [authenticated csrf-token]} data]
           (if authenticated
             [:<>
              [:a.material-icons.mdc-top-app-bar__action-item.mdc-icon-button
               {:href "#profile" :aria-label "Profile"}
               "person_pin"]
              [:form {:action (path-for routing-data :logout) :method "post"}
               [:input {:type "hidden", :name "_csrf", :value csrf-token}]
               [:button.material-icons.mdc-top-app-bar__action-item.mdc-icon-button
                {:type "submit" :aria-label "Logout"}
                "exit_to_app"]]]
             [:<>
              [:a.material-icons.mdc-top-app-bar__action-item.mdc-icon-button
               {:href (path-for routing-data :login) :aria-label "Login"}
               "account_circle"]
              [:a.material-icons.mdc-top-app-bar__action-item.mdc-icon-button
               {:href (path-for routing-data :register) :aria-label "Register"}
               "person_add"]])))
