(println "---defroutes")
(defroutes context
          ("/" "<a href='/greeting'>greeting</a>")
          ("/greeting" "hello"))
(println "---start http service")
(http-service 18080 context)
