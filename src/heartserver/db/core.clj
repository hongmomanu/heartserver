(ns heartserver.db.core
  (:use korma.core
        [korma.db :only [defdb with-db]])
  (:require
    [clojure.java.jdbc :as jdbc]
    ;[yesql.core :refer [defqueries]]
    [taoensso.timbre :as timbre]
    [clojure.java.io :as io]
    [environ.core :refer [env]])
  (:import java.sql.BatchUpdateException))

(def datapath (str (.getName (io/file ".")) "/"))

(defn get-config-prop []
  (let [filename (str datapath "server.config")]
    (read-string (slurp filename))
    )
  )

(def db-sqlserver {:classname "net.sourceforge.jtds.jdbc.Driver"
                   :subprotocol "jtds:sqlserver"
                   :subname (let [prop (get-config-prop)
                                  sqlserver (:sqlserver prop)
                                  ]
                              (str (:address sqlserver) ";user="
                                   (:user sqlserver ) ";password=" (:pass sqlserver)
                                   ))
                   })



(defn getdoctorinfobyid [code imghost]
  (with-db db-sqlserver

           (exec-raw [(str "select name,title,('" imghost "'+pic) as img,subjects,cast(cv as nvarchar(1000)) as cv,case sex when '1' then '男' else '女' end as sex from a_employee_introduction where a_employee_introduction.code=?")   [code]] :results)
           )

  )