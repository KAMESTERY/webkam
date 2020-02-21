(ns utils.local-storage
  (:require [clojure.edn :as edn]))

(def auth-user-key (str "AUTH_USER_STORAGE_KEY"))


(defn set-item!
  "Set `key' in browser's localStorage to `val`."
  [key val]
  (-> js/window
      (.-localStorage)
      (.setItem key val)))

(defn get-item
  "Returns value of `key' from browser's localStorage."
  [key]
  (-> js/window
      (.-localStorage)
      (.getItem  key)
      (edn/read-string))) ;; edn string in local storage

(defn remove-item!
  "Remove the browser's localStorage value for the given `key`"
  [key]
  (-> js/window
      (.-localStorage)
      (.removeItem key)))

(def auth-user-key (str "AUTH_USER_STORAGE_KEY"))

(defn set-user! [auth]
  (set-item! auth-user-key auth))

(defn get-user []
  (get-item auth-user-key))

(defn remove-user! []
  (remove-item! auth-user-key))
