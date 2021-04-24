import java.util.ArrayList;
import java.util.Stack;

public class Executioner {

    public Token tokens;
    public Stack<Token> stack = new Stack<>();

    public Executioner(Token givenStack) {
        this.tokens = givenStack;
    }

    public Token eval() {
        /*
            Take in an arraylist of tokens.
            Add each token to stack.
            (Loop) Start at top of the stack, if it is an expression run evaluation on it
                (Decide if the which operation it is.)
                (Check list to make sure they are of the correct type.)
                (Perform operation on whole list.)
            Should end up without expressions.
            Work out to the outside and keep working until end of the list.
            Finally, evaluate the outermost ().
         */
        //System.out.println(tokens.printToken());
        if(tokens.getLiteral()) {
            return tokens;
        }

        if(tokens.isTokenArrayList()) {
            for (int i = 0; i < tokens.getTokenArrayList().size(); i++) {
                //System.out.println(tokens.getTokenArrayList().get(i).printToken() + tokens.getTokenArrayList().get(i).getLiteral());
                stack.add(tokens.getTokenArrayList().get(i));
            }
        }

        for (int i = 0; i < stack.size(); i++) {
            if (stack.get(i).isTokenArrayList() && !stack.get(i).getLiteral()){
                //System.out.println("making new evaluator");
                stack.set(i, new Executioner(stack.get(i)).eval());
            }
        }

        //printStack();

        if(stack.isEmpty()){
            return new Token();
        }

        //System.out.println(stack.get(0).printToken() + " = this is the first token!!");

        if(stack.get(0).isNumber() || stack.get(0).isString() || stack.get(0).isBoolean()) {
            String error = stack.get(0).printToken();
            System.out.print(error + "is not a valid function name!\n");
            return new Token();
        } else if(stack.get(0).getOperator().isArithm()) {
            ArrayList<Double> doublesList = new ArrayList<>();
            for(int i = 1; i < stack.size(); i++) {
                /*if(!stack.get(i).isNumber()) {                                        todo: add non-recursive error checking!
                    System.out.print(stack.get(i).printToken() + "is not a number!");
                    return new Token();
                }*/
                doublesList.add(stack.get(i).getNumber());
            }
            Token token = stack.get(0).getOperator().arithEval(doublesList);
            //System.out.println(token.printToken());
            return token;
        } else if(stack.get(0).getOperator().isBool()) {
            ArrayList<Double> doublesList = new ArrayList<>();
            for (int i = 1; i < stack.size(); i++) {
                doublesList.add(stack.get(i).getNumber());
            }
            Token token = stack.get(0).getOperator().boolEval(doublesList);
            //System.out.println(token.printToken());
            return token;
        } else if(stack.get(0).getOperator().isMath()) {
            ArrayList<Double> doublesList = new ArrayList<>();
            for (int i = 1; i < stack.size(); i++) {
                doublesList.add(stack.get(i).getNumber());
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
            ArrayList<Token> tokensList = new ArrayList<>();
            for(int i = 0; i < stack.get(1).getTokenArrayList().size(); i++) {
                tokensList.add(stack.get(1).getTokenArrayList().get(i));
            }
            Token token = stack.get(0).getOperator().evalLisp(tokensList);
            return token;
        }
        return null;
    }

    public String defineVar() {

        return "";
    }

    public void printStack() {
        System.out.println("\nThis is a stack----------------");
        for(Token token : this.stack) {
            System.out.println(token.printToken() + " : " + token.printTokenType());
        }
        System.out.println("------------------------------");
    }

    public int arithmetic() {

        return 0;
    }

    public String stringOperation() {
        return "";
    }

    public boolean isBalanced() {
        return false;
    }

}
