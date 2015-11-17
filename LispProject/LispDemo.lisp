;sbcl for thomas
;top - used to go to top of errors
(+ 2 3) ;prefix notation

'(+ 2 3) ;quote mark doesn't include first mark

;LISP - stands for List Processing (stored as linked lists)

(car '(a b c)) //car gives you first item in List
;output: A

(cdr '(a b c))
;output: (B C)

(car (cdr '(a b c)))
;output: B

(car (cdr (cdr '(a b c))))
;output: C

(caddr '(a b c))
;output: C

;build list - use cons 
(cons 'a '(b c))
;output: (a b c)

;create a function
(defun f (x) (body) )
;output: f //means f is now defined

(defun f(x) (* 5 x))

(f 4)
;output: 20

(f (car '(3 4)))
;output: 15

;condition statement
(defun absVal (x)
	(cond
		( (< x 0) (- 0 x) )
		( T x)
	)
)

(absVal 45)
;output: 45

(absVal -5)
;output: 5

;recursive function
(defun fact (n)
	(cond 
		( (= 0 n) 1)
		( T (* n (fact (- n 1))))
	)
)
;output: fact ;fact function is now defined

(fact 3)
;output: 6

(fact 8)
;output: ...

(defun append2 (x y)
	(cond
		((null x) y) ;x is empty
		((null y) x) ;y is empty
		((null (cdr x)) (cons (car x) y)) ;x has one item, glue onto  beginning of y
		(T  (cons (car x) (append2 (cdr x) y))) ;x has more than one item
	)
)

(append2 '(1 2) '(3 4))
;output: (1 2 3 4)

(append2 '(1 2 3 4 5) '(a b c d e f))
;output: (1 2 3 4 5 a b c d e f)

;Function that writes a Function
(defun makef (x)
	(lambda (y)(+ x y))
)

(funcall (makef 5) 7)
;output: 12

(funcall (makef 8) 10)
;output: 18

;Take a function as an argument
(defun doubler (f) 
	(labmda (x)
		(funcall f (funcall f x))
	)
)
;output: DOUBLER

(funcall (doubler (makef 5)) 7)
;output: 17

(funcall (doubler (doubler (makef 5))) 7)
;output: 27


;fixes first argument 
(defun curry (f x)
	(labmda (y) (funcall f x y))
)

(funcall (curry '* 6) 2)
;output: 12

;filter function example
(defun small (x) 
	(< x 10)
)

(defun filter (f L)	; f = filter function, L = list to be filtered
	(
		((null L) nil) ; nil is empty list
		((funcall f (car L)) 
			(cons (car L) (filter f (cdr L))))
		(T (filter f (cdr L)))

	)
)

;sample function call
; (filter 'small '(1 21 15 3))
; output = (1 3)

; list function
(list 'a)
;output (A) ; makes an item a list

; glue function
(defun glue (left pivot right) ; left & right are lists, pivot is not
	(append left (cons pivot right)) ; cons appends an item to the front of the list
)

(glue '(a b c) 'x '(1 2 3))
;output: (A B C 1 2 3)

;quicksort function
(defun quick (L) 
	(cond
		((null L) nil)	; empty list
		((null (cdr L)) L)	; item has 1 item - already sorted
		(T 
			(glue 
				(quick (filter ; <= pivot
					#'(lambda (x) (<= x (car L))) ; lambda expression to write function as argument without defun
					(cdr L)
				))
				(car L) ; the pivot
				(quick (filter 
					#'(lambda (x) (>= x (car L))) ; lambda expression to write function literal
					(cdr L)
				))	; >= pivot
			)
		)
	)
)

(quick '(8 1 7 2 6 3 5 4))
;output (1 2 3 4 5 6 7 8)

;function to make list n..1
(defun thefinalcountdown (n)
	(cond
		((= 1 n) '(1))
		(T (cons n (thefinalcountdown (- n 1))))
	)
)

(quick (thefinalcountdown (1000)))

;mapcar - apply a function to every item in the list

(defun add1 (x) (+ x 1))

(add1 3)
;output: 4

(mapcar 'add1 '(-4 0 12 76))
;output: -3, 1, 13, 77

(mapcar #'(lambda (y) (* 2 y)) '(-4 0 12 76))
;output: (-8 0 24 152)

;power set problem
; set: '(1 2)
; powerset: '(nil, (1) (2) (1 2))
(defun powerset (L)
	(cond 
		((null L) '(nil))
		(T
			(append
				(powerset (cdr L))
				(mapcar 
					#'(lambda (x) (cons (car L) x))
					(powerset (cdr L))
				)
			)
		)

	)
)

;return length of list

(length '(1 2 3 4))

;function with no arguments
(defun f() 3)

(let (arguments) stuff) ;define local variables using let (don't need variables)

























