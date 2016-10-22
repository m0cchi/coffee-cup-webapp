(println "---defroutes")
(defvar myname "m0cchi")
(defun greeting (name)
  (str "hello, " name))

(defmacro while (cond &rest body)
  (. add body 0 (new net.m0cchi.function.DoList))
  (. add body 0 (new net.m0cchi.function.NewList))
  (list 'loop
    (list 'if cond body (list 'kill (new net.m0cchi.exception.handler.LoopInterrupt)))))

(defun images ()
  (defvar dir (new java.io.File "/images/"))
  (defvar l (array-to-s-list (. listFiles dir)))
  (defvar it (s-list-to-iterator l))
  (defvar ret (list))
  (while (. hasNext it)
      (defvar path (. next it))
      (if (matches ".*\\.(png|jpg|gif)$" (. toString path))
      (. add ret path)))
  ret)


(defun map-get (map str)
  (. get map (new net.m0cchi.value.Value (invoke-static valueOf net.m0cchi.value.AtomicType "LETTER") str)))

(defun replace-string (str test)
  (defvar it (. iterator (. keySet test)))
  (while (. hasNext it)
    (do-list
      (defvar key (. next it))
      (setq str (. replaceAll str key (map-get test key)))
      str)))
 
 (defroutes context
  ("/" (read (file "file/index.html")))
  ("/name" (template (read (file "file/template.list.html"))))
  ("/code-runner" (template (read (file "file/code-runner.html"))))
  ("/img/preview.png" (read (file "file/img/preview.png")))
  ("/byte" (new java.lang.String (read (file "bootstrap.lisp"))))
  ("/images" (read (file (. get (images) 0))))
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