(ns utils.core
  (:require [clojure.string :as str]
            [goog.crypt.base64 :as b64]
            ["moment" :as moment]))

(defn handle-response [response grab-data-fn]
  (let [status (:status response)]
    (prn "Status: " status)
    (condp = status
      200 (grab-data-fn response)
      401 ["Ouch!!!!"]
      403 ["Remote Service Denied Access"]
      404 ["Could not Find Anything"]
      500 ["Something Broke"])))

(defn encode-base64 [s]
  (b64/encodeString s))

(defn decode-base64 [d]
  (b64/decodeString d))

(defn date-format [timestamp & {:keys [format]
                                :or {format "MM-DD-YYYY"}}]
  (-> timestamp
      moment/utc
      (.format format)))

(defn date-from-now [timestamp & {:keys [no-suffix]
                                  :or {no-suffix false}}]
  (-> timestamp
      moment/utc
      (.fromNow true)))

