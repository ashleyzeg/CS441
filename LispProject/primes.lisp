;Ashley Zegiestowsky
;CS441
;Jon Sorenson
;November 13, 2015
;LISP Program: Takes a number n and prints out all the prime numbers up to n

;determine primality of number n
(defun isPrime (n x)
	(cond
		((< x 2) T) ;x < 2
		(T			;x >= 2
			(cond	
				((= (mod n x) 0) nil)	;if n divides evenly (not prime), return false
				(T (isPrime n (- x 1))) ;else, decrement x and recursively call function
			)
		)
	)
)

;list prime numbers between 2 to n
(defun listprimes (n)
	(cond
		( (< n 2) nil )	;n < 2, return empty list 
		( (= n 2) (list n) )	;n = 2, add 2 to list
		( T	; n > 2
			(append 
				(listprimes (- n 1)) ;Tail recursion (recursively call previous number)
				(cond ((isPrime n (isqrt n)) (list n)))	;check if prime, pass n and sqrt(n)
			)
		)
	)
)

;Sample Run
;* (listprimes 0)

;NIL
;* (listprimes -10)

;NIL
;* (listprimes 2)

;(2)
;* (listprimes 10)

;(2 3 5 7)
;* (listprimes 100)

;(2 3 5 7 11 13 17 19 23 29 31 37 41 43 47 53 59 61 67 71 73 79 83 89 97)