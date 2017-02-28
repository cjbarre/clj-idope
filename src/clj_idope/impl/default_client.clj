(ns clj-idope.impl.default-client
  (:require [clj-idope.protocols :refer [IDopeSearchAPIClient]]
            [clj-http.client :as http]
            [clojure.data.json :as json]))

(def api-url "https://idope.se/search/")

(def search-key-missing ":search value must be provided.")
(def search-key-not-string ":search value must be a String.")
(def category-not-supported ":category value is not supported.")
(def question-mark "?")
(def ampersand "&")
(def default-category :all)
(def default-page 1)
(def default-order-option :seed)
(def default-ordering :descending)
(def page-key-must-be-number ":page value must be a number.")

(def categories {:all -1
                 :others 0
                 :movies 1
                 :video 2
                 :tv 3
                 :anime 4
                 :xxx 5
                 :music 6
                 :games 7
                 :apps 8
                 :books 9})

(def modifiers {:category "c"
                :page "p"
                :order "o"
                :unknown-1 "m"
                :unknown-2 "s"})

(def order-options {:seed 1
                    :size 2
                    :age 3})

(def orderings {:ascending (fn [order-option] order-option)
                :descending (fn [order-option] (* -1 order-option))})

(defn get-category
  [category]
  (if-not category
    (get categories default-category)
    (let [c (get categories category)]
      (when-not c
        (throw (IllegalArgumentException. category-not-supported)))
      c)))

(defn get-page
  [page]
  (if-not page
    default-page
    (if-not (number? page)
      (throw (IllegalArgumentException. page-key-must-be-number))
      page)))

(defn get-order
  [order]
  (let [ordering-fn (get orderings default-ordering)]
    (ordering-fn (get order-options default-order-option))))

(defn build
  [modifier value]
  (str (get modifiers modifier) "=" value))

(defn validate-search
  [search]
  (when-not search
    (throw (IllegalArgumentException. search-key-missing)))
  (when-not (string? search)
    (throw (IllegalArgumentException. search-key-not-string))))

(defn build-query-string
  [api-url search category page order]
  (str api-url
       search
       question-mark
       (build :category (get-category category))
       ampersand
       (build :page (get-page page))
       ampersand
       (build :order (get-order order))))

(defn results->edn
  [json-result-str]
  (json/read-str json-result-str :key-fn keyword))

(defrecord DefaultIDopeSearchAPIClient []
  IDopeSearchAPIClient
  (query [this request]
    (let [{:keys [search category page order]} request
          _ (validate-search search)
          http-response (http/get (build-query-string api-url
                                                      search
                                                      category
                                                      page
                                                      order))]
      (results->edn (:body http-response)))))

(defn client
  []
  (DefaultIDopeSearchAPIClient.))
