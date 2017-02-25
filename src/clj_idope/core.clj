(ns clj-idope.core
  (:require [clj-http.client :as http]
            [clojure.data.json :as json]))


(def api-url "https://idope.se/search/")

(def categories {:all -1
                 :video 2
                 :movies 1
                 :tv 3
                 :games 7
                 :music 6
                 :anime 4
                 :apps 8
                 :books 9
                 :xxx 5
                 :others 0})

(def parameters {:category :c
                 :page :p
                 :unknown-1 :m
                 :order :o
                 :unknown-2 :s})

(def sort-by-opts {:seed 1
                   :size 2
                   :age 3})

(defn descending
  [sort-by]
  (* sort-by -1))

(defn get-category
  [category]
  (str (-> parameters :category name)
       "="
       (get categories category (:all categories))))

(defn get-page
  [page]
  (str (-> parameters :page name)
       "="
       page))

(defn get-order
  [sort-by]
  (let [sort-category (get sort-by-opts (key sort-by) (:seed sort-by-opts))
        sort-order (if (= :descending (val sort-by))
                    (descending sort-category)
                    sort-category)]
    (str (-> parameters :order name)
         "="
         sort-order)))


(defn query*
  "{:term
    :category
    :page
    :sort-by}"
  [params]
  (let [{:keys [term category page sort-by]} params
        url (str api-url term "?" (get-category category)
                                  "&"
                                  (get-page page)
                                  "&"
                                  (get-order (first sort-by)))]
    (-> (http/get url)
        :body
        (json/read-str :key-fn keyword))))

;; info_hash is used to compute the HTML page URI
;; Maybe can be used to compute the magnet URI as well
