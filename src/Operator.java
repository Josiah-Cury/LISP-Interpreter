import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 */
public enum Operator {
    MULTIPLY("arithm", "*"),
    DIVIDE("arithm", "/"),
    ADD("arithm", "+"),
    SUBTRACT("arithm", "-"),

    LESS("boolean", "<"),
    LESSEQ("boolean", "<="),
    GREATER("boolean", ">"),
    GREATEREQ("boolean", ">="),
    EQ("boolean", "="),
    NOTEQ("boolean", "!="),

    SQRT("math", "sqrt"),
    POW("math", "pow"),

    IF("conditional", "if"),
    AND("conditional", "and"),
    OR("conditional", "or"),
    NOT("conditional", "not"),

    CAR("lisp", "car"),
    CDR("lisp", "cdr"),
    CONS("lisp", "cons"),
    DEFINE("lisp", "define"),
    SET("lisp", "set!"),
    DEFUN("lisp", "defun");

    private String type;
    private String symbol;

    Operator(String type, String symbol) {
        this.type = type;
        this.symbol = symbol;
    }

    public String getType() {
        return this.type;
    }
    public String getSymbol() {return  this.symbol; }

    public boolean isArithm() { return (this.type.equals("arithm")); }
    public boolean isBool() { return (this.type.equals("boolean")); }
    public boolean isMath() { return (this.type.equals("math")); }
    public boolean isConditional() { return (this.type.equals("conditional")); }
    public boolean isLisp() { return (this.type.equals("lisp")); }

    /**
     *
     * @param doubles
     * @return
     * @throws Exception
     */
    public Token arithEval(ArrayList<Double> doubles) throws Exception {

        //check is doubles are empty
        if(doubles.isEmpty()){
            throw new Exception("Arithmetic Operator needs at least one argument!");
        }

        for(Double doub: doubles) {
            if(doub == null) {
                throw new Exception("One of the arguments is not a number!");
            }
        }

        Double answer = doubles.get(0);

        switch (this) {
            case ADD:
                for(int i = 1; i < doubles.size(); i++) {
                    answer += doubles.get(i);
                }
                return new Token(answer);

            case SUBTRACT:
                for(int i = 1; i < doubles.size(); i++) {
                    answer -= doubles.get(i);
                }
                return new Token(answer);

            case MULTIPLY:
                for(int i = 1; i < doubles.size(); i++) {
                    answer *= doubles.get(i);
                }
                return new Token(answer);

            case DIVIDE:
                for(int i = 1; i < doubles.size(); i++) {
                    if(doubles.get(i) == 0){
                        throw new Exception("Error: divide by zero");
                    }
                    answer /= doubles.get(i);
                }
                return new Token(answer);
        }

        return null;
    }

    /**
     *
     * @param doubles
     * @return
     * @throws Exception
     */
    public Token boolEval(ArrayList<Double> doubles) throws Exception {

        //check is doubles are empty
        if(doubles.isEmpty()){
            throw new Exception("Boolean Operator needs at least one argument!");
        }

        for(Double doub: doubles) {
            if(doub == null) {
                return new Token();
            }
        }

        Double answer = doubles.get(0);

        switch (this) {
            case LESS:
                for(int i = 1; i < doubles.size(); i++) {
                    if(answer >= doubles.get(i)) {
                        return new Token(false);
                    }
                }
                return new Token(true);

            case LESSEQ:
                for(int i = 1; i < doubles.size(); i++) {
                    if(answer > doubles.get(i)) {
                        return new Token(false);
                    }
                }
                return new Token(true);

            case GREATER:
                for(int i = 1; i < doubles.size(); i++) {
                    if(answer <= doubles.get(i)) {
                        return new Token(false);
                    }
                }
                return new Token(true);

            case GREATEREQ:
                for(int i = 1; i < doubles.size(); i++) {
                    if(answer < doubles.get(i)) {
                        return new Token(false);
                    }
                }
                return new Token(true);

            case EQ:
                for(int i = 1; i < doubles.size(); i++) {
                    if(!answer.equals(doubles.get(i))) {
                        return new Token(false);
                    }
                }
                return new Token(true);

            case NOTEQ:
                for(int i = 1; i < doubles.size(); i++) {
                    if(answer.equals(doubles.get(i))) {
                        return new Token(false);
                    }
                }
                return new Token(true);

        }

        return null;
    }

    /**
     *
     * @param doubles
     * @return
     * @throws Exception
     */
    public Token evalMath(ArrayList<Double> doubles) throws Exception {

        //check is doubles is empty
        if(doubles.isEmpty()){
            throw new Exception("Math Operator needs at least one argument!");
        }

        for(Double doub: doubles) {
            if(doub == null) {
                return new Token();
            }
        }

        Double answer = doubles.get(0);

        switch(this) {
            case SQRT:
                if(doubles.size() != 1) {
                    throw new Exception("SQRT should only take in one argument!");
                }
                answer = Math.sqrt(answer);
                return new Token(answer);

            case POW:
                if(doubles.size() != 2) {
                    throw new Exception("POW should take in two arugments!");
                }
                answer = Math.pow(answer, doubles.get(1));
                return new Token(answer);
        }

        return null;
    }

    /**
     * @param tokens
     * @return
     * @throws Exception
     */
    public Token evalIf(ArrayList<Token> tokens) throws Exception {

        switch(this) {
            case IF:
                if(!tokens.get(0).isBoolean()) {
                    throw new Exception("The first argument should be a boolean!!");
                }
                if(tokens.size() == 3) {
                    if(tokens.get(0).getBool()) {
                        return tokens.get(1);
                    } else {
                        return tokens.get(2);
                    }
                }

                throw new Exception("IF operator should take in only 3 arguments.");

            case AND:
                if(tokens.size() == 2) {
                    if(tokens.get(0).getBool() && tokens.get(1).getBool()) {
                        return new Token(true);
                    } else {
                        return new Token(false);
                    }
                }

                throw new Exception("AND operator should take in only 2 arguments.");

            case OR:
                if(tokens.size() == 2) {
                    if(tokens.get(0).getBool() || tokens.get(1).getBool()) {
                        return new Token(true);
                    } else {
                        return new Token(false);
                    }
                }

                throw new Exception("OR operator should take in only 2 arguments.");
            case NOT:
                if(tokens.size() == 1) {
                    if(!tokens.get(0).getBool()) {
                        return new Token(true);
                    } else {
                        return new Token(false);
                    }
                }

                throw new Exception("NOT operator should take in only 1 argument.");
        }
        return null;

    }

    /**
     * @param tokens
     * @param variables
     * @return
     * @throws Exception
     */
    public Token evalLisp(ArrayList<Token> tokens, HashMap<String, Token> variables) throws Exception {
        switch(this) {
            case CAR:
                return tokens.get(0);
            case CDR:
                ArrayList<Token> cdrList = new ArrayList<>();
                for(int i = 1; i < tokens.size(); i++) {
                    cdrList.add(tokens.get(i));
                }

                return new Token(cdrList)   ;
            case CONS:
                ArrayList<Token> consList = new ArrayList<>();
                consList.add(tokens.get(0));

                for(int i = 0; i < tokens.get(1).getTokenArrayList().size(); i++) {
                    consList.add(tokens.get(1).getTokenArrayList().get(i));
                }
                return new Token(consList);

            case DEFINE:
                if(tokens.size() == 2) {
                    if(tokens.get(0).isString()){
                        if(!tokens.get(1).isNull()){
                            variables.put(tokens.get(0).getString(), tokens.get(1));
                            return tokens.get(1);
                        }
                    }
                }
                throw new Exception("Define should take in only two arguments.");

        }
        return null;

    }

    @Override
    public String toString() {
        return symbol;
    }
}
