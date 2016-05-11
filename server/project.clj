(defproject server "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :min-lein-version "2.0.0"
  :dependencies [[org.clojure/clojure "1.7.0"]
                 [compojure "1.4.0"]
                 [ring/ring-defaults "0.1.5"]
                 [ring-logger-onelog "0.7.6"]
                 [ring/ring-json "0.4.0"]
                 [damionjunk/nlp "0.3.0"]
                 [clj-http "3.0.1"]
                 [ring/ring-jetty-adapter "1.4.0"]]
  :main ^:skip-aot server.handler
  :plugins [[lein-ring "0.9.7"]]
  :ring {:handler server.handler/app}
  :profiles
  {:dev {:dependencies [[javax.servlet/servlet-api "2.5"]
                        [ring/ring-mock "0.3.0"]]}
   :uberjar {:aot :all}
   })
