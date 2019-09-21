(ns services.usersvc
  (:require [taoensso.timbre :as log]
            [services.authsvc :as authsvc]))

(defn authenticate [data]
  (let [{:keys [email password]} data]
    (do
      (log/debug email)
      (log/debug password)
      (authsvc/set-claims! (authsvc/get-claims)))))

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