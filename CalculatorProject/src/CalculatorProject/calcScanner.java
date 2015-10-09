package CalculatorProject;

/**
 * @author Ashley Zegiestowsky
 * Created: September 10, 2015
 * Last Updated: Semptember 14, 2015
 * CS441: Organization of Programming Languages
 * Description: The Scanner (part of calculator program) takes an input data file and prints out a list of tokens and their token types.
 * If the input includes characters not consistent with a token, the program stops and prints an error message.
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.ArrayList;

public class calcScanner {

    public ArrayList<ArrayList<Token>> validLinesArray = new ArrayList<ArrayList<Token>>();
    //public ArrayList validLinesArray = new ArrayList();

    public calcScanner(File input) throws FileNotFoundException {
        System.out.println("*** Welcome to the Scanner ***");

        ArrayList<String> lines = new ArrayList<String>();
        //ArrayList<ArrayList<Token>> validLinesArray = new ArrayList<ArrayList<Token>>();

        try {

            lines = readFileToArray(input);

        } catch (FileNotFoundException ex) {
            System.out.print("Error Occured: " + ex.getMessage());
        }

        //loop through each line to find tokens
        for(int i = 0; i < lines.size(); i++) {
            //ArrayList<Token> validTokensArray = new ArrayList<Token>();
            ArrayList<Token> validTokensArray = new ArrayList<Token>();
            String currentLine = lines.get(i);
            Integer lineNum = i + 1;
            Integer tokenCount = 0;
            System.out.println("**Line " + lineNum + ":" +currentLine);

            //EXTRA CREDIT: Checks for comments at the beginning or end of a line
            if (currentLine.startsWith("//")) {
                currentLine = "";
            } else if(currentLine.contains("//")){
                String[] commentRemoved = currentLine.split("(?=//)");
                currentLine = commentRemoved[0];
            }

            Scanner tokens = new Scanner(currentLine);
            while(tokens.hasNext()) {
                String currentToken = tokens.next();
                //String validToken = "";
                Token validToken;

                if (currentToken.length() > 1) {
                    String[] newTokens = splitTokens(currentToken);

                    for(int nt = 0; nt < newTokens.length; nt++) {
                        validToken = scanToken(newTokens[nt], lineNum);
                        tokenCount++;
                        validTokensArray.add(validToken);
                    }
                } else {
                    validToken = scanToken(currentToken, lineNum);
                    tokenCount++;
                    validTokensArray.add(validToken);
                }

            }

            validLinesArray.add(validTokensArray);
            //System.out.println("**Token Count: " + tokenCount);
            System.out.println("NEW LINE");
        }

        System.out.println("Successfully scanned the input file and all tokens are valid!");

    }

/** Helper Functions */

    /**
     * Method & Desc: splitTokens, This method splits tokens based on certain conditions and regular expressions
     * @param t the token string that is evaluated and split into smaller tokens
     * @return the array of tokens after being evaluated and split accordingly
     */
    public String[] splitTokens(String t) { //TODO: maybe refactor to switch statement
        String[] newTokens = new String[1]; //initialized as size 1 because atleast 1 token will be returned
        //TODO: maybe use recursion
        //TODO: add condition to split tokens by variable name
        if (t.toLowerCase().startsWith("quit")) {		//checks to see if string contains 'quit'
            newTokens = t.split("((?<=quit)|(?=quit))"); //splits string on regex "quit", but keeps delimiters in array
        //EXTRA CREDIT: Allows for variables longer than one character
        } else if (!t.matches("\\d+") && !t.matches("[a-zA-Z]+")) {	//checks for word characters
            newTokens = t.split("(?!^)"); //splits token by each character
        } else if (t.matches("\\d+")) {  //RegEx Ex: Integers >= 10
            newTokens[0] = t;
        } else {
            newTokens[0] = t;
        }
        return newTokens;
    }

    /**
     * Method & Desc: scanToken, This method calls upon other helper functions to evaluate whether each token
     * is included in the accepted list of tokens
     * @param t token value to be evaluated
     * @param line line number of current token
     * @return If the token is valid, the token value is returned. If the token is invalid, an error message occurs
     * and the program stops further execution
     */
    public Token scanToken(String t, Integer line) {
        Token t1, t2, t3, t4, t5, t6;
        Token temp = new Token("", 0);

        t1 = isInteger(t);
        t2 = isArithmeticOperators(t);
        t3 = isAssignmentOperators(t);
        t4 = isSemicolon(t);
        t5 = isVariableName(t);
        t6 = isQuit(t);

//        if (t1.type == 6 || t2.equals("") && t3.equals("") && t4.equals("") && t5.equals("") && t6.equals("")) {
//            System.out.println("**Invalid Token: " + t + ", Line: " + line);
//            System.exit(0);
//        }
        if (t1.type != 6) temp = t1;
        else if (t2.type != 6) temp = t2;
        else if (t3.type != 6) temp = t3;
        else if (t4.type != 6) temp = t4;
        else if (t5.type != 6) temp = t5;
        else if (t6.type != 6) temp = t6;
        else {
            System.out.println("**Invalid Token: " + t + ", Line: " + line);
            System.exit(0);
        }
        return temp;
    }

    /**
     * Method & Desc: isInteger, This method checks the token to see if it is an integer
     * @param t token to be evaluated
     * @return If t is an Integer, return t. If t is not an integer, return the empty string
     */
    public static Token isInteger(String t) {
        if (t.matches("\\d+")) {
            System.out.println("Integer: " + t);
            return new Token(t, Token.INT);
        } else return new Token(t, Token.INVALID);
    }

    /**
     * Method & Desc: isArithmeticOperators, This method checks the token to see if it is an accepted operator
     * @param t token to be evaluated
     * @return If t is an operator, return t. If t is not an operator, return the empty string
     */
    public Token isArithmeticOperators(String t) {
        if (t.matches("[(\\+|\\*|\\-|\\/|\\%|\\^)]")) {
            System.out.println("Operator: " + t);
            return new Token(t, Token.OP);
        } else return new Token(t, Token.INVALID);
    }

    /**
     * Method & Desc: isAssignmentOperators, This method checks the token to see if it is an assignment operator
     * @param t token to be evaluated
     * @return If t is an assignment operator, return t. If t is not an assignment operator, return the empty string
     */
    public Token isAssignmentOperators(String t) {
        if (t.matches("=")) {
            System.out.println("Assignment: " + t);
            return new Token(t, Token.ASSIGN);
        } else return new Token(t, Token.INVALID);
    }

    /**
     * Method & Desc: isSemicolon, This method checks the token to see if it is an semicolon
     * @param t token to be evaluated
     * @return If t is an semicolon, return t. If t is not an semicolon, return the empty string
     */
    public Token isSemicolon(String t) {
        if (t.matches(";")) {
            System.out.println("Semicolon: " + t);
            return new Token(t, Token.SEMI);
        } else return new Token(t, Token.INVALID);
    }

    /**
     * Method & Desc: isVariableName, This method checks the token to see if it is a variable name/identifier
     * that is a single character
     * @param t token to be evaluated
     * @return If t is an variable name, return t. If t is not an variable name, return the empty string
     */
    public Token isVariableName(String t) {
        //EXTRA CREDIT: Allows for variables longer than one character
        if (t.matches("[a-zA-Z]+") && !t.toLowerCase().contains("quit")) {
        //if (t.matches("\\w+") && !t.toLowerCase().contains("quit")) {
            System.out.println("Variable: " + t);
            return new Token(t, Token.VAR);
        } else return new Token(t, Token.INVALID);
    }

    /**
     * Method & Desc: isQuit, This method checks the token to see if it is the keyword quit
     * @param t token to be evaluated
     * @return If t is the keyword quit, return t. If t is not the keyword quit, return the empty string
     */
    public Token isQuit(String t) {
        if (t.toLowerCase().matches("quit")) {
            System.out.println("Keyword Quit: " + t);
            return new Token(t, Token.QUIT);
        } else return new Token(t, Token.INVALID);
    }

    /**
     * Method & Desc: readFileToArray, This method takes the input file and dynamically stores each line in an ArrayList
     * @param input text file provided by the user input
     * @return ArrayList of the lines in the text file (including blank lines)
     * @throws FileNotFoundException If the file does not exist, an error message is displayed and the program ends
     */
    public ArrayList<String> readFileToArray(File input) throws FileNotFoundException {

        ArrayList<String> linesArray = new ArrayList<String>();

        try {

            Scanner in = new Scanner(input);

            while(in.hasNextLine()) {
                String next = in.nextLine();
                //System.out.println(next);
                linesArray.add(next);
            }

        } catch (FileNotFoundException ex) {
            System.out.print("Error Occured: " + ex.getMessage());
        }

        return linesArray;
    }
}
