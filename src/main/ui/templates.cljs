(ns ui.templates
  (:require [taoensso.timbre :as log]
            [express.web-api :refer [path-for]]))

(defn doc-card []
 [:div.mdc-card.document-card
  [:div.mdc-card__primary-action
   [:div.document-card-content
    [:h2 "Document Title"]
    [:p "author: name"]
    [:div "body"]
    [:div.mdc-card__actions.spacer-top-25
     [:div.mdc-card__action-buttons
      [:button.mdc-button.mdc-card__action.mdc-card__action--button
       [:span.mdc-button__label "Read Article"]]]]]]])

(defn top-app-bar []
 [:header.mdc-top-app-bar
  {:id "app-bar"}
  [:div.mdc-top-app-bar__row
   [:section.mdc-top-app-bar__section.mdc-top-app-bar__section--align-start
    [:a.material-icons.mdc-top-app-bar__navigation-icon.mdc-icon-button
     {:href "#"}
     "menu"]
    [:a.mdc-top-app-bar__title {:href (path-for :home)} [:strong "KAMESTERY"]]]
   [:section.mdc-top-app-bar__section.mdc-top-app-bar__section--align-end
    {:role "toolbar"}
    [:a.material-icons.mdc-top-app-bar__action-item.mdc-icon-button
     {:href (path-for :login) :aria-label "Login"}
     "account_circle"]
    [:a.material-icons.mdc-top-app-bar__action-item.mdc-icon-button
     {:href "#" :aria-label "Register"}
     "person_add"]]]])

(defn side-bar []
 [:div.menu-container
  [:aside.mdc-drawer
   {:id "side-bar"}
   [:div.mdc-drawer__content.page-menu-content
    [:h3.side-bar-title "Topics"]
    [:ul.mdc-list
     [:li.mdc-list-item
      {:tab-index 0}
      [:span.mdc-list-item__text "history"]]
     [:li.mdc-list-item
      [:span.mdc-list-item__text "africa"]]
     [:li.mdc-list-item
      [:span.mdc-list-item__text "language"]]
     [:li.mdc-list-item
      [:span.mdc-list-item__text "education"]]]]]])

(defn default-template-ui [data]
  (let [{:keys [content script title]} data]
    [:html
     {:lang "en"}
     [:header
      [:meta {:charset "utf-8"}]
      [:meta {:http-equiv "content-type" :content "text/html; charset=UTF-8"}]
      [:title (str "KAMESTERY | " title)]]
     [:link {:rel "stylesheet" :href "/css/bundle.css"}]
     [:link {:rel "stylesheet" :href "https://fonts.googleapis.com/icon?family=Material+Icons"}]
     [:link {:rel "stylesheet", :href "https://fonts.googleapis.com/css?family=Roboto&display=swap"}]
     [:body
      [top-app-bar]
      [:div.main-container
       [side-bar]
       ;[:div {:id "app"} "JS Loading..."]
       content]
      [:footer
       [:div
        [:p
         {:dangerouslySetInnerHTML {:__html "Copyright &copy; 2019 OUCASTGEEK INC. All rights reserved."}}]]]
      [:script {:type "text/javascript" :src script}]
      [:script {:type "text/javascript" :src "/js/bundle.js"}]]]))

;;;;;;;;;;;;;;;;;;;;;; Widgets
(defn raw-str-widget-ui [data]
  (log/debug "Raw Stringing...")
  (let [{:keys [text]} data]
    ;;[:div text]
    [:div
     [:br]
     [:strong text]]))

(defn un-bouton-ui [data]
  (log/debug "Buttoning ...")
  (let [{:keys [text]} data]
    [:div
     [:hr]
     [:input
      {:type  "submit"
       :class "btn btn-default"
       :value text}]]))


(defn hello-ui [data]
  (log/debug "Rendering ...")
  (let [{:keys [upper-bound]} data]
    [:div
     "Hello world!"
     [:ul (for [n (range 1 upper-bound)]
            [:li {:key n} n])]
     [un-bouton-ui {:text "React!!"}]]))

(defn home-ui []
 [:div.content-container
  [:section
   [:h1.section-title "title"]
   [:div.card-container
    [doc-card]
    [doc-card]
    [doc-card]
    [doc-card]]]
  [:section
   [:h1.section-title "title"]
   [:div.card-container
    [doc-card]
    [doc-card]
    [doc-card]
    [doc-card]]]
  [:section
   [:h1.section-title "title"]
   [:div.card-container
    [doc-card]
    [doc-card]
    [doc-card]
    [doc-card]]]
  [:section
   [:h1.section-title "title"]
   [:div.card-container
    [doc-card]
    [doc-card]
    [doc-card]
    [doc-card]]]])

(defn login-ui [csrf-token]
; (let [{:keys [token]} csrf-token]
  [:div.content-container
   [:form.spacer-top-50 {:action "/user/authenticate", :method "post"}
    [:h2.center-text "Login"]
    [:input {:type "hidden", :name "_csrf", :value csrf-token}]
    [:div.mdc-text-field.mdc-text-field--box.email
     [:input.mdc-text-field__input {:type "email", :id "email-input", :name "email"}]
     [:label {:class "mdc-floating-label", :for "email-input"} "Email"]
     [:div {:class "mdc-line-ripple"}]]
    [:div {:class "mdc-text-field mdc-text-field--box password"}
     [:input {:type "password", :class "mdc-text-field__input", :id "password-input", :name "password" , :minlength "8"}]
     [:label {:class "mdc-floating-label", :for "password-input"} "Password"]
     [:div {:class "mdc-line-ripple"}]]
    [:div {:class "form-button-container"}
     [:button {:type "button", :class "mdc-button cancel"} "Cancel"]
     [:button {:type "submit" :class "mdc-button mdc-button--raised login"} "Login"]]]]
;  )
 )
