(ns fig-pat.chita
  (:require
   [patterning.sshapes :as sshapes]
   [patterning.groups :refer [translate rotate reframe
                              stretch scale over-style]]
   [patterning.layouts :refer [clock-rotate stack grid-layout
                               four-mirror h-mirror v-mirror
                               half-drop-grid-layout framed
                               sshape-as-layout four-round]]
   [patterning.library.std :refer [poly drunk-line square diamond]]
   [patterning.color :refer [p-color]]
   [patterning.view :refer [transformed-sshape make-txpt xml-tpl make-svg] ]
   [patterning.maths :refer [PI]]
   [patterning.examples.tutorial :refer []]
   [patterning.examples.framedplant :refer [framed-plant]]
   [patterning.library.complex_elements :refer [petal-group petal-pair-group
                                                polyflower-group]] ))

(def p1
  (let [verde {:fill (p-color  100 150 100) :stroke-weight 0}
        bg (square verde)
        amarelo {:fill (p-color 255 255 0) :stroke-weight 0}
        y (translate 0.3 0 (scale 0.6  (diamond amarelo)))
        azul {:fill (p-color 50 30 150) :stroke-weight 0}
        b (stretch 0.6 1
                   (let [bs (stack (square azul)
                                   (->> (poly 0 0.2 0.2 7 {:fill (p-color 255 255 255)})

                                        (#(grid-layout 3 (cycle [() %])))) )]
                     (framed 4 (repeat bs) (repeat bs) []) ))
        vermelho {:fill (p-color 250 20 60)
                  :stroke-weight 2
                  :stroke (p-color 200 0 0)}
        petal (let [p (->> (poly 0.2 0 0.4 15 vermelho)
                           (stretch 1.3 1)
                           )
                    p2 (scale 0.8 (over-style {:fill (p-color 255 240 250)} p))
                    p3 (scale 0.8 (over-style vermelho p2))]
                (stack p p2 p3                       ))
        flor (stretch 1 0.7 (clock-rotate 5 petal))
        dl (drunk-line 7 0.15 { :stroke-weight 4 :stroke (p-color 0 255 0)})
        rose-line (four-round (clock-rotate 3 (reframe (stack dl (sshape-as-layout (first dl) (cycle [() flor]) 0.08)))))  ]

    (stack bg y b rose-line ) ))

(def p2
  (let [blue {:fill (p-color 150 150 255)
              :stroke (p-color 50 50 255)
              :stroke-weight 2}

        yellow {:fill (p-color 180 250 50)
                :stroke-weight 1
                :stroke (p-color 150 120 20)}

        green {:fill (p-color 20 100 30)}

        rand-rot #(rotate (* (mod (rand-int 100) 8) (/ PI 4)) %)

        inner (stack
               (poly 0 0 0.3 12 {:fill (p-color 50 50 220)
                                 :stroke-weight 0})
               (->> (poly 0 0.1 0.06 5 yellow)
                    (clock-rotate 5)
                    (translate -0.09 -0.07)
                    ))

        flower (stack
                (clock-rotate 15 (petal-group blue 0.3 0.8 ))
                (let [y-stream
                      (clock-rotate 15 (petal-group yellow 0.3 0.8 ))]
                  (stack (list (nth y-stream 5))
                         (take 2 y-stream)
                         ))
                inner)

        leafy (fn [n]
                (stack
                 (->> (diamond green)
                      (stretch 0.5 0.25)
                      (translate -0.6 0)
                      (clock-rotate 4)
                      (drop 2)
                      (take n))
                 (->> (diamond {:fill (p-color 200 255 200)
                                :stroke-weight 0})
                      (stretch 0.2 0.1)
                      (translate -0.6 0)
                      (clock-rotate 4)
                      (drop 2)
                      (take n))
                 flower))

        whites (stack
                (->> (poly 0 0.3 0.2 5
                           {:fill (p-color 255 255 255)
                            :stroke-weight 0})
                     (clock-rotate 7)

                     )
                (poly 0 0 0.2 8 {:fill (p-color 0 0 200)}))

        small-yellow (let [all (->> (diamond yellow)
                                    (stretch 0.4 0.5)
                                    (translate 0 -0.6 )
                                    (clock-rotate 5)
                                    (scale 0.6 ))
                           blues (over-style blue all)]

                       (stack
                        all
                        (list (nth blues 0))
                        (list (nth blues 2))))


        leafy-seq (->> (iterate #(+ 1 (mod (rand-int 100) 2)) 0)
                       (map leafy)
                       (map rand-rot))
        ]
    (stack
     (square {:fill (p-color 255 10 10)})
     (half-drop-grid-layout
      17 (map rand-rot
              (map rand-nth
                   (repeat [whites []
                            small-yellow small-yellow ]))))
     (half-drop-grid-layout 6 leafy-seq)) ))
