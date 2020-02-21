(ns services.uisvc
  (:require-macros [cljs.core.async.macros :refer [alt! go]])
  (:require [cljs.core.async
             :as async
             :refer [<! put! chan close! timeout pipeline-async to-chan]]
            [taoensso.timbre :as log]
            [cljs-http.client :as http]
            [cljs-http.util :as httputil]
            [cognitect.transit :as t]
            [bidi.bidi :refer [path-for]]
            [routing :refer [routing-data]]))

(defn- clj->json
  [ds]
  (.stringify js/JSON (clj->js ds)))

(defn <fetch [& params]
  (go
    (let [r (t/reader :json)
          res (<! (http/get
                    (apply path-for routing-data params)))
          raw-data (-> res :body clj->json)
          data (t/read r raw-data)]
      (do
        (println "response::" res)
        (println "raw-data::" raw-data)
        (if data
          (println "transformed::" data)
          (println "No data returned!!"))
        data))))
