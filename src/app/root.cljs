(ns app.root
  (:require [app.ui :as ui]
            [com.fulcrologic.fulcro.components :as comp :refer [defsc]]
            [com.fulcrologic.fulcro.data-fetch :as df]
            [com.fulcrologic.fulcro.dom :as dom]))

(defsc Root [this {:keys [invoice]}]
  {:query [{:invoice (comp/get-query ui/Invoice)}]
   :initial-state (fn [_] {:invoice (comp/get-initial-state ui/Invoice)})}
  (dom/div {}
           (ui/invoice-ui (comp/computed invoice {:onClick #(df/load this [:invoice/id 1] ui/Invoice)}))))