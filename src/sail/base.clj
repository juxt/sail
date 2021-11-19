(ns sail.base
  (:require [sail.color :refer [palette]]))

;; Based on L351 SUIT CSS forked section of the css generated by tailwind.
;; assume this is what happens when you call @tailwind base; for now.

(def base-reset
  [
    :html {;; Prevent padding and border from affecting element width https://goo.gl/pYtbK7
           :box-sizing "border-box"
           ;; Change the default font family in all browsers (opinionated)
           :font-family "sans-serif"}
    [:* (keyword "::before") (keyword "::after")] {:boxing-size "inherit"}

    ;; Removes the default spacing and border for appropriate elements.
    [:blockquote
     :dl
     :dd
     :h1
     :h2
     :h3
     :h4
     :h5
     :h6
     :hr
     :figure
     :p
     :pre] {:margin 0}

    :button {:background "transparent" :padding 0}

    ;; Work around a Firefox/IE bug where the transparent `button` background
    ;; results in a loss of the default `button` focus styles.
    :button:focus {;; :outline "1px dotted" ;; TODO another case of duplicated keys in source
                   :outline "5px auto -webkit-focus-ring-color"}

    :fieldSet {:margin 0 :padding 0}

    [:ol :ul] {:list-style "none" :margin 0 :padding 0}
    ])

(def custom-reset
  [
    :html {;; Use the system font stack as a sane default.
           :font-family "-apple-system, BlinkMacSystemFont, \"Segoe UI\", Roboto, \"Helvetica Neue\", Arial, \"Noto Sans\", sans-serif, \"Apple Color Emoji\", \"Segoe UI Emoji\", \"Segoe UI Symbol\", \"Noto Color Emoji\""
           ;; Use Tailwind's default "normal" line-height so the user isn't forced
           ;; to override it to ensure consistency even when using the default theme.
           :line-height 1.5}
    
    ;; Allow adding a border to an element by just adding a border-width.
    [:* (keyword "::before") (keyword "::after")] {:border-width 0 :border-style "solid" :border-color (:gray-300 palette)}

    ;; Ensure horizontal rules are visible by default
    :hr {:border-top-width "1px"}

    ;; TODO Remove the border-style: none reset from Normalize
    ;; Undo the border-style: none reset for images
    :img {:border-style "solid"}

    :textarea {:resize "vertical"}


    ;; TODO potential autoprefixing?
    [(keyword "input::-webkit-input-placeholder")
     (keyword "textarea::-webkit-input-placeholder")] {:color (:gray-500 palette)}

    [(keyword "input::-moz-placeholder")
     (keyword "textarea::-moz-placeholder")] {:color (:gray-500 palette)}

    [(keyword "input:-ms-input-placeholder")
     (keyword "textarea:-ms-input-placeholder")] {:color (:gray-500 palette)}

    [(keyword "input::-ms-input-placeholder")
     (keyword "textarea::-ms-input-placeholder")] {:color (:gray-500 palette)}

    [(keyword "input::placeholder")
     (keyword "textarea::placeholder")] {:color (:gray-500 palette)}

    [:button (keyword "[role=\"button\"]")] {:cursor "pointer"}

    :table {:border-collapse "collapse"}

    [:h1 :h2 :h3 :h4 :h5 :h6] {:font-size "inherit" :font-weight "inherit"}

    :a {:color "inherit" :text-decoration "inherit"} 

    [:button :input :optgroup :select :textarea] {:padding 0
                                                  :line-height "inherit"
                                                  :color "inherit"}

    [:pre :code :kbd :samp] {:font-family "Menlo, Monaco, Consolas, \"Liberation Mono\", \"Courier New\", monospace"}

    [:img :svg :video :canvas :audio :iframe :embed :object]
    {:display "block" :vertical-align "middle"}

    [:img :video] {:max-width "100%" :height "auto"}])

(def base (into base-reset custom-reset))
