import java.util.ArrayList;

public enum Operator {
    LPAREN("arithm", "("),
    RPAREN("arithm", ")"),
    EXPONENT("arithm", "^"),
    MULTIPLY("arithm", "*"),
    DIVIDE("arithm", "/"),
    MODULO("arithm", "%"),
    ADD("arithm", "+"),
    SUBTRACT("arithm", "-");

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

    @Override
    public String toString() {
        return symbol;
    }
}
