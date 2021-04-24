import java.util.ArrayList;

public enum Operator {
    LPAREN("arithm", "("),
    RPAREN("arithm", ")"),
    EXPONENT("arithm", "^"),
    MULTIPLY("arithm", "*"),
    DIVIDE("arithm", "/"),
    MODULO("arithm", "%"),
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
    CDR("lisp", "cdr");

    private String type;
    private String symbol;

    Operator(String type, String symbol) {
        this.type = type;
        this.symbol = symbol;
    }

    public String getType() {
        return this.type;
    }

    public boolean isArithm() { return (this.type.equals("arithm")); }
    public boolean isBool() { return (this.type.equals("boolean")); }
    public boolean isMath() { return (this.type.equals("math")); }
    public boolean isConditional() { return (this.type.equals("conditional")); }
    public boolean isLisp() { return (this.type.equals("lisp")); }

    public Token arithEval(ArrayList<Double> doubles) {

        //check is doubles is empty
        if(doubles.isEmpty()){
            System.out.print("Arithmetic Operator needs at least one argument!");
            return new Token();
        }

        for(Double doub: doubles) {
            if(doub == null) {
                return new Token();
            }
        }

        Double answer = doubles.get(0);

        switch (this) {
            case ADD:
                for(int i = 1; i < doubles.size(); i++) {
                    answer += doubles.get(i);
                }
                //System.out.println("\nThis is the arraylist of doubles_____________\n" + doubles.toString() + "\n_______________________________\n");
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
                    answer /= doubles.get(i);
                }
                return new Token(answer);
        }

        return null;
    }

    public Token boolEval(ArrayList<Double> doubles) {

        //check is doubles is empty
        if(doubles.isEmpty()){
            System.out.print("Boolean Operator needs at least one argument!");
            return new Token();
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
                //System.out.println("\nThis is the arraylist of doubles_____________\n" + doubles.toString() + "\n_______________________________\n");
                return new Token(true);

            case LESSEQ:
                for(int i = 1; i < doubles.size(); i++) {
                    if(answer > doubles.get(i)) {
                        return new Token(false);
                    }
                }
                //System.out.println("\nThis is the arraylist of doubles_____________\n" + doubles.toString() + "\n_______________________________\n");
                return new Token(true);

            case GREATER:
                for(int i = 1; i < doubles.size(); i++) {
                    if(answer <= doubles.get(i)) {
                        return new Token(false);
                    }
                }
                //System.out.println("\nThis is the arraylist of doubles_____________\n" + doubles.toString() + "\n_______________________________\n");
                return new Token(true);

            case GREATEREQ:
                for(int i = 1; i < doubles.size(); i++) {
                    if(answer < doubles.get(i)) {
                        return new Token(false);
                    }
                }
                //System.out.println("\nThis is the arraylist of doubles_____________\n" + doubles.toString() + "\n_______________________________\n");
                return new Token(true);

            case EQ:
                for(int i = 1; i < doubles.size(); i++) {
                    if(!answer.equals(doubles.get(i))) {
                        return new Token(false);
                    }
                }
                //System.out.println("\nThis is the arraylist of doubles_____________\n" + doubles.toString() + "\n_______________________________\n");
                return new Token(true);

            case NOTEQ:
                for(int i = 1; i < doubles.size(); i++) {
                    if(answer.equals(doubles.get(i))) {
                        return new Token(false);
                    }
                }
                //System.out.println("\nThis is the arraylist of doubles_____________\n" + doubles.toString() + "\n_______________________________\n");
                return new Token(true);

        }

        return null;
    }

    public Token evalMath(ArrayList<Double> doubles) {
        //check is doubles is empty
        if(doubles.isEmpty()){
            System.out.print("Math Operator needs at least one argument!");
            return new Token();
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
                    System.out.println("SQRT should only take in one argument!");
                    return new Token();
                }
                answer = Math.sqrt(answer);
                //System.out.println("\nThis is the arraylist of doubles_____________\n" + doubles.toString() + "\n_______________________________\n");
                return new Token(answer);

            case POW:
                if(doubles.size() != 2) {
                    System.out.println("POW should take in two arugments!");
                    return new Token();
                }
                answer = Math.pow(answer, doubles.get(1));
                return new Token(answer);
        }

        return null;
    }

    public Token evalIf(ArrayList<Token> tokens) {

        switch(this) {
            case IF:
                if(tokens.size() == 3) {
                    if(tokens.get(0).getBool()) {
                        return tokens.get(1);
                    } else {
                        return tokens.get(2);
                    }
                }

                System.out.println("IF operator should take in only 3 arguments.");
                return new Token();

            case AND:
                if(tokens.size() == 2) {
                    if(tokens.get(0).getBool() && tokens.get(1).getBool()) {
                        return new Token(true);
                    } else {
                        return new Token(false);
                    }
                }

                System.out.println("AND operator should take in only 2 arguments.");
                return new Token();
            case OR:
                if(tokens.size() == 2) {
                    if(tokens.get(0).getBool() || tokens.get(1).getBool()) {
                        return new Token(true);
                    } else {
                        return new Token(false);
                    }
                }

                System.out.println("OR operator should take in only 2 arguments.");
                return new Token();
            case NOT:
                if(tokens.size() == 1) {
                    if(!tokens.get(0).getBool()) {
                        return new Token(true);
                    } else {
                        return new Token(false);
                    }
                }

                System.out.println("NOT operator should take in only 1 argument.");
                return new Token();
        }
        return null;

    }

    public Token evalLisp(ArrayList<Token> tokens) {

        switch(this) {
            case CAR:
                System.out.println("You chose CAR! : " + tokens.get(0).printToken());
                return tokens.get(0);
            case CDR:
                ArrayList<Token> cdrList = new ArrayList<>();
                for(int i = 1; i < tokens.size(); i++) {
                    cdrList.add(tokens.get(i));
                }

                return new Token(cdrList)   ;

        }
        return null;

    }

    @Override
    public String toString() {
        return symbol;
    }
}
