import java.util.ArrayList;
import java.util.Locale;

/**
 * Token contains a number, an operator, a string, a boolean, or an arraylist of tokens.
 * Token is basically an abstract syntax tree, which contains nested expressions.
 */
public class Token {
    private Double number;
    private Operator operator;
    private String string;
    private ArrayList<Token> tokenArrayList;
    private Boolean bool;
    private Boolean literal = false;

    public Boolean getLiteral() { return literal; }
    public void setLiteral(Boolean literal) { this.literal = literal; }

    public ArrayList<Token> getTokenArrayList() { return tokenArrayList; }
    public void setTokenArrayList(ArrayList<Token> tokenArrayList) { this.tokenArrayList = tokenArrayList; }

    public Double getNumber() { return number; }
    public void setNumber(Double number) { this.number = number; }

    public Operator getOperator() { return operator; }
    public void setOperator(Operator operator) { this.operator = operator; }

    public String getString() { return string; }
    public void setString(String string) { this.string = string; }

    public Boolean getBool() { return bool; }
    public void setBool(Boolean bool) { this.bool = bool; }



    public Token() {
        this.number = null;
        this.operator = null;
        this.string = null;
        this.tokenArrayList = null;
        this.bool = null;
    }

    public Token(double number) {
        this.number = number;
        this.operator = null;
        this.string = null;
        this.tokenArrayList = null;
        this.bool = null;
        this.literal = true;
    }

    public Token(Operator operator) {
        this.number = null;
        this.operator = operator;
        this.string = null;
        this.tokenArrayList = null;
        this.bool = null;
    }

    public Token(ArrayList<Token> tokenArray) {
        this.number = null;
        this.operator = null;
        this.string = null;
        this.tokenArrayList = tokenArray;
        this.bool = null;
    }

    public Token(String string) {
        this.number = null;
        this.operator = null;
        this.string = string;
        this.tokenArrayList = null;
        this.bool = null;
        this.literal = true;
    }

    public Token(Boolean bool) {
        this.number = null;
        this.operator = null;
        this.string = null;
        this.tokenArrayList = null;
        this.bool = bool;
        this.literal = true;
    }

    public boolean isOperator() { return this.operator != null; }
    public boolean isNumber() { return this.number != null; }
    public boolean isString() { return this.string != null; }
    public boolean isTokenArrayList() { return this.tokenArrayList != null; }
    public boolean isBoolean() { return this.bool != null; }

    public boolean isNull() {
        boolean nullNumber = this.number == null;
        boolean nullOperator = this.operator == null;
        boolean nullString = this.string == null;
        boolean nullTokenArrayList = this.tokenArrayList == null;;
        return !(nullNumber || nullOperator || nullString || nullTokenArrayList);
    }

    /**
     * Takes in a single string that represents a token. Convert the string into the appropriate token.
     *
     * @param token a single string that represents a token.
     * @return a single token that is either a number, operator, string, or boolean token.
     */
    public static Token parseToken(String token) {
        Token newToken;

        for(Operator op : Operator.values()) {
            if(token.equals(op.toString())) {
                newToken = new Token(op);
                return newToken;
            }
        }

        if(token.equalsIgnoreCase("t")) {
            newToken = new Token(true);
            return newToken;
        } else if (token.equalsIgnoreCase("nil")) {
            newToken = new Token(false);
            return newToken;
        }

        try {
            newToken = new Token(Double.parseDouble(token));
        } catch (NumberFormatException e) {
            newToken = new Token(token);
        }

        return newToken;
    }

    /**
     * Takes in a single string that represents a token. Convert the string into only a string token.
     *
     * @param token a single string that represents a token.
     * @return a single string token.
     */
    public static Token parseLiteralToken(String token) {
        Token newToken;
        newToken = new Token(token);
        return newToken;
    }

