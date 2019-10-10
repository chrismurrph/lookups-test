(ns app.general
  (:require [com.fulcrologic.fulcro.components :as comp :refer [defsc]]))

;;
;; The merging trick only works on the edges, where your lookup is the last table, as here with product/id.
;; If product was in turn looking up something else, say pricing-rule/id, then you would fall afoul of Fulcro
;; requiring that the response map matches the query.
;; So the final real solution is very simple. Have 2 defsc's for Product. If you want only certain information
;; then only ask for that information. Hence ProductStub comes about...
;;
(def stub-way? true)

(def stub-product :app.ui.ProductStub)
(def product :app.ui.Product)

(defn product-class []
  (let [key (if stub-way?
              stub-product
              product)]
    (if-let [cls (comp/registry-key->class key)]
      cls
      (assert false (str "Not found in registry" key)))))
