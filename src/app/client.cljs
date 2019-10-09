(ns app.client
  (:require
    [app.mount-point :as mount]
    [app.root :as root]
    [app.ui :as ui]
    [com.fulcrologic.fulcro.application :as app]
    [com.fulcrologic.fulcro.components :as comp :refer [defsc]]
    [com.fulcrologic.fulcro.mutations :as m :refer [defmutation]]
    [com.fulcrologic.fulcro.data-fetch :as df]
    [com.fulcrologic.fulcro.routing.dynamic-routing :as dr :refer [defrouter]]
    [taoensso.timbre :as log]
    [com.fulcrologic.fulcro.rendering.keyframe-render :as keyframe-renderer]
    [com.fulcrologic.fulcro.networking.http-remote :as fhr]))

(defn started-callback [app]
  (df/load! app [:organisation/id 1] ui/Organisation))

(def options {:remotes           {:remote
                                  (fhr/fulcro-http-remote {:url "/api"})}
              :client-did-mount  (fn [app]
                                   (started-callback app))
              :optimized-render! keyframe-renderer/render!})

(defn refresh []
  (mount/mount root/Root))

(defn ^:export start []
  (reset! mount/app (app/fulcro-app options))
  (mount/mount root/Root))

