(ns routing
  (:require [bidi.bidi :refer [path-for]]))

(def routing-data
  ["/"
   ;; public
   {""                             {:get
                                    {"" :home}}
    "home.json"                    {:get
                                    {"" :home-json}}
    ;; user
    "user/login"                   {:get
                                    {"" :login}}
    "user/logout"                   {:get
                                     {"" :logout}}
    "user/authenticate"            {:post
                                    {"" :authenticate}}
    "user/register"                {:get
                                    {"" :register}}
    "user/enroll"                  {:post
                                    {"" :enroll}}
                                        ;content
    ["content/" :topic "/" :title] {:get
                                    {"" :document}}
    ["content/json/" :topic "/" :title] {:get
                                         {"" :document-json}}
    ["content-list-topic/" :topic] {:get
                                    {"" :list-content-by-topic}}
    ["content-list-topic/json/" :topic] {:get
                                         {"" :list-content-by-topic-json}}
    ["content-list-tag/" :tag]     {:get
                                    {"" :list-content-by-tag}}
    ["content-list-tag/json/" :tag]     {:get
                                         {"" :list-content-by-tag-json}}
    ["img/" :name] {:get
                    {"" :image}}
    ["js/" :name] {:get
                   {"" :javascript}}
    ["css/" :name] {:get
                    {"" :style}}
    ;; app life-cycle
    "_ah/start"                    {:get
                                    {"" :start}}
    "_ah/health"                   {:get
                                    {"" :health}}
    "_ah/stop"                     {:get
                                    {"" :stop}}
    ;; reference
    "react"                        {:get
                                    {"" :react}}}])

(defn path-img [name]
  (path-for routing-data :image :name name))

(defn path-js [name]
  (path-for routing-data :javascript :name name))

(defn path-css [name]
  (path-for routing-data :style :name name))

