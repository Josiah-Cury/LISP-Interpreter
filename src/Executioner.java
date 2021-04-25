import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

/**
 * Executioner will take in an arraylist of tokens and evaluate the list. The most important method is the eval()
 * method, which will take in a hashmap. eval() will return a Token that contains the answer back to the Interpreter
 * object to print.
 */
public class Executioner {

    public Token tokens;
    public Stack<Token> stack = new Stack<>();

    public Executioner(Token givenStack) {
        this.tokens = givenStack;
    }

    /**
     * The method eval() will start by checking it the Token passed to the constructor is either a literal, an
     * arraylist, or a empty. I will list out the steps the Executioner object will handle the evaluation of a token.
     *
     * (1) If the token is a literal (a boolean or a number) then return the token without changing the token.
     * If the token is a literal string, but it is not a defined variable, then throw an error.
     *
     * (2) If the token is an arraylist of tokens, then the token must be an expression, so each item of the arraylist of tokens is
     * added to a stack.
     *
     * (3) Check if each token on the stack is another arraylist, and if so, create a new Executioner object, pass the arraylist token,
     * and run eval() on that token.
     * (The eval() method will return a token, and that arraylist token on the stack will be replaced with that answer token.
     * This method of recursion will find the deepest nested arraylist and evaluate that first and replace the arraylist token with
     * the correct answer token.)
     *
     * (4) If the Token is empty, then the evaluator returns an empty token.
     *
     * (5) Check the first member of the stack, and if it is not a pre-defined operator, then throw
     * an exception and print the error message. Otherwise, if the first item of the stack is an operator token, then
     * perform the operation on the remaining items on the stack.
     *
     * @param variables A hashmap that is taken from the Interpreter class, to ensure a global scoping.
     * @return A token that contains the answer to the evaluation.
     * @throws Exception Catches user input error and returns string of error to print.
     */
    public Token eval(HashMap<String, Token> variables) throws Exception {

        /**
         * (1) If the token is a literal (a boolean or a number) then return the token without changing the token.
         * If the token is a literal string, but it is not a defined variable, then throw an error.
         */
        if(tokens.getLiteral()) {

            if(tokens.isNumber() || tokens.isBoolean()) {
                return tokens;
            } else {
                if(variables.containsKey(tokens.getString())) {
                    return variables.get(tokens.getString());
                }
                //throw new Exception(tokens.getString()+ " is not a function name!");
                return tokens;
            }

        }

        /**
         * (2) If the token is an arraylist of tokens, then the token must be an expression, so each item of the arraylist
         * of tokens is added to a stack.
         */
        if(tokens.isTokenArrayList()) {

            for (int i = 0; i < tokens.getTokenArrayList().size(); i++) {
                //System.out.println(tokens.getTokenArrayList().get(i).printToken() + tokens.getTokenArrayList().get(i).getLiteral());
                stack.add(tokens.getTokenArrayList().get(i));
            }

        }

        /**
         * (3) Check if each token on the stack is another arraylist, and if so, create a new Executioner object,
         * pass the arraylist token, and run eval() on that token.
         *
         * (The eval() method will return a token, and that arraylist token on the stack will be replaced with that answer token.
         * This method of recursion will find the deepest nested arraylist and evaluate that first and replace the arraylist token with
         * the correct answer token.)
         */
        for (int i = 0; i < stack.size(); i++) {

            if (stack.get(i).isTokenArrayList() && !stack.get(i).getLiteral()){
                stack.set(i, new Executioner(stack.get(i)).eval(variables));
            }

        }

        //printStack();

        /**
         * (4) If the Token is empty, then the evaluator returns an empty token.
         */
        if(stack.isEmpty()){

            return new Token();

        }

        /**
         * (5) Check the first member of the stack, and if it is not a pre-defined operator, then throw
         * an exception and print the error message. Otherwise, if the first item of the stack is an operator token, then
         * perform the operation on the remaining items on the stack.
         */
        if(stack.get(0).isNumber() || stack.get(0).isString() || stack.get(0).isBoolean()) {

            String error = stack.get(0).printToken();
            throw new Exception(error + "is not a valid function name!\n");

            /**
             * This 'if' is to perform an arithmetic operator (+, -, *, /, etc.).
             */
        } else if(stack.get(0).getOperator().isArithm()) {

            ArrayList<Double> doublesList = new ArrayList<>();
            for(int i = 1; i < stack.size(); i++) {

                if(stack.get(i).getNumber() == null){
                    if(variables.containsKey(stack.get(i).getString())){
                        doublesList.add(variables.get(stack.get(i).getString()).getNumber());
                    }
                } else {
                    doublesList.add(stack.get(i).getNumber());
                }
            }

            Token token = stack.get(0).getOperator().arithEval(doublesList);
            return token;

            /**
             * This if is to perform a boolean operator (<, <=, >, =>, etc.).
             */
        } else if(stack.get(0).getOperator().isBool()) {

            ArrayList<Double> doublesList = new ArrayList<>();

            for (int i = 1; i < stack.size(); i++) {
                if(stack.get(i).getNumber() == null){
                    if(variables.containsKey(stack.get(i).getString())){
                        doublesList.add(variables.get(stack.get(i).getString()).getNumber());
                    }
                } else {
                    doublesList.add(stack.get(i).getNumber());
                }
            }

            Token token = stack.get(0).getOperator().boolEval(doublesList);
            return token;

            /**
             * This if will perform a math operator ().
             */
        } else if(stack.get(0).getOperator().isMath()) {

            ArrayList<Double> doublesList = new ArrayList<>();

            for (int i = 1; i < stack.size(); i++) {
                if(stack.get(i).getNumber() == null){
                    if(variables.containsKey(stack.get(i).getString())){
                        doublesList.add(variables.get(stack.get(i).getString()).getNumber());
                    }
                } else {
                    doublesList.add(stack.get(i).getNumber());
                }
            }

            Token token = stack.get(0).getOperator().evalMath(doublesList);

            return token;

        } else if(stack.get(0).getOperator().isConditional()) {

            ArrayList<Token> tokensList = new ArrayList<>();

            for (int i = 1; i < stack.size(); i++) {
                tokensList.add(stack.get(i));
            }

            Token token = stack.get(0).getOperator().evalIf(tokensList);

            return token;

        } else if(stack.get(0).getOperator().isLisp()) {

            if(stack.get(0).getOperator().getSymbol().equals("define")) {

                ArrayList<Token> tokensList = new ArrayList<>();

                for(int i = 1; i < stack.size(); i++) {
                    tokensList.add(stack.get(i));
                }

                Token token = stack.get(0).getOperator().evalLisp(tokensList, variables);
                return token;
            } else if(!stack.get(0).getOperator().getSymbol().equals("cons")) {

                ArrayList<Token> tokensList = new ArrayList<>();
                if(variables.containsKey(stack.get(1).getString())){
                    stack.set(1, variables.get(stack.get(1).getString()));
                }
                for(int i = 0; i < stack.get(1).getTokenArrayList().size(); i++) {
                    tokensList.add(stack.get(1).getTokenArrayList().get(i));
                }

                Token token = stack.get(0).getOperator().evalLisp(tokensList, variables);
                return token;
            } else {

                ArrayList<Token> tokensList = new ArrayList<>();
                tokensList.add(stack.get(1));
                tokensList.add(stack.get(2));
                Token token = stack.get(0).getOperator().evalLisp(tokensList, variables);
                return token;
            }
        }
        return null;
    }

    /**
     * Prints a view of the stack out to the terminal.
     */
    public void printStack() {
        System.out.println("\nThis is a stack----------------");
        for(Token token : this.stack) {
            System.out.println(token.printToken() + " : " + token.printTokenType());
        }
        System.out.println("------------------------------");
    }


}
