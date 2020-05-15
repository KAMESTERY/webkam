(ns utils.strings
  (:require [goog.crypt.base64 :as b64]
            [taoensso.timbre :as log]
            ["moment" :as moment]
            ["slugify" :as slugify]))

(defn encode-base64 [s]
  (if s
    ((try
       (b64/encodeString s)
       (catch :default e
         (log/error "ERROR:::: " e)
         s)))))

(defn decode-base64 [d]
  (if d
    (try
      (b64/decodeString d)
      (catch :default e
        (log/error "ERROR:::: " e)
        d))))

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

