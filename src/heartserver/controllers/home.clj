(ns heartserver.controllers.home

  (:import
    (java.text SimpleDateFormat)
    (java.util  Date Calendar)
    )
  (:use compojure.core org.httpkit.server)
  (:use ruiyun.tools.timer)
  (:use [taoensso.timbre :only [trace debug info warn error fatal]])
  (:require [heartserver.db.core :as db]
            [heartserver.layout :as layout]
            [ring.util.http-response :refer [ok]]
            [taoensso.timbre :as timbre]
            [cheshire.core :refer :all]
            [clojure.data.json :as json]
            [heartserver.public.websocket :as websocket]
            [heartserver.public.funcs :as funcs]

            )
  )




(defn callToRomm [content]

  (try
      (do
        (ok {:success true :result  (json/read-str content)})
        )
    (catch Exception ex
      (ok {:success false :message (.getMessage ex)})
      )

    )

  )




