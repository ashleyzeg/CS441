package CalculatorProject;

/**
 * Created by azegiest on 10/1/15.
 */
public class Token {
    public String value;
    public int type;

    public Token (String val, int t) {
        value = val;
        type = t;
    }

    public static final int INT = 0;
    public static final int OP = 1;
    public static final int ASSIGN = 2;
    public static final int SEMI = 3;
    public static final int VAR = 4;
    public static final int QUIT = 5;

    public static final int INVALID = 6;

}
