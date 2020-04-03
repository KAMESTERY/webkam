(ns ui.ux.language)

(defn google-translate-element-init [el_id & {:keys [lang] :or {lang "en"}}]
      (let [GoogleTranslateElement (.. js/google -translate -TranslateElement)]
           (GoogleTranslateElement. (js-obj :pageLanguage lang) el_id)))
