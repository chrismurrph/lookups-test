(ns app.root
  (:require [app.ui :as ui]
            [com.fulcrologic.fulcro.components :as comp :refer [defsc]]
            [com.fulcrologic.fulcro-css.localized-dom :refer [div]]
            [com.fulcrologic.fulcro-css.css-injection :as inj]))

(defsc Root [this {:keys [invoice]}]
  {:query [{:invoice (comp/get-query ui/Invoice)}]
   :initial-state (fn [_] {:invoice (comp/get-initial-state ui/Invoice)})}
  (div
    (inj/style-element {:component this})
    (div
      (ui/invoice-ui invoice))))