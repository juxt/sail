(ns sail.base)

;; Based on L351 SUIT CSS forked section of the css generated by tailwind.
;; assume this is what happens when you call @tailwind base; for now.

(def base
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
