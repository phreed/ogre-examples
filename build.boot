(def log-config
  [:configuration {:scan true, :scanPeriod "10 seconds"}
   [:appender {:name "FILE" :class "ch.qos.logback.core.rolling.RollingFileAppender"}
    [:encoder [:pattern "%d{HH:mm:ss.SSS} [%thread] %-5level %logger{36} - %msg%n"]]
    [:rollingPolicy {:class "ch.qos.logback.core.rolling.TimeBasedRollingPolicy"}
     [:fileNamePattern "logs/%d{yyyy-MM-dd}.%i.log"]
     [:timeBasedFileNamingAndTriggeringPolicy {:class "ch.qos.logback.core.rolling.SizeAndTimeBasedFNATP"}
      [:maxFileSize "64 MB"]]]
    [:prudent true]]
   [:appender {:name "STDOUT" :class "ch.qos.logback.core.ConsoleAppender"}
    [:encoder [:pattern "%-5level %logger{36} - %msg%n"]]
    [:filter {:class "ch.qos.logback.classic.filter.ThresholdFilter"}
     [:level "INFO"]]]
   [:root {:level "INFO"}
    [:appender-ref {:ref "FILE"}]
    [:appender-ref {:ref "STDOUT"}]]
   [:logger {:name "user" :level "ALL"}]
   [:logger {:name "boot.user" :level "ALL"}]])

(set-env!
  :resource-paths #{"src/res"}
  :source-paths #{"src/clj"}
  :dependencies
  '[[org.clojure/clojure "1.9.0-alpha17"]
    [nightlight "1.6.4" :scope "test"]
    [clojurewerkz/ogre "3.0.0.0-beta3-SNAPSHOT"]
    [org.apache.tinkerpop/gremlin-core "3.2.4"]
    [org.apache.tinkerpop/gremlin-test "3.2.4"  :scope "test"]
    [org.apache.tinkerpop/tinkergraph-gremlin "3.2.4" :scope "test"]
    [org.clojure/tools.logging "0.3.1"]
    [adzerk/boot-logservice "1.2.0"]])


(require
  '[nightlight.boot :refer [nightlight]]
  '[adzerk.boot-logservice :as log-service]
  '[clojure.tools.logging  :as log])

(alter-var-root #'log/*logger-factory* 
  (constantly (log-service/make-factory log-config)))

(deftask run []
  (comp
    (wait)
    (nightlight :port 4000)))

