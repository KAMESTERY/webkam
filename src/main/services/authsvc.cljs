(ns services.authsvc
  (:require [taoensso.timbre :as log]))

(def auth-key (str "KAM_AUTH_STORAGE_KEY"))

;; claims
(defn get-claims []
  {:token  "xxx-yyy-zzz"
   :userId "user"
   :email  "user@mail.com"
   :role   1})


(defn set-claims! [claims]
  (log/debug (str "STORING CLAIMS::::" claims)))

(defn remove-claims! []
  (remove-item! auth-key))

(defn logged-in? []
  (let [claims (get-claims)]
    (boolean
     (and
      (:email claims)
      (:role claims)
      (:token claims)
      (:userId claims)))))
