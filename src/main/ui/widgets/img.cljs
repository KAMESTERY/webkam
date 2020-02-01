(ns ui.widgets.img)

(defn img [attr]
  (let [{:keys [alt src]} attr]
    [:div
     [:img.w-100
      {:alt alt
       :src src}]]))