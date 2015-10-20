package CalculatorProject;

/**
 * @author Ashley Zegiestowsky
 * Created: October 1, 2015
 * Last Updated: October 19, 2015
 * CS441: Organization of Programming Languages
 * Description: The Parser and Sematnic Analyzer (part of calculator program) parses the input file line-by-line for
 * syntactical correctness. While the input file is being parsed the semantic analyzer stores variables and computes
 * the results for each line.
 */

import java.lang.Math;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Stack;

public class parser {

    private ArrayList<Token> tokens;
    private Hashtable<String, Integer> symbols = new Hashtable<String, Integer>();

    //Creating and passing a Position object creates a similar effect as "pass-by-reference"
    private class Position {
        public int x = 0;
        public int val = 0;
        public boolean quitProgram = false;
    }

    Position pos = new Position();

    public parser(ArrayList<ArrayList<Token>> tokenList) {

        int numOfLines = tokenList.size();

        for (int i = 0; i < numOfLines; i++) {
            tokens = tokenList.get(i); //returns arraylist of current line of tokens
            int numOfTokens = tokens.size();
            int lineNumber = i+1;
            pos.x = 0;

            parseL(pos, numOfTokens - 1, lineNumber);

            //System.out.println("Line " + (i+1) + ": Successfully Parsed");
        }
    }

    /**
     * Method & Desc: parseL, This method parses each line for syntactical correctness based on the grammar provided
     * for the Line productions
     * @param pos the position object that holds the tokens position, computed value
     * @param lastToken an integer value of the number of tokens on the line
     * @param line an integer value of the current line number (used to display error messages)
     */
    public void parseL (Position pos, int lastToken, int line) {
        int lt = lastToken;
        int l = line;

        //evaluates syntax for production L -> epsilon
        if (tokens.isEmpty()) {
            return;
        }

        //evaluates syntax for production L -> var = E;
        if (tokens.get(pos.x).type == 4 && tokens.get(pos.x+1).type == 2) { //type 4 = var, type 2 = assign
            String currentVar = tokens.get(pos.x).value.toUpperCase();
            pos.x += 2;
            parseE(pos, lt, l);
            symbols.put(currentVar, pos.val);
        }

        //evaluates syntax for production L -> E;
        if (tokens.get(pos.x).type == 0 || tokens.get(pos.x).type == 1 || tokens.get(pos.x).type == 4 && tokens.get(pos.x+1).type != 2) {
            parseE(pos, lt, l);
        }

        //evaluates syntax for production L -> quit;
        if (tokens.get(pos.x).type == 5 && pos.x != lt) { //type 5 = quit
            pos.quitProgram = true;
            pos.x++;
        }

        //check for semicolon at end
        if(tokens.get(pos.x).type == 3 && pos.x == lt) {
            if (pos.quitProgram) {
                System.out.println("End of program.");
                System.exit(0);
            } else {
                System.out.println(pos.val);
                return;
            }
        } else if ((pos.x == 0) && (tokens.get(pos.x).type == 3)) {
            System.out.println("**Parse Error (Line: " + l + ", Position: " + pos.x + ") A semicolon is not a valid statement");
            System.exit(0);
        } else {
            System.out.println("**Parse Error (Line: " + l + ", Position: " + pos.x + ") Semi-colon expected");
            System.exit(0);
        }

    }

    /**
     * Method & Desc: parseE, This method parses each line for syntactical correctness based on the grammar provided
     * for the Expression productions
     * @param pos the position object that holds the tokens position, computed value
     * @param lastToken an integer value of the number of tokens on the line
     * @param line an integer value of the current line number (used to display error messages)
     */
    public void parseE (Position pos, int lastToken, int line) {
        int lt = lastToken;
        int l = line;
        int val1, val2;

        //evaluates syntax for production E -> var
        if ((tokens.get(pos.x).type == 4) && pos.x != lt) { //type 4 = var
            if (symbols.containsKey(tokens.get(pos.x).value.toUpperCase())) {
                pos.val = symbols.get(tokens.get(pos.x).value.toUpperCase());
                pos.x++; return;
            } else {
                System.out.println("***Semantic Error: (Line "+l+", Position "+pos.x+") Variable '"+tokens.get(pos.x).value+"' has not been declared.");
                System.exit(0);
            }

        }

        //evaluates syntax for production E -> int
        if ((tokens.get(pos.x).type == 0) && pos.x != lt) { //type 0 = int
            pos.val = Integer.parseInt(tokens.get(pos.x).value);
            pos.x++; return;
        }

        //Displays an error message and exits program if the token being evaluated is not a variable, integer, or operator
        if (tokens.get(pos.x).type != 1 && pos.x != lt) { //type 1 = op
            System.out.println("**Parse Error (Line: " + l + ", Position: " + pos.x + ") Variable, integer, or operator expected");
            System.exit(0);
        }

        //Displays an error message and exits program if there are no more tokens to be evaluated
        if (pos.x == lt) {
            System.out.println("**Parse Error (Line: " + l + ", Position: " + pos.x + ") Ran out of tokens");
            System.exit(0);
        }

        //evaluates syntax for production E -> opEE
        Stack operators = new Stack();

        operators.push(tokens.get(pos.x).value);
        pos.x++;

        parseE(pos, lt, l);
        val1 = pos.val;

        parseE(pos, lt, l);
        val2 = pos.val;

        switch (operators.pop().toString()) {
            case "+":
                pos.val = val1 + val2; break;
            case "-":
                pos.val = val1 - val2; break;
            case "*":
                pos.val = val1 * val2; break;
            case "/":
                pos.val = val1 / val2; break;
            case "%":
                pos.val = val1 % val2; break;
            case "^":
                pos.val = (int) Math.pow(val1, val2); break;
            default: System.out.println("Operator not supported.");
        }
    }
}

