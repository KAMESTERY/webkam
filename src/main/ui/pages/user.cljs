(ns ui.pages.user
  (:require [taoensso.timbre :as log]
            [ui.components.core :as c]
            [bidi.bidi :refer [path-for]]
            [routing :refer [routing-data]]
            [ui.styles.core :as styles]))

;; TODO REFACTOR MDC TEXT INPUT INTO A WIDGET
(defn login-ui [data]
  (let [{:keys [csrf-token]} data
        text-field {:class (styles/text-field)}]
    [:div.vh-100
     [:form.mt5.ph3.w-40-ns.w-50-m.center
      {:action (path-for routing-data :authenticate), :method "post"}
      [:h2.tc "Login"]
      [:p (:msg data)]
      [:input {:type "hidden", :name "_csrf", :value csrf-token}]
      [:div text-field
       [:input.mdc-text-field__input {:type "email", :id "email-input", :name "email"}]
       [:label.mdc-floating-label {:for "email-input"} "Email"]
       [:div.mdc-line-ripple]]

      [:div text-field
       [:input.mdc-text-field__input
        {:type "password", :id "password-input", :name "password", :minLength "8"}]
       [:label.mdc-floating-label {:for "password-input"} "Password"]
       [:div.mdc-line-ripple]]

      [:div.flex.justify-end.center
       [:button.mdc-button {:type "button"} "Cancel"]
       [:button.mdc-button.mdc-button--raised.ml1 {:type "submit"} "Login"]]]]))

;; TODO REFACTOR MDC TEXT INPUT INTO A WIDGET
(defn register-ui [data]
  (let [{:keys [csrf-token]} data
        text-field {:class (styles/text-field)}]
    [:div.vh-100
     [:form.mt5.ph3.w-40-ns.w-50-m.center
      {:action (path-for routing-data :enroll), :method "post"}
      [:h2.tc "Register"]
      [:p (:msg data)]
      [:input {:type "hidden", :name "_csrf", :value csrf-token}]

      [:div text-field
       [:input.mdc-text-field__input {:type     "email"
                                      :id       "email-input"
                                      :name     "email"
                                      :required true}]
       [:label.mdc-floating-label {:for "email-input"} "Email"]
       [:div.mdc-line-ripple]]

      [:div text-field
       [:input.mdc-text-field__input {:type       "password"
                                      :id         "password-input"
                                      :name       "password"
                                      :required   true
                                      :min-length 8}]

       [:label.mdc-floating-label {:for "password-input"} "Password"]
       [:div.mdc-line-ripple]]

      [:div text-field
       [:input.mdc-text-field__input {:type       "password"
                                      :id         "confirm-password-input"
                                      :name       "confirm-password"
                                      :required   true
                                      :min-length 8}]
       [:label.mdc-floating-label {:for "confirm-password-input"} "Confirm Password"]
       [:div.mdc-line-ripple]]

      [:div.flex.justify-end.center
       [:button.mdc-button {:type "button"} "Cancel"]
       [:button.mdc-button.mdc-button--raised.ml1 {:type "submit"} "Register"]]]]))
