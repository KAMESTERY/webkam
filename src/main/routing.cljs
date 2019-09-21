(ns routing)

(def routing-data
  ["/"
   ;; public
   {""                             {:get
                                    {"" :home}}
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
    ["content-list-topic/" :topic] {:get
                                    {"" :list-content-by-topic}}
    ["content-list-tag/" :tag]     {:get
                                    {"" :list-content-by-tag}}
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

