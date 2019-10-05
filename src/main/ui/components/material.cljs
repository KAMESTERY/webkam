(ns ui.components.material)

(defn icon-button [icon label]
  [:button.mdc-fab.mdc-fab--extended.bg-primary.h-50.mr2 {:aria-label label}
   [:span.mdc-fab__icon.material-icons icon]
   [:span.mdc-fab__label label]]
  )