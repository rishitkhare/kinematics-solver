//represents leaf of binary tree

public class UnaryExpression extends Expression {

    private String value;

    public UnaryExpression(String value) {
        this.value = value;
        if (Algebra.isNumber(value)) {
            super.setIsKnown(true);
        }
    }

    public String toString() {
        return value;
    }

    public double evaluate() {
        // Only runs when isKnown
        return Double.parseDouble(value);
    }
}