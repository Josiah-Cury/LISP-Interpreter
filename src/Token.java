import java.util.ArrayList;
import java.util.Locale;

public class Token {
    private Double number;
    private Operator operator;
    private String string;
    private ArrayList<Token> tokenArrayList;
    private Boolean bool;


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
    }

    public Token(Boolean bool) {
        this.number = null;
        this.operator = null;
        this.string = null;
        this.tokenArrayList = null;
        this.bool = bool;
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
        return (nullNumber || nullOperator || nullString || nullTokenArrayList);
    }

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

        if (token.equals("(")) {

            while(!stringList.get(0).equals(")")) {
                tokens.add(parseTokenList(stringList));
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
}
