(ns utils.content
  (:require [clojure.string :as str]
            [utils.strings :refer [to-slug]]
            [services.urls :as urls]))

(defn update-items [k item coll]
  (-> (remove #(= (k %) (k item)) coll)
      (into [])
      (concat [item])))

(defn swap-media! [m media]
  (reduce-kv (fn [m k v]
               (if (= k :media)
                 (assoc m k (update-items :media-title media v))
                 (assoc m k v))) {} m))

(defn remove-item [item coll]
  (into [] (remove #{item} coll)))

(defn remove-map-item! [m key item]
  (reduce-kv (fn [m k v]
               (if (= k key)
                 (assoc m k (remove-item item v))
                 (assoc m k v))) {} m))

(def empty-doc {:doc-topic    ""
                :doc-title    ""
                :doc-body     ""
                :doc-tags     []
                :doc-language ""
                :doc-level    ""
                :doc-filter   ""
                :media        []})

(def empty-media {:media-title ""
                  :media-type  ""
                  :media-url   ""
                  :media-tags  []})

(defn topic-slug [{:keys [doc-topic]}]
  "[{:doc-topic The Topic}] -> the-topic"
  (if doc-topic
    (str/lower-case (to-slug doc-topic))))

(defn title-slug [{:keys [doc-title]}]
  "[{:doc-title The Title}] -> the-title"
  (if doc-title
    (str/lower-case (to-slug doc-title))))

(defn topic-key [document]
  "[{:doc-topic The Topic}] -> namespace:##:the-topic"
  (let [{:keys [namespace]} (urls/url-config)
        topic-slug (topic-slug document)]
    (str namespace ":##:" topic-slug)))

(defn doc-key [document]
  "[{:doc-topic The Topic :doc-title The Title}] -> namespace:##:the-topic:##:the-title"
  (let [topic-key (topic-key document)
        title-slug (title-slug document)]
    (str topic-key ":##:" title-slug)))

(defn topics []
  ["history" "education" "language" "africa" "development"])

(def header-img-tag "#headerimage")

(defn get-tag [coll tag]
  (into [] (filter (fn [x]
                     (some #(= tag %) (:Tags x))) coll)))

(defn media-title [id topic]
  (str/replace id (str topic ":##:") ""))

(defn kam-ns [doc]
  (let [topic (:Topic doc)
        topic-slug (-> doc
                       :Identifier
                       to-slug
                       str/lower-case)]
    (str/replace topic (str ":##:" topic-slug) "")))

(defn media-type [type ns]
  (str/replace type (str ns ":##:") ""))

