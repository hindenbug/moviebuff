Create a Slackbot using Botkit.

Following http://altitudelabs.com/blog/create-a-slackbot-using-botkit/

People message the bot something and the bot responds with a movie quote that closely matches the message posted. We can use http://api.quodb.com/search/beautiful?titles_per_page=5&phrases_per_title=1&page=1 for this.


## Botkit (Bundled right now) [Node]

Can be modified into a separate app later.

```cd moviebuff/botkit ```

```sudo npm install```

```token=<TOKEN> url=<url-of-the-quote-server:3000> node bot.js```


## Server (Sentiment analysis and parser) [Clojure]

#### Local Setup

```brew install leiningen```

```cd moviebuff/server```

```lein ring server```


## Pacakaging

```lein uberjar```


## Running

```java -jar target/server-0.1.0-SNAPSHOT-standalone.jar```



## TODO:

* Handle negative sentiments with a positive response

* Setup server behind Nginx
