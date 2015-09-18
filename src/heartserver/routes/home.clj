(ns heartserver.routes.home
  (:require [heartserver.layout :as layout]
            [compojure.core :refer [defroutes GET]]
            [ring.util.http-response :refer [ok]]
            [heartserver.controllers.home :as home]
            [clojure.java.io :as io]))

(defn home-page []
  (layout/render
    "home.html" {:docs (-> "docs/docs.md" io/resource slurp)}))

(defn about-page []
  (layout/render "about.html"))

(defroutes home-routes
  (GET "/" [] (home-page))
  (GET "/helloheart" [] (ok {:success true}))
  (GET "/fireprop" [room name value] (home/fireprop room name value))
  (GET "/firerefreshsystem" [room] (home/firerefreshsystem room))
  (GET "/clearscreen" [room] (home/clearscreen room))
  (GET "/callArrToRoom" [content] (home/callArrToRoom content))
  (GET "/callToRoom" [type name status id room] (home/callToRoom type name status id room))
  (GET "/about" [] (about-page)))

