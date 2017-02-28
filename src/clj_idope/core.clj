(ns clj-idope.core
  (:require [clj-idope.protocols :as p]
            [clj-idope.impl.default-client :refer [client]]
            [clj-http.client :as http]
            [clojure.data.json :as json]))

(def api-client (client))

(defn query
  "Convenience function.

  See clj-idope.protocols/query for docs."
  [request]
  (p/query api-client request))

;; info_hash is used to compute the HTML page URI
;; Maybe can be used to compute the magnet URI as well
