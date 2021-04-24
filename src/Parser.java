import java.util.ArrayList;

public class Parser {

    private String wholeString;
    private ArrayList<String> parsedString = new ArrayList<>();
    private Token tokenList;





    public Token getTokenList() { return tokenList; }

    public String getWholeString() { return wholeString; }
    public void setWholeString(String wholeString) { this.wholeString = wholeString; }

    public void setParsedString(ArrayList<String> parsedString) { this.parsedString = parsedString; }
    public ArrayList<String> getParsedString() { return parsedString; }





    public Parser() {
        this.wholeString = null;
        this.parsedString = null;
    }

    public Parser(String string) {
        this.wholeString = string;
        parseString(this.wholeString);

        this.tokenList = Token.parseTokenList((ArrayList<String>) parsedString.clone(), false);
        for(Token token: this.tokenList.getTokenArrayList()) {
            System.out.println(token.printToken() + ": " + token.getLiteral());
        }
    }





    public void parseString(String string) {

        string = string.replace("(", " ( ").replace(")", " ) ");
        string = string.replace("'", " ' ");
        String[] strings = string.trim().split("\\s+");

        for(String i : strings) {
            parsedString.add(i);
        }

    }






    public void printParsedString() {

        System.out.println(parsedString);

    }




    public boolean isValid() {

        return false;
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
