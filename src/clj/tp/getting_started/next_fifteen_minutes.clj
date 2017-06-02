(ns tp.getting-started
  ";;http://tinkerpop.apache.org/docs/current/tutorials/getting-started/ "
  (:require [clojure.test :refer [deftest testing is]]            
            [clojurewerkz.ogre.core :as q]
            [clojurewerkz.ogre.util :as util]
            [clojure.pprint :as pp])  
  (:import (org.apache.tinkerpop.gremlin.structure T Vertex)
           (org.apache.tinkerpop.gremlin.process.traversal P Traversal)
           (org.apache.tinkerpop.gremlin.tinkergraph.structure TinkerGraph TinkerFactory)))
 

;; The Next Fifteen Minutes

;; Creating a Graph

(def graph (TinkerGraph/open))
graph

(defn addVertex 
  [graph & args]
  (.addVertex graph (into-array Object args)))

(defn addEdge 
  [v1 label v2 & args]
  (.addEdge v1 label v2 (into-array Object args)))

(def v1 (addVertex graph T/id (int 1), T/label "person", "name" "marko", "age" 29))
v1
(def v2 (addVertex graph T/id (int 3), T/label "software", "name" "lop", "lang" "java"))
v2

(addEdge v1 "created" v2 T/id (int 9), "weight" 0.4)

;; Graph Traversal - Staying Simple

(def g (q/traversal graph))
(.toString g)

(defmacro gremlin 
  "get behavior similar to the gremlin console"
  [& wrap] 
  `(util/into-seq! (q/traverse ~@wrap)))

(with-out-str (pp/pprint (macroexpand '(gremlin g q/V))))

;; g.V().has('name','marko')
(gremlin g q/V
    (q/has :name "marko"))

;; g.V().has('name','marko').outE('created')
(gremlin g q/V
    (q/has :name "marko")
    (q/outE "created"))

;; g.V().has('name','marko').outE('created').inV()
(gremlin g q/V
    (q/has :name "marko")
    (q/outE "created")
    (q/inV))

;; g.V().has('name','marko').out('created')
(gremlin g q/V
    (q/has :name "marko")
    (q/out "created"))

;; gremlin> g.V().has('name','marko').out('created').values('name')
(gremlin g q/V
    (q/has :name "marko")
    (q/out "created")
    (q/values :name))

;; Graph Traversal - Increasing Complexity
(def graph-modern (TinkerFactory/createModern))
graph-modern

(def gm (q/traversal graph-modern))
gm

;; g.V().has('name',within('vadas','marko')).values('age')
(gremlin gm q/V
    (q/has :name (P/within ["vadas" "marko"]))
    (q/values :age))

;; g.V().has('name',within('vadas','marko')).values('age').mean()
(gremlin gm q/V
    (q/has :name (P/within ["vadas" "marko"]))
    (q/values :age)
    (q/mean))

;; g.V().has('name','marko').out('created') 
(gremlin gm q/V
    (q/has :name "marko")
    (q/out "created"))

;; g.V().has('name','marko').out('created').in('created').values('name')
(gremlin gm q/V
  (q/has :name "marko")
  (q/out "created")
  (q/in "created")
  (q/values :name))

;; .V().has('name','marko').as('exclude').out('created').in('created').where(neq('exclude')).values('name')
(gremlin gm q/V
  (q/has :name "marko")
  (q/as "exclude-self")
  (q/out "created")
  (q/in "created")
  (q/where (P/neq "exclude-self"))
  (q/values :name))

;; g.V().as('a').out().as('b').out().as('c').select('a','b','c')
(gremlin gm q/V
  (q/as "a")
  (q/out) (q/as "b")
  (q/out) (q/as "c")
  (q/select "a" "b" "c"))

;; g.V().group().by(label)
(gremlin gm q/V
  q/group
  (q/by T/label))

;; g.V().group().by(label).by('name')
(gremlin gm q/V
  q/group
  (q/by T/label)
  (q/by :name))






