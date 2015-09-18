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
  (GET "/callToRomm" [content] (home/callToRomm content))
  (GET "/about" [] (about-page)))
