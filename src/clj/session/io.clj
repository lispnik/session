(ns session.io
  (:require
    merchant.common
    session.datamappings
    yantra.datamappings
    merchant.edn.reader
    merchant.edn.writer

    ))

(declare resolve-session-object)
(declare object-mappings)

(def data-mappings
  (concat yantra.datamappings/data-mappings
         session.datamappings/data-mappings
         ))


(defn read-edn [x]
  (merchant.edn.reader/import-edn
    x
    (conj data-mappings {:tag "session/Object" :constructor resolve-session-object})))


(def object-mappings
  {:id-to-object (atom {})
   :object-to-id (atom {})})



;; return an id
(defn object-to-id [o object-mappings]
  (if-let [id (@(:object-to-id object-mappings) o)]
    id
    (let [id (java.util.UUID/randomUUID)]
      (swap! (:object-to-id object-mappings) assoc o id)
      (swap! (:id-to-object object-mappings) assoc id o)
      id)))


(defn id-to-object [id object-mappings]
  (@(:id-to-object object-mappings) id))


(defn resolve-session-object
       [obj]
  (if-let [o (id-to-object (:id obj) object-mappings)]
    o
    (throw (Exception. "No object for id"))))



(defn write-edn [x]
  (merchant.edn.writer/export-edn
    x
    { :default-fn
               (fn [writer o]
                 (merchant.common/write-tagged
                   writer
                   "session/Object"
                   merchant.common/write-map
                   {:type (pr-str (class o)) :id (object-to-id o object-mappings)}))
      :tag-map (into {}
                    (map
                      #(vector (:record %)
                               (assoc % :write-fn merchant.common/write-map))
                      data-mappings))}))