public enum Operator {
    LPAREN(3, "("),
    RPAREN(3, ")"),
    EXPONENT(2, "^"),
    MULTIPLY(1, "*"),
    DIVIDE(1, "/"),
    MODULO(1, "%"),
    ADD(0, "+"),
    SUBTRACT(0, "-");

    private int precedence;
    private String symbol;

    Operator(int precedence, String symbol) {
        this.precedence = precedence;
        this.symbol = symbol;
    }

    public int getPrecedence() {
        return this.precedence;
    }

    double eval(double a, double b) {
        switch (this) {
            case ADD:
                return a + b;
            case SUBTRACT:
                return a - b;
            case MULTIPLY:
                return a * b;
            case DIVIDE:
                return a / b;
            case EXPONENT:
                return Math.pow(a, b);
            case MODULO:
                return a % b;
            case LPAREN:
            case RPAREN:
                return Double.NaN;
        }
        return 0;
    }

    @Override
    public String toString() {
        return symbol;
    }
}
