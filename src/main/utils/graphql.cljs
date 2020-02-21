(ns utils.graphql)

(defn inline-fragment
  [type fields]
  [(keyword (str "... on " (name type))) fields])