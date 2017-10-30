(ns fig-pat.core
  (:require

   [patterning.sshapes :as sshapes]
   [patterning.groups :refer [translate rotate
                              stretch scale over-style]]
   [patterning.layouts :refer [clock-rotate stack grid-layout
                               four-mirror h-mirror v-mirror
                               half-drop-grid-layout]]
   [patterning.library.std :refer [poly drunk-line square diamond]]
   [patterning.color :refer [p-color]]
   [patterning.view :refer [transformed-sshape make-txpt xml-tpl make-svg] ]
   [patterning.maths :refer [PI]]
   [patterning.examples.tutorial :refer []]
   [patterning.examples.framedplant :refer [framed-plant]]
   [patterning.library.complex_elements :refer [petal-group petal-pair-group]]

   [fig-pat.chita :refer [p1 p2]]
   [cljs.tools.reader :refer [read-string]]
   [cljs.js :refer [empty-state eval js-eval]]
   ) )

(enable-console-print!)

(defonce app-state (atom {}))

(println (-> 0 (inc) (inc) (inc) (str " A B C")) )

;; destined for main

(defn on-line [[x1 y1] [x2 y2] p]
  (let []))

;;

(defn update! [pat]
  (let [div (.getElementById js/document "draw-div")
        code (.getElementById js/document "input")]
    (set! (.-innerHTML div) (make-svg 550 550 pat) )
    ;(set! (.-innerHTML code) (str pat))
    ))






(def final p1)

(update! final)
(defn on-js-reload [] )
