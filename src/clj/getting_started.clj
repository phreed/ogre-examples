(ns tp.getting-started
  ";;http://tinkerpop.apache.org/docs/current/tutorials/getting-started/ "
  (:require [clojure.test :refer [deftest testing is]]            
            [clojurewerkz.ogre.core :as q]
            [clojurewerkz.ogre.util :as util]
            [clojure.pprint :as pp])  
  (:import (org.apache.tinkerpop.gremlin.structure T Vertex)
           (org.apache.tinkerpop.gremlin.process.traversal P Traversal)
           (org.apache.tinkerpop.gremlin.tinkergraph.structure TinkerGraph TinkerFactory)))
 

(def graph (q/open-graph {(TinkerGraph/GRAPH) 
                          (.getName TinkerGraph)}))
(TinkerFactory/generateModern graph)
(.toString graph)

(def g (q/traversal graph))
(.toString g)


(util/into-seq! 
  (q/traverse g (q/V)
             (q/id)))

(util/into-seq! 
  (q/traverse g (q/V 1)))

(util/into-seq! 
  (q/traverse g (q/V (int 1))))
   
(def fro (mapv int [2]))

(util/into-seq! 
  (q/traverse g (q/V (first fro))
    (q/values :name)))


;; "g.V(v1Id).as('a').out('created').addE('createdBy').to('a').property('weight', 2.0d)" 
(.toString
  (q/traverse 
    g 
    q/V 
    (q/as :a)                
    (q/out :created)               
    (q/addE :createdBy)  
    (q/to :a)                
    (q/property :weight 2.0)))

 
(q/traverse 
  g
  q/V 
  (q/match
    (q/__ (q/as :a) (q/out :created) (q/as :b))
    (q/__ (q/as :b) (q/has :name "lop"))
    (q/__ (q/as :b) (q/in :created) (q/as :c))
    (q/__ (q/as :c) (q/has :age 29)))
  (q/select :a :c) (q/by :name)
  (util/into-seq!))

;; What are the names of Gremlin's friends' friends?
(q/traverse g q/V
  (q/has :name :gremlin)
  (q/out :knows)
  (q/out :knows))


(q/traverse
  g
  q/V
  (q/has :person :name :gremlin)
  (q/out :knows)
  (q/out :created)
  ;; (q/has-label :knows)
  (q/values "stars")
  (q/mean))

(q/traverse
  g
  q/V
  (q/has :person :name :gremlin)
  (q/out :knows)
  (q/out :created)
  ;; (q/has-label :knows)
  (q/values "stars")
  (q/mean))


