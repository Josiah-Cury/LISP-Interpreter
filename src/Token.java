import java.util.ArrayList;

public class Token {
    private Double number;
    private Operator operator;
    private String string;
    private ArrayList<Token> tokenArrayList;

    public Double getNumber() {
        return number;
    }
    public Operator getOperator() {
        return operator;
    }

    public String getString() { return string; }
    public void setString(String string) { this.string = string; }

    public Token(double number) {
        this.number = number;
        this.operator = null;
        this.string = null;
        this.tokenArrayList = null;
    }

    public Token(Operator operator) {
        this.number = null;
        this.operator = operator;
        this.string = null;
        this.tokenArrayList = null;
    }

    public Token(ArrayList<Token> tokenArray) {
        this.number = null;
        this.operator = null;
        this.string = null;
        this.tokenArrayList = tokenArray;
    }

    public Token(String string) {
        this.number = null;
        this.operator = null;
        this.string = string;
        this.tokenArrayList = null;
    }

    public boolean isOperator() {
        if(this.operator != null) { return true; }

        return false;
    }

    public boolean isNumber() {
        if(this.number != null) { return true; }

        return false;
    }

    public boolean isString() {
        if(this.string != null) { return true; }

        return false;
    }

    public boolean isTokenArrayList() {
        if(this.tokenArrayList != null) { return true; }

        return false;
    }

    public static Token parseToken(String token) {
        Token newToken;

        for(Operator op : Operator.values()) {
            if(token.equals(op.toString())) {
                newToken = new Token(op);
                return newToken;
            }
        }

        try {
            newToken = new Token(Double.parseDouble(token));
        } catch (NumberFormatException e) {
            newToken = new Token(token);
        }

        return newToken;
    }

    public static Token parseTokenList(ArrayList<String> stringList) {

        Token finishedTokenList;
        ArrayList<Token> tokens = new ArrayList<>();
        String token;

        if (stringList.size() == 0) {
            System.out.println("unexpected EOF");
            return null;
        }

        token = stringList.get(0);
        stringList.remove(0);

        if (token.equals("(")) {

            while(!stringList.get(0).equals(")")) {
                tokens.add(parseTokenList(stringList));
            }

            stringList.remove(0);
            finishedTokenList = new Token(tokens);
            return finishedTokenList;

        } else if (token.equals(")")) {

            System.out.println("unexpected )");
            return null;

        } else {

            return parseToken(token);
        }
    }

    public String printToken(Token token) {
        String string = null;

        if(token.isNumber()) {
            string = string + token.number.toString();
        } else if(token.isOperator()) {
            string = string + token.getOperator().toString();
        } else if(token.isString()) {
            string = string + token.getString();
        } else {
            for(Token toke : tokenArrayList) {
                printToken(toke);
            }
        }

        return string;
    }
}
