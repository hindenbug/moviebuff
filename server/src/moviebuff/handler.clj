(ns server.handler
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [ring.middleware.defaults :refer :all]
            [ring.logger.onelog :as logger]
            [ring.middleware.json :refer :all]
            [ring.middleware.params :refer :all]
            [ring.util.response :refer [response]]
            [damionjunk.nlp.stanford :as nlp]
            [clj-http.client :as http]
            [cheshire.core :refer :all]
            [ring.adapter.jetty :as jetty])
  (:gen-class))

(defn fetch-sentiment [smap]
  (get smap :sentiment))

(defn get-sentiment [msg]
  (/ (reduce + (map #(fetch-sentiment %1) (into [] (nlp/sentiment-maps msg))))
     (count (nlp/sentiment-maps msg))))

(defn attach-sentiment [h]
  (let [phrase (get-in h ["phrase"])
        title (get-in h ["title"])
        quote (hash-map :phrase phrase, :title title)]
    (assoc quote :sentiment (get-sentiment phrase))))

(defn extract-quotes [resp]
  (println resp)
  (get-in (parse-string (:body resp)) ["docs"]))

(defn matching-quotes [msg]
  (let [resp (http/get (str "http://api.quodb.com/search/" msg))]
    (map attach-sentiment (extract-quotes resp))))

(defn sort-quotes [quotes]
  (sort-by :sentiment > quotes))

(defn get-quote [msg]
  (response (first (sort-quotes (matching-quotes msg)))))

(defroutes app-routes
  (GET "/api/ping" [] "Hello World")
  (GET "/api/quote" [msg]
        (get-quote msg))
  (route/resources "/")
  (route/not-found "Not Found"))

(def app
  (-> app-routes
      wrap-params
      wrap-json-response))

(defn -main []
  (jetty/run-jetty app {:port 3000}))
