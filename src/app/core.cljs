(ns applets.invoices.core
  (:require [applets.invoices.root :as root]
            [applets.mount-point :as mount]
            [com.fulcrologic.fulcro.application :as app]
            [com.fulcrologic.fulcro.rendering.keyframe-render :as keyframe-renderer]
            [general.dev :as dev]
            [com.fulcrologic.fulcro.data-fetch :as df]
            [com.fulcrologic.fulcro.networking.http-remote :as fhr]
            [applets.invoices.ui :as ui]))

(defn started-callback [app]
  (dev/log-a "started-callback, so USER REFRESH")
  (df/load! app [:organisation/id 1] ui/Organisation))

(def options {:remotes           {:remote
                                  (fhr/fulcro-http-remote {:url "/api"})}
              :client-did-mount  (fn [app]
                                   (started-callback app))
              :optimized-render! keyframe-renderer/render!})

(defn ^:export init [{:keys [refresh?]}]
  (when (not refresh?)
    (reset! mount/app (app/fulcro-app options)))
  (mount/mount root/Root))
