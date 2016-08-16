(println "---defroutes")
(defvar myname "m0cchi")
(defun greeting (name)
  (str "hello, " name))
(defroutes context
  ("/" (read (file "file/index.html")))
  ("/name" (template (read (file "file/template.list.html"))))
  ("/code-runner" (template (read (file "file/code-runner.html"))))
  ("/img/preview.png" (read (file "file/img/preview.png")))
  ("/byte" (new java.lang.String (read (file "bootstrap.lisp"))))
  ("/greeting" "hello"))
(println "---start http service")
(defvar app (http-service 18080 context))
;; -- new context
(defun append-context (app path env context)
  (. createContext app path
     (new net.m0cchi.util.ElementContext env
          (new net.m0cchi.value.JValue context))))

(append-context app "/later" (new net.m0cchi.value.Environment env)
                (str "later proc"))

(. removeContext app "/later")

(append-context app "/later" (new net.m0cchi.value.Environment env)
                (str "over write"))
