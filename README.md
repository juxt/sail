<p align="center">
  <img width="460" height="300" src="./sail-logo.png">
</p>

Sail is a pure-Clojure generation tool for creating Tailwind CSS classes. This
includes the ability to only generate the classes that you use by analysing
the source code in your project.

[![Clojars Project](https://img.shields.io/clojars/v/com.hypalynx/sail.svg)](https://clojars.org/com.hypalynx/sail)

## Getting Started

Include sail as a dependency in your project: 
```clojure
[com.hypalynx/sail "0.8.15"]
{com.hypalynx/sail {:mvn/version "0.8.15"}}
```

Require it in a namespace, like dev.user:
```clojure
(require '[sail.core :as sail]')
```

Include the following in your build sequence to get your css:
```clojure
;; generates all tailwind classes to use in development and re-builds when changes occur
(sail/watch "target/public/styles.gen.css" {:paths ["./src/cljs"]})

;; generates all tailwind classes once, used for production builds
(sail/build "styles.test.gen.css" {:paths ["./src/cljs"]})
```

## Usage Notes

### Grid Equivalents
  (N.B 4px is useful for things like py-2 which have 4px either side to == 8px total)
- 8 pt grid: w-1 w-2 w-4 w-6 w-8 w-16 w-32 w-64 == 4px 8px 16px 24px 32px 64px 128p 256px
- 8 pt grid: text-xs, text-base, text-2xl, text-5xl, text-9xl == font-size/line-height, 12px/16px, 16px/24px, 24px/32px, 48px, 128px



## Development

If you add depenedencies, you must add them to both deps.edn and pom.xml.. ideally we generate the pom file to be
honest but this works for now.

### TODO / Known Issues

When watching/regenerating files.. the new file won't refresh? need to test this and confirm for both shadow & figwheel
  - figwheel works.. you just have to configure the css-dirs to live reload them

- Watcher will rewrite the output file even if there are no changes. (e.g file change triggers rebuild but the css is
  computed to be exactly the same)

## Implementation notes

Originally I used array-maps to persist order and keep selectors/operators as
maps but as they get bigger, grouping the array-maps revert to hash maps.
Switching to vectors seemed the easier choice.

L600 in full default tailwind.css is where I consider base to end and
components to begin, when .container is first defined.

It looks as though Safair only supports old flex syntax e.g -webkit-box. This
means that multiple keys of the same name are required, where the most modern
sits at the bottom.

display: -webkit-inline-box;
display: inline-flex;

## Technical Differences

Sail aims to be 100% compliant with Tailwind CSS but there are some additional
classes added to make the library easier to use. For example classes with `/`
is them are not valid keywords when used directly so we have alternate tags in
addition to the originals e.g `w-1/2 & w-1-2`

## Releasing

```
make build
CLOJARS_USERNAME="x" CLOJARS_PASSWORD="y" clj -A:deploy
```

## TODO

- [ ] do not assume all source files are in "src"
- [ ] allow reading .cljc files (current implementation causes "Conditional read not allowed exception")
- [ ] borders should show without .border-solid, see examples on this page: https://tailwindcss.com/docs/table-layout 
- [ ] ensure font-sans is prefixed as .font-sans
- [ ] consume all classes from tailwindcss npm (make this easy to re-run as new
  versions are released)
- [ ] autoprefix css rules that require it.
- [X] purgecss style dead code elimination
  - N.B using keywords alone with strip out uses in html e.g index.html base
    page.
- [X] describe/find a clean way of tacking this onto a project? e.g already
  using sass?
- [X] cssnano, minification?
- [ ] autocomplete (maybe not part of this lib, autocompleting css classes when
  - most likely a cljs npm language-server will do the trick.
  writing hiccup is important though for this workflow.)
- [X] add in all default colors
- [ ] how to use guide
- [ ] clean up components code (mostly putting things under the correct names)
- [ ] include placeholder classes
- [ ] .container
- [X] .sr-only
- Check :hover\:border-green-800:hover.. seems wrong to have hover at both ends (seen in generated all-project-keywords for sail project)
- [X] include media queries
- [ ] consider adding text-decoration-style (add PR to tailwindcss repo) https://developer.mozilla.org/en-US/docs/Web/CSS/text-decoration-style

## Reference

;; npm view normalize.css version == 8.0.1
;; npm view tailwindcss version == 1.2.0
