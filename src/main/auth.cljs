(ns auth
  (:require-macros [fast-twitch.macros :as m])
  (:require [fast-twitch.web-api :as web]))

(defn is-authenticated? [req]
      (let [auth-cookie-kw (keyword (m/env-var "AUTH"))
            auth-cookie-val (get-in req [:cookies auth-cookie-kw])]
           (not-empty auth-cookie-val)))

(defn check-auth [req func]
      (if (is-authenticated? req)
        (func req)
        (web/redirect :login)))
