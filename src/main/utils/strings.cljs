(ns utils.strings
  (:require [goog.crypt.base64 :as b64]
            ["moment" :as moment]
            ["slugify" :as slugify]))

(defn encode-base64 [s]
  (if s
    (b64/encodeString s)))

(defn decode-base64 [d]
  (if d
    (b64/decodeString d)))

(defn date-format [timestamp & {:keys [format]
                                :or   {format "MM-DD-YYYY"}}]
  (-> timestamp
      moment/utc
      (.format format)))

(defn date-from-now [timestamp & {:keys [no-suffix]
                                  :or   {no-suffix false}}]
  (-> timestamp
      moment/utc
      (.fromNow no-suffix)))

(defn to-slug [s]
  (if s
    (slugify s (clj->js {:remove #"[*+~.()'\"!:@]"}))))

(defn update-vals [m val-keys f]
  (reduce #(update-in % [%2] f) m val-keys))

