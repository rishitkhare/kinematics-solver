public class UnaryExpression extends Expression {

    private String value;

    public UnaryExpression(String value) {
        this.value = value;
        if (Algebra.isNumber(value)) {
            super.setIsKnown(true);
        }
    }

    public double evaluate() {
        // Only runs when isKnown
        return Double.parseDouble(value);
    }
}