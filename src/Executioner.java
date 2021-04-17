import java.lang.reflect.Array;
import java.util.ArrayList;

public class Executioner {

    public Token tokens;

    public Executioner(Token givenStack) {
        this.tokens = givenStack;
    }

    public void eval() {
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
    }

    public String defineVar() {

        return "";
    }

    public String variablePrint() {
        return "";
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
