(ns ui.widgets.video)

(defn video [src]
  [:video {:controls "true" :width "100%"}
   [:source {:src src}]
   "Sorry, your browser doesn't support embedded video."])

(defn iframe-video [src]
  (let [allowed "accelerometer; autoplay; encrypted-media; gyroscope; picture-in-picture"]
    [:div.w-100.aspect-ratio.p-btm-56pct
     [:iframe.absolute.top-0.left-0.w-100.h-100
      {:src             src
       :frameborder     "0"
       :allow           allowed
       :webkitAllowFullScreen "true"
       :mozallowfullscreen "true"
       :allowFullScreen "true"}]]))