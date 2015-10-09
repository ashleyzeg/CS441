package CalculatorProject;

/**
 * @author Ashley Zegiestowsky
 * Created: September 10, 2015
 * Last Updated: Semptember 14, 2015
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
