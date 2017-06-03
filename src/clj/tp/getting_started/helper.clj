(ns tp.getting-started.helper
  ";;http://tinkerpop.apache.org/docs/current/tutorials/getting-started/ "
  (:require [clojure.test :refer [deftest testing is]]            
            [clojurewerkz.ogre.core :as q]
            [clojurewerkz.ogre.util :as util]
            [clojure.pprint :as pp])  
  (:import (org.apache.tinkerpop.gremlin.tinkergraph.structure TinkerGraph TinkerFactory TinkerGraph$DefaultIdManager)
           (org.apache.commons.configuration BaseConfiguration)))
 
(defn createModern []
  (let [conf 
        (doto (BaseConfiguration.)
          (.setProperty TinkerGraph/GREMLIN_TINKERGRAPH_VERTEX_ID_MANAGER 
            (.name TinkerGraph$DefaultIdManager/INTEGER))
          (.setProperty TinkerGraph/GREMLIN_TINKERGRAPH_EDGE_ID_MANAGER 
            (.name TinkerGraph$DefaultIdManager/INTEGER))
          (.setProperty TinkerGraph/GREMLIN_TINKERGRAPH_VERTEX_PROPERTY_ID_MANAGER 
            (.name TinkerGraph$DefaultIdManager/INTEGER)))]
    (doto (TinkerGraph/open conf)
        (TinkerFactory/generateModern))))

