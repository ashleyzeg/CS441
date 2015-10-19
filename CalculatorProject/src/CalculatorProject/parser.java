package CalculatorProject;

import java.util.ArrayList;

/**
 * Created by azegiest on 10/1/15.
 */

public class parser {

    public ArrayList<Token> tokens;

    //Creating and passing a Position object creates a similar effect as "pass-by-reference"
    public class Position {
        public int x = 0;
        public int val = 0;
        public boolean quitProgram = false;
    }

    Position pos = new Position();

    public parser(ArrayList<ArrayList<Token>> tokenList) {
        System.out.println("*** Welcome to the Parser and Semantic Analyzer! ***");

        int numOfLines = tokenList.size();
        //System.out.println(tokenList.size());

        for (int i = 0; i < numOfLines; i++) {
            tokens = tokenList.get(i); //returns arraylist of current line of tokens
            //System.out.println(tokens);
            int numOfTokens = tokens.size();
            int lineNumber = i+1;
            pos.x = 0;

            parseL(pos, numOfTokens-1, lineNumber);

            System.out.println("Line " + (i+1) + ": Successfully Parsed");
        }
    }

    //Evaluates the syntax for all of the line productions and predict sets
    public void parseL (Position pos, int lastToken, int line) {
        int lt = lastToken;
        int l = line;

        //evaluates syntax for production L -> epsilon
        if (tokens.isEmpty()) {
            return;
        }

        //evaluates syntax for production L -> var = E;
        if (tokens.get(pos.x).type == 4 && tokens.get(pos.x+1).type == 2) { //type 4 = var, type 2 = assign
            //newPos = parseE(pos+2, lt, l);
            pos.x += 2;
            parseE(pos, lt, l);
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
        } else if (pos.x == 0) {
            System.out.println("**Parse Error (Line: " + l + ", Position: " + pos.x + ") A semicolon is not a valid statement");
            System.exit(0);
        } else {
            System.out.println("**Parse Error (Line: " + l + ", Position: " + pos.x + ") Semi-colon expected");
            System.exit(0);
        }

    }

    //Evaluates the syntax for all of the expression productions and predict sets
    public void parseE (Position pos, int lastToken, int line) {
        int lt = lastToken;
        int l = line;

        //evaluates syntax for production E -> var
        if ((tokens.get(pos.x).type == 4) && pos.x != lt) { //type 4 = var
            pos.x++; return;
        }

        //evaluates syntax for production E -> int
        if ((tokens.get(pos.x).type == 0) && pos.x != lt) { //type 0 = int
            pos.val = Integer.parseInt(tokens.get(pos.x).value);
            pos.x++;
            return;
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
        pos.x++;
        parseE(pos, lt, l);
        parseE(pos, lt, l);
    }
}

/* Sample Output
*** Welcome to the Parser! ***
Line 1: Successfully Parsed
Line 2: Successfully Parsed
Line 3: Successfully Parsed
Line 4: Successfully Parsed
Line 5: Successfully Parsed
Line 6: Successfully Parsed
 */
