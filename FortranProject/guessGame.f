C Ashley Zegiestowsky
C CS441
C 10/30/2015
C Fortran 77 Project: Guessing Game. This program picks a random number between 1 and 100. 
C the user guesses the number, and then the program says whether the number is too high, 
C too low, or correct!

C23456789
      program guessGame
	  implicit none
	  
C -- Program Variables
	  integer randNum
	  integer userInput
	  
	  print *, "*** Welcome to the Fortran 77 Guessing Game!!! ***"
	  print *, "I have a number between 1 and 100. Can you guess it?"
	  
C -- Generate random number between 1 and 100	  
	  randNum = int(100*rand(0))+1
	  
C -- Label for goto Jump and user inputs guess	  
 10	  print *, "Please enter an integer between 1 and 100:"
	  read *, userInput
	  
C -- If incorrect guess, a specific message is displayed 
C -- and the user is prompted to guess again
      if(userInput.ne.randNum) then 

		  if(userInput.lt.1 .OR. userInput.gt.100) then
			print *, "That number is not between 1 and 100"
			goto 10
		  elseif(userInput.lt.randNum) then
			print *, "Too Low, dimwit!" 
		  elseif(userInput.gt.randNum) then
			print *, "Too High, elbow nose!" 
		  endif
C -- goto label 10 simulates a while loop structure 
C -- while the userInput is not equal to the randNum		 
	   goto 10
	   endif
	   
C -- If correct guess, message displays and program ends		 
      print *, "Correct, you win!!"	  
	  end
	  
C  Sample Output:
C  *** Welcome to the Fortran 77 Guessing Game!!! ***
C  I have a number between 1 and 100. Can you guess it?
C  Please enter an integer between 1 and 100:
C 150
C  That number is not between 1 and 100
C  Please enter an integer between 1 and 100:
C 50
C  Too High, elbow nose!
C  Please enter an integer between 1 and 100:
C 0
C  That number is not between 1 and 100
C  Please enter an integer between 1 and 100:
C 1
C  Correct, you win!!