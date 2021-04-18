import java.util.ArrayList;

public class Token {
    private Double number;
    private Operator operator;
    private String string;
    private ArrayList<Token> tokenArrayList;


    public ArrayList<Token> getTokenArrayList() { return tokenArrayList; }
    public void setTokenArrayList(ArrayList<Token> tokenArrayList) { this.tokenArrayList = tokenArrayList; }

    public Double getNumber() {
        return number;
    }
    public void setNumber(Double number) { this.number = number; }

    public Operator getOperator() {
        return operator;
    }
    public void setOperator(Operator operator) { this.operator = operator; }

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

    public boolean isOperator() { return this.operator != null; }
    public boolean isNumber() { return this.number != null; }
    public boolean isString() { return this.string != null; }
    public boolean isTokenArrayList() { return this.tokenArrayList != null; }

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

    public String printToken() {
        Token token = this;
        String string = "";

        if(token.isNumber()) {
            string = string + token.getNumber().toString() + " ";
        } else if(token.isOperator()) {
            string = string + token.getOperator().toString() + " ";
        } else if(token.isString()) {
            string = string + token.getString() + " ";
        } else {
            string = string + "[ ";
            for(Token toke : tokenArrayList) {
                string = string + toke.printToken();
            }
            string = string + "] ";
        }

        return string;
    }
}
