(ns tp.getting-started.first-five-minutes
  ";;http://tinkerpop.apache.org/docs/current/tutorials/getting-started/ "
  (:require [clojure.test :refer [deftest testing is]]            
            [clojurewerkz.ogre.core :as q]
            [clojurewerkz.ogre.util :as util]
            [clojure.pprint :as pp]
            [tp.getting-started.helper :as help])
  (:import (org.apache.tinkerpop.gremlin.structure T Vertex)
           (org.apache.tinkerpop.gremlin.process.traversal P Traversal)
           (org.apache.tinkerpop.gremlin.tinkergraph.structure TinkerGraph TinkerFactory)))
 

;; The First Five Minutes


(def graph (help/createModern))
;; (def graph (TinkerFactory/createModern))
(.toString graph)

(def g (q/traversal graph))
(.toString g)

(defmacro gremlin 
  "get behavior similar to the gremlin console"
  [& wrap] 
  `(util/into-seq! (q/traverse ~@wrap)))


;; Get all the vertices in the Graph.
;; g.V()
(gremlin g (q/V)
           (q/id))

;; Get a vertex with the unique identifier of "1".
;; g.V(1)
(gremlin g (q/V 1))

;; Get the value of the name property on vertex with the unique identifier of "1".
;; g.V(1).values("name")
(gremlin g (q/V 1)
    (q/values :name))

;; Get the edges with the label "knows" for the vertex with the unique identifier of "1".
;; g.V(1).outE('knows')
(gremlin g (q/V 1)
    (q/outE :knows))

;; Get the names of the people that the vertex with the unique identifier of "1" "knows".
;; g.V(1).outE('knows').inV().values('name')
(gremlin g (q/V 1)
    (q/outE :knows)
    (q/inV)
    (q/values :name))

;; Note that when one uses outE().inV() as shown in the previous command, 
;; this can be shortened to just out() 
;; (similar to inE().inV() and in() for incoming edges).
;; g.V(1).out('knows').values('name')
(gremlin g (q/V 1)
    (q/out :knows)
    (q/values :name))

;; Get the names of the people vertex "1" knows who are over the age of 30.
;; g.V(1).out('knows').has('age', gt(30)).values('name')
(gremlin g (q/V 1)
    (q/out :knows)
    (q/has :age (P/gt 30))
    (q/values :name))



