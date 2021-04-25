import java.util.ArrayList;
import java.util.Locale;

/**
 * Parser is used to take in a single string and creates an arraylist of string delimited by white space.
 * The parser will also add white space around parenthesis.
 */
public class Parser {

    private String wholeString;
    private ArrayList<String> parsedString = new ArrayList<>();
    private Token tokenList;

    public Token getTokenList() { return tokenList; }

    public String getWholeString() { return wholeString; }
    public void setWholeString(String wholeString) { this.wholeString = wholeString; }

    public void setParsedString(ArrayList<String> parsedString) { this.parsedString = parsedString; }
    public ArrayList<String> getParsedString() { return parsedString; }


    /**
     * Default constructor sets everything to null.
     */
    public Parser() {
        this.wholeString = null;
        this.parsedString = null;
    }

    /**
     * Consructor that is most often used. This constructor essentially takes in a single string and creates
     * a list of tokens that can be evaluated later.
     *
     * Parser will take in a single string and parse the string into an arraylist of strings. These strings are then ran
     * through a method from the Token class, parseTokenList, and creates an arraylist of Tokens. The arraylist of
     * Tokens are saved in tokenList which can be used for the Executioner class.
     *
     * @param string The user input string that needs to be parsed and converted to tokens.
     */
    public Parser(String string) throws Exception {
        this.wholeString = string;
        parseString(this.wholeString);

        if(isValid()) {
            this.tokenList = Token.parseTokenList((ArrayList<String>) parsedString.clone());
        } else {
            throw new Exception("The input string does not have balanced parenthesis!");
        }

    }


    /**
     *  The method parseString will take in a string and add spaces around parenthesis and quotes. Then the string
     *  will be delimited by white space to make an arraylist of strings.
     *
     *  Each delimited string will be added to the parser's attribute parsedString, which is an arraylist of strings.
     * @param string
     */
    public void parseString(String string) {

        string = string.replace("(", " ( ").replace(")", " ) ");
        string = string.replace("'", " ' ");
        String[] strings = string.trim().split("\\s+");

        for(String i : strings) {
            parsedString.add(i);
        }

    }






    public void printParsedString() { System.out.println(parsedString); }


    /**
     * Checks if the user input parenthesis are balanced!
     * @return true or false depending on if the string is valid
     */
    public boolean isValid() {
        int count = 0;
        for(String token : parsedString){
            if(token.equals("(")) {
                count++;
            } else if (token.equals(")")) {
                count--;
            }

            if(count < 0) {
                return false;
            }
        }
        if(count != 0) {
            return false;
        }
        return true;
    }


    /*public int findEndExpression(String expression) {
        int count = 0;
        for(int i = 0; i < expression.length(); i++) {
            if(expression.charAt(i) == '(') {
                count++;
            } else if(expression.charAt(i) == ')') {
                count--;
            }

            if(count == 0) {
                return i;
            }
        }
        return 0;
    }*/
}
