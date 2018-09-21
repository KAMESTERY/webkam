(ns hello-world.core)

(enable-console-print!)

(println "Hello world!")

(defn average [a b]
  (let [average (/ (+ a b) 2.0)]
    (println average)
    average))


(comment
  (require '[hello-world.core :as hello] :reload)
  )
