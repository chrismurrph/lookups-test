(ns app.responses
  (:require [com.fulcrologic.fulcro.mutations :refer [defmutation]]))

;;
;; If you are not using product-resolver then now is too late.
;; Everything in all products apart from the id has gone.
;; I believe that in a mutation you can get the before state??
;; If so that would give us a chance to reconstitute what's
;; been lost.
;; Perhaps another workaround is to have pathom on the client,
;; basically moving the product-resolver from the server to the
;; client.
;;
(defmutation loaded-invoice [no-params]
             (action [{:keys [state]}]
                     (let [st @state
                           first-product (get-in st [:product/id 1])]
                       (js/console.log "first-product" first-product)
                       state)))
