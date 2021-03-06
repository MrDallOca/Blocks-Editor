(ns blocks-editor.core
  (:require [reagent.core :as reagent]
            [re-frame.core :as rf] 
            [blocks-editor.view :as v]
            [blocks-editor.view.styles] 

            [Blockly :as b])
  (:import [Blockly.Blocks
            loops logic procedures math texts variables lists colour]
           [Blockly.Msg en]))

(defonce $ (js* "$"))

(defonce workspace (atom nil))

(defn config-workspace! []
  (doto (.ajax $ (clj->js  {:url "assets/xml/toolbox.xml"}))
    (.done #(set! workspace (-> "#blocklyDiv" $ (aget 0)
                                (b/inject
                                 (clj->js {:toolbox %})))))))

(defn config-tooltip! []
  (doto ($ "[data-toggle=\"tooltip\"]")
    .tooltip))

(defn ^:export init!
  [config-file-name] 
  (rf/dispatch [:init-db])
  (rf/dispatch [:setup-config config-file-name])
  (reagent/render
   [v/ui]
   (-> "#app" $ (aget 0)))
  (config-workspace!)
  (config-tooltip!))