    /**
     * Takes in a list of strings that represent user input. If it is valid, the parseTokenList will create
     *  a list tokens for each string in the stringList. If it is only a single string in the arraylist, then
     *  just create a single token. This will represent a stack, or an abstract syntax tree.
     *
     * @param stringList The list of strings that represent the tokens to be created!
     * @return A token, that may or may not nest other tokens, that represents the user input.
     */
    public static Token parseTokenList(ArrayList<String> stringList) {

        Token finishedTokenList;
        ArrayList<Token> tokens = new ArrayList<>();
        String token;

        if (stringList.size() == 0) {
            System.out.println("unexpected EOF");
            return new Token();
        }

        token = stringList.get(0);
        stringList.remove(0);

        if (token.equals("'")) {

            finishedTokenList = parseLiteralTokenList(stringList);
            return finishedTokenList;
        }

        if (token.equals("(")) {

            while(!stringList.get(0).equals(")")) {
                Token end = parseTokenList(stringList);

                tokens.add(end);
            }

            if(tokens.isEmpty()){
                System.out.print("NIL");
                return new Token();
            }

            stringList.remove(0);
            finishedTokenList = new Token(tokens);

            return finishedTokenList;

        } else if (token.equals(")")) {

            System.out.print("unexpected )");
            return new Token();

        } else {

            Token end = parseToken(token);

            return end;
        }
    }

    /**
     * Takes in a list of strings that represent user input. If it is valid, the parseLiteralTokenList will create
     *  a list string tokens for each string in the stringList. If it is only a single string in the arraylist, then
     *  just create a single token. This will represent a stack, or an abstract syntax tree.
     *
     * @param stringList The list of strings that represent the tokens to be created!
     * @return A token that represents the user input.
     */
    public static Token parseLiteralTokenList(ArrayList<String> stringList) {

        Token finishedTokenList;
        ArrayList<Token> tokens = new ArrayList<>();
        String token;

        if (stringList.size() == 0) {
            System.out.println("unexpected EOF");
            return new Token();
        }

        token = stringList.get(0);
        stringList.remove(0);

        if (token.equals("(")) {

            while(!stringList.get(0).equals(")")) {
                Token end = parseLiteralTokenList(stringList);
                tokens.add(end);
            }

            if(tokens.isEmpty()){
                System.out.print("NIL");
                return new Token();
            }

            stringList.remove(0);
            finishedTokenList = new Token(tokens);
            finishedTokenList.setLiteral(true);
            return finishedTokenList;

        } else if (token.equals(")")) {

            System.out.print("unexpected )");
            return new Token();

        } else {

            Token end = parseLiteralToken(token);
            return end;
        }
    }

    /**
     * This will recursively return the string that makes a token. If a token is a list of other tokens, then
     * each token in the list will print.
     *
     * @return The string that represents the Token.
     */
    public String printToken() {
        Token token = this;
        String string = "";

        if(this.number != null) {
            double num = this.number;
            Integer int_num = (int) num;

            if(num % int_num == 0) {
                string += int_num + " ";
                return string;
            }
        }

        if(token.isNumber()) {
            string = string + token.getNumber().toString() + " ";
        } else if(token.isOperator()) {
            string = string + token.getOperator().toString() + " ";
        } else if(token.isString()) {
            string = string + token.getString() + " ";
        } else if(token.isTokenArrayList()){
            string = string + "( ";
            for(Token toke : tokenArrayList) {
                string = string + toke.printToken();
            }
            string = string + ") ";
        } else if(token.isBoolean()) {
            if(token.getBool()) {
                string = string + "T ";
            } else {
                string = string + "NIL ";
            }
        }

        return string;
    }

    /**
     * Prints out the token type as a string.
     * @return a string that is the token type.
     */
    public String printTokenType() {
        Token token = this;
        String string = "";

        if(token.isNumber()) {
            string = string + "Number ";
        } else if(token.isOperator()) {
            string = string + "Operator ";
        } else if(token.isString()) {
            string = string + "String ";
        } else if(token.isTokenArrayList()){
            string = string + "( ";
            for(Token toke : tokenArrayList) {
                string = string + toke.printTokenType();
            }
            string = string + ") ";
        } else if(token.isBoolean()) {
            if(token.getBool()) {
                string = string + "T ";
            } else {
                string = string + "NIL ";
            }
        }

        return string;
    }
}
