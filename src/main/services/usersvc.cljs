(ns ui.services.usersvc
  (:require [taoensso.timbre :as log]))

(defn authenticate [data]
  (let [{:keys [email password]} data]
    (do
      (log/debug email)
      (log/debug password)
      {:token  "xxx-yyy-zzz"
       :userId "user"
       :email  "user@mail.com"
       :role   1})))

(defn enroll [data]
  (do
    (log/debug data)
    {:user_id "dev_user"
     :email "user@mail.com"
     :username "user"
     :role 1
     :confirmed 1
     :lastseen ""
     :password "password"
     :confirm_password "password"}))