(ns routing)

(def routing-data
  ["/"
   ;; public
   {"" {:get
        {"" :home}}
    ;; user
    "user/login" {:get
                  {"" :login}}
    "user/authenticate" {:post
                  {"" :authenticate}}
    "user/register" {:get
                         {"" :register}}
    "user/enroll" {:post
                         {"" :enroll}}
    ;content
    ["content/" :topic "/" :title] {:get
                        {"" :get-document}}
    ;; app life-cycle
    "_ah/start" {:get
                 {"" :start}}
    "_ah/health" {:get
                  {"" :health}}
    "_ah/stop" {:get
                {"" :stop}}
    ;; reference
    "react" {:get
             {"" :react}}}
   ])

