(ns app.ui
  (:require [com.fulcrologic.fulcro.components :as comp :refer [defsc]]
            [com.fulcrologic.fulcro-css.localized-dom :refer [span div button br label]]
            [com.fulcrologic.fulcro.data-fetch :as df]
            [com.fulcrologic.fulcro-css.css-injection :as inj]
            [app.responses :as response]))

(defsc Product [this
                {:product/keys [description price]}
                {:keys [time quantity]}]
  {:query [:product/id :product/description :product/price]
   :ident :product/id
   :pre-merge (fn [{:keys [current-normalized data-tree state-map]}]
                ;(js/console.log "data-tree" data-tree)
                ;(js/console.log "current-normalized" current-normalized)
                ;(js/console.log "state-map" state-map)
                (merge current-normalized data-tree))
   :css   [[:.row
            {:height                "24px"
             :display               "grid"
             :grid-template-columns "40px 200px 90px 70px 80px"
             :grid-template-rows    "17px"
             :grid-template-areas   "\"time desc quantity price extension\""}]
           [:.time {:grid-area "time" :text-align "left"}]
           [:.desc {:grid-area "desc"}]
           [:.quantity {:grid-area "quantity" :text-align "right"}]
           [:.price {:grid-area "price" :text-align "right"}]
           [:.extension {:grid-area "extension" :text-align "right"}]]}
  (div :.row (inj/style-element {:component this})
       (div :.time time)
       (div :.desc description)
       (div :.quantity quantity)
       (div :.price (str "$" price))
       (div :.extension (str "$" (* price quantity)))))
(def product-ui (comp/factory Product))

(defsc Organisation [this {:organisation/keys [id products]}]
  {:query [:organisation/id {:organisation/products (comp/get-query Product)}]
   :ident :organisation/id})

(defsc LineItem [this {:line-item/keys [id product quantity]}]
  {:query [:line-item/id {:line-item/product (comp/get-query Product)} :line-item/quantity]
   :ident :line-item/id}
  (div (product-ui (comp/computed product {:time id :quantity quantity}))))

(def line-item-ui (comp/factory LineItem))

(defsc Invoice [this
                {:invoice/keys [line-items id]}]
  {:query         [:invoice/id {:invoice/line-items (comp/get-query LineItem)}]
   :initial-state (fn [_] {:invoice/id 1
                           :invoice/line-items []})
   :ident         :invoice/id
   :css           [[:.headings
                    {:height                "24px"
                     :display               "grid"
                     :grid-template-columns "40px 200px 90px 70px 80px"
                     :grid-template-rows    "17px"
                     :grid-template-areas   "\"time desc quantity price extension\""}]
                   [:.time {:grid-area "time" :text-align "left"}]
                   [:.desc {:grid-area "desc" :font-weight "bold"}]
                   [:.quantity {:grid-area "quantity" :font-weight "bold" :text-align "right"}]
                   [:.price {:grid-area "price" :font-weight "bold" :text-align "right"}]
                   [:.extension {:grid-area "extension" :font-weight "bold" :text-align "right"}]]}
  (div
    (button {:onClick #(df/load this [:invoice/id 1] Invoice {:post-mutation `response/loaded-invoice})}
            "Load Invoice")
    (div (str "Num line items to show is " (count line-items)))
    (br)
    (div :.headings (inj/style-element {:component this})
         (div :.time (label "Time"))
         (div :.desc (label "Description"))
         (div :.quantity (label "Quantity"))
         (div :.price (label "Price"))
         (div :.extension (label "Extension")))
    (if (-> line-items count zero?)
      (div
        (br)
        (div "---------> Press \"Load Invoice\" button to see the items"))
      ;; If you apply, react doesn't complain, so you don't need to set a :keyfn
      (apply div (map line-item-ui line-items)))))

(def invoice-ui (comp/factory Invoice))
