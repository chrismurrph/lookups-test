(ns app.ui
  (:require [com.fulcrologic.fulcro.components :as comp :refer [defsc]]
            [com.fulcrologic.fulcro.dom :as dom :refer [span div button br]]))

(defsc Product [this
                {:product/keys [description price]}
                {:keys [quantity]}]
  {:query [:product/id :product/description :product/price]
   :ident :product/id}
  (span (str description ", quantity: " quantity ", price: $" price ", extension: $" (* price quantity))))
(def product-ui (comp/factory Product))

(defsc Organisation [this {:organisation/keys [id products]}]
  {:query [:organisation/id {:organisation/products (comp/get-query Product)}]
   :ident :organisation/id})

(defsc LineItem [this {:line-item/keys [id product quantity]}]
  {:query         [:line-item/id {:line-item/product (comp/get-query Product)} :line-item/quantity]
   :ident         :line-item/id}
  (div (product-ui (comp/computed product {:quantity quantity}))))

(def line-item-ui (comp/factory LineItem))

(defsc Invoice [this
                {:invoice/keys [line-items id]}
                {:keys [onClick]}]
  {:query         [:invoice/id {:invoice/line-items (comp/get-query LineItem)}]
   :initial-state (fn [_] {:invoice/id 1})
   :ident         :invoice/id}
  (assert onClick)
  (div
    (button {:onClick #(onClick)}
                "Load Invoice")
    (div (str "Num line items to show is " (count line-items)))
    (br)
    ;; If you apply, react doesn't complain, you don't need a keyfn
    (apply div (map line-item-ui line-items))))

(def invoice-ui (comp/factory Invoice))
