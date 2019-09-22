(ns services.usersvc
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

;(defn remove-claims! []
;  (remove-item! auth-key))

(defn logged-in? []
  (let [claims (get-claims)]
    (boolean
     (and
      (:email claims)
      (:role claims)
      (:token claims)
      (:userId claims)))))

(defn authenticate [data]
  (let [{:keys [email password]} data]
    (do
      (log/debug email)
      (log/debug password)
      (set-claims! (get-claims)))))

(defn enroll [data]
  (do
    (log/debug (str "ENROLLING:: " data))
    {:user_id "dev_user"
     :email "user@mail.com"
     :username "user"
     :role 1
     :confirmed 1
     :lastseen ""
     :password "password"
     :confirm_password "password"}))