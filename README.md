# clj-idope

A low level Clojure client for the IDope torrent site search API. More expressive and featured clients are meant to be built ontop of this one.

## Note
I hope you find this library useful and of reasonable quality. Whether the demand for a library like this is large or small, I hope that even one developer finds this and makes something for themselves or others. This library is provided to you for free, forever, to do with as you please. May it save you time, or bring you joy in some small way.

Enjoy,  
Cameron

## Usage

Require the query function. See `clj-idope.protocols/query` for a description of the request map.

```clojure
(require '[clj-idope.core :refer [query]])
```

Category and page number are optional. The following returns query results on page 1, filtered by category `All`.

```clojure
(query {:search "The Shawshank Redemption"})
```

The following will restrict the search to movies and return results from page 2.

```clojure
(query {:search "The Shawshank Redemption"
        :category :movies
        :page 2})
```

## Limitations

- **Ordering is not implemented completely, results are returned ordered by seeds descending.**

- **This API client is based on an undocumented search API, as far as I know.**

## Contributing

- Make an issue.
- Send a PR.
- Contact me.

## License

Distributed under the unlicense: http://unlicense.org/
