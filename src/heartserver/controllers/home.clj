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




(defn callArrToRoom [content]

  (try
      (do
        (ok {:success true :result  (json/read-str content)})
        )
    (catch Exception ex
      (ok {:success false :message (.getMessage ex)})
      )

    )

  )

(defn changeStatus [lineno status  room]
  (timbre/info "fire changeStatus : " room ",status:" status ",lineno: " lineno )
  (doseq [channel (keys @websocket/channel-hub)]
    (if (= (get  (get @websocket/channel-hub channel) "type") "mainscreen")

      (send! channel (generate-string
                       {
                         :room room
                         :lineno lineno
                         :type "changestatus"
                         }
                       )
        false)(when (= (get  (get @websocket/channel-hub channel) "content") room)

                (send! channel (generate-string
                                 {
                                   :room room
                                   :lineno lineno
                                   :type "changestatus"
                                   }
                                 )
                  false)

                )

      )

    )
  (ok {:success true})


  )
(defn callToRoom [lineno name  room status]

  (timbre/info "fire callToRoom : " room ",name:" name ",lineno: " lineno )
  (future (doseq [channel (keys @websocket/channel-hub)]
    (if (= (get  (get @websocket/channel-hub channel) "type") "mainscreen")

      (send! channel (generate-string
                       {
                         :room room
                         :name name
                         :status status
                         :lineno lineno
                         :type "callpatient"
                         }
                       )
        false)(when (= (get  (get @websocket/channel-hub channel) "content") room)

                (send! channel (generate-string
                                 {
                                   :room room
                                   :name name
                                   :status status
                                   :lineno lineno
                                   :type "callpatient"
                                   }
                                 )
                  false)

                )

      )

    ))
  (ok {:success true})

  )

(defn firebychangeroom [oldno newno newname]

  (timbre/info "fire by change room  from  oldno : " oldno ",newno:" newno ",newname:" newname )
  (doseq [channel (keys @websocket/channel-hub)]
    (when (= (get  (get @websocket/channel-hub channel) "content") oldno)

      (do (send! channel (generate-string
                           {

                             :roomno oldno
                             :data  {:newno newno :newname newname}
                             :type "changeroom"
                             }
                           )
            false)
        (swap! websocket/channel-hub assoc channel (conj (get @websocket/channel-hub channel) {:content newno}) )
        )



      )
    )

  )

(defn fireprop [room name value]

  (timbre/info "fire prop room : " room ",name:" name ",value: " value)
  (doseq [channel (keys @websocket/channel-hub)]
    (when (= (get  (get @websocket/channel-hub channel) "content") room)

      (send! channel (generate-string
                       {
                         :room room
                         :name name
                         :value value
                         :type "fireprop"
                         }
                       )
        false)

      )
    )
  (ok {:success true})

  )

(defn firerefreshsystem [room]

  (timbre/info "fire refresh system by  room : " room )

  (doseq [channel (keys @websocket/channel-hub)]
    (when (= (get  (get @websocket/channel-hub channel) "content") room)

      (send! channel (generate-string
                       {
                         :room room
                         :type "freshsystem"
                         }
                       )
        false)

      )
    )
  (ok {:success true})
  )

(defn clearscreen [room]

  (timbre/info "fire clear room : " room )
  (doseq [channel (keys @websocket/channel-hub)]
    (when (= (get  (get @websocket/channel-hub channel) "content") room)

      (send! channel (generate-string
                       {
                         :room room
                         :type "clearscreen"
                         }
                       )
        false)

      )
    )
  (ok {:success true})

  )




