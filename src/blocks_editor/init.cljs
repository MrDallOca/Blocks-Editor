(ns blocks-editor.init
  (:require [blocks-editor.events]
            [blocks-editor.core :as c]))

(set! (.-onload js/window) #(c/init! {}))

