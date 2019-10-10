(ns app.resolvers
  (:require
    [com.wsscode.pathom.connect :as pc]))

(def line-items-table
  {1 {:line-item/id 1 :line-item/quantity 1 :line-item/product {:product/id 1}}
   2 {:line-item/id 2 :line-item/quantity 3 :line-item/product {:product/id 2}}
   3 {:line-item/id 3 :line-item/quantity 6 :line-item/product {:product/id 1}}
   4 {:line-item/id 4 :line-item/quantity 2 :line-item/product {:product/id 2}}
   5 {:line-item/id 5 :line-item/quantity 1 :line-item/product {:product/id 3}}
   6 {:line-item/id 6 :line-item/quantity 3 :line-item/product {:product/id 1}}
   7 {:line-item/id 7 :line-item/quantity 6 :line-item/product {:product/id 4}}
   8 {:line-item/id 8 :line-item/quantity 2 :line-item/product {:product/id 2}}})

(def products-table
  {1 {:product/id 1 :product/description "1kg rice" :product/price 32}
   2 {:product/id 2 :product/description "egg" :product/price 22}
   3 {:product/id 3 :product/description "1 litre OJ" :product/price 11}
   4 {:product/id 4 :product/description "15 Metres cling wrap" :product/price 55}})

;;
;; This resolver works fine, but is needlessly returning products that already exist
;; in app state.
;; All the product information already exists on the client. You ought to be able to
;; just return the id, so not even have this resolver, and rely on merge behaviour
;; creating the ident that points to the existing product. Instead what happens is that
;; all the product information on the client is deleted.
;;
(pc/defresolver product-resolver [env {:product/keys [id]}]
  {::pc/input  #{:product/id}
   ::pc/output [:product/description :product/price]}
  (get products-table id))

(pc/defresolver invoice-resolver [env {:invoice/keys [id]}]
  {::pc/input  #{:invoice/id}
   ::pc/output [{:invoice/line-items [:line-item/id {:line-item/product [:product/id :product/description :product/price]}]}]}
  {:invoice/line-items (vec (vals line-items-table))})

(pc/defresolver organisation-resolver [env {:organisation/keys [id]}]
  {::pc/input  #{:organisation/id}
   ::pc/output [{:organisation/products [:product/id :product/description :product/price]}]}
  {:organisation/products (vec (vals products-table))})

(def resolvers [organisation-resolver invoice-resolver product-resolver])
