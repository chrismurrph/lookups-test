(ns app.mount-point
  (:require [com.fulcrologic.fulcro.application :as app]))

(defonce app (atom nil))

(defn mount [comp]
  (app/mount! @app comp "app"))
