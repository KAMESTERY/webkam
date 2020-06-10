(ns ui.ux.promotion)

(defn blm-badge-init []
  (let [config (js-obj {
                        :layout    1
                        :theme     "dark"
                        :promoText "Send a donation"
                        :promoLink ":https //minnesotafreedomfund.org/"
                        :message   "To be silent is to be complicit. Black lives matter."
                        :title     "#BlackLivesMatter"})]
    (.init js/BLMBadge config)))
