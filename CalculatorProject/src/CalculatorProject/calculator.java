package CalculatorProject;

/**
 * @author Ashley Zegiestowsky
 * Created: September 10, 2015
 * Last Updated: October 19, 2015
 * CS441: Organization of Programming Languages
 * Description: A calculator program that consists of a scanner, parser, and semantic analyzer
 */

import java.io.*;
import java.util.*;

public class calculator {
    public static void main(String [] args) throws IOException{

        System.out.println("*** Welcome to the Calculator Program!!! ***");
        System.out.println("Enter input file name and extension: ");

        Scanner in = new Scanner(System.in);
        File path = new File("");
        File input = new File(path.getAbsolutePath() + "/src/CalculatorProject/", in.nextLine());

        calcScanner s = new calcScanner(input);

        parser p = new parser(s.validLinesArray);
    }
}

/*
SAMPLE INPUT FILE: input.txt
a=3; //comment
  + 5 31 ;
b = + * A 4 - 5 4;
testVar = 10 ;
+ ^ testVar 2 - a B;
quit;


SAMPLE OUTPUT:
*** Welcome to the Calculator Program!!! ***
Enter input file name and extension:
input.txt
3
36
13
10
90
End of program.
*/
