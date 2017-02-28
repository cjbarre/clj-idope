(ns clj-idope.protocols)

(defprotocol IDopeSearchAPIClient
  (query [this request]
    "request Documentation:

    :search (required)
      - Search term
      - String
      - Example: 'Peep Show S01E02'
    :category (optional)
      - Filter category
      - Keyword
      - Pick one: :all
                  :others
                  :movies
                  :video
                  :tv
                  :anime
                  :xxx
                  :music
                  :games
                  :apps
                  :books
      - Default: :all
      - Example: :tv
    :page (optional)
      - Page number, or which page of search results to return
      - Number
      - Default: 1
      - Example: 1
    :order (optional)
      - Control the ordering of the returned results
      - Tuple [option ordering]
      - Option pick one: :seed
                         :size
                         :age
      - Ordering pick one: :ascending
                           :descending
      - Default [:seed :descending]
      - Example: [:seed :descending]

      Full Example: {:search 'Peep Show S01E02'
                     :category :tv
                     :page 1
                     :order [:seed :descending]}

      Optional Example (good luck): {:search 'Peep Show S01E02'}"))
