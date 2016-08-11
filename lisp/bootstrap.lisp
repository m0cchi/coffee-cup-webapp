(println "---defroutes")
(defroutes context
          ("/" (read (file "file/index.html")))
          ("/img/preview.png" (read (file "file/img/preview.png")))
          ("/greeting" "hello"))
(println "---start http service")
(http-service 18080 context)
