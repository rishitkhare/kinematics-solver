public class BinaryExpression extends Expression {

    private Expression operand1;
    private Expression operand2;
    private char operator;

    public BinaryExpression(Expression operand1, Expression operand2, char operator) {
        setIsKnown(operand1.getIsKnown() && operand2.getIsKnown());
        this.operand1 = operand1;
        this.operand2 = operand2;
        this.operator = operator;
    }

    public BinaryExpression(Expression operand1, String operand2, char operator) {
        // Assuming that operand2 is a valid number
        this(operand1, new UnaryExpression(operand2), operator);
    }

    public BinaryExpression(String operand1, Expression operand2, char operator) {
        // Assuming that operand1 is a valid number
        this(new UnaryExpression(operand1), operand2, operator);

    }

    public String toString() {
        if (operand1.getIsKnown() && operand2.getIsKnown()) {
            return Double.toString(evaluate());
        }
        else if (operand1.getIsKnown()) {
            return Double.toString(operand1.evaluate()) + " " + operator + " " + operand2.toString();
        }
        else if (operand2.getIsKnown()) {
            return operand1.toString() + " " + operator + " " + Double.toString(operand2.evaluate());
        }
        else {
            return ("(" + operand1.toString() + " " + operator + " " + operand2.toString() + ")");
        }
    }

    public Expression getOperand1() {
        return operand1;
    }

    public Expression getOperand2() {
        return operand2;
    }

    public char getOperator() {
        return operator;
    }

    public double evaluate() {
        // Only runs when isKnown
        if (operator == '^') {
            return Math.pow(operand1.evaluate(), operand2.evaluate());
        }
        else if (operator == '*') {
            return operand1.evaluate() * operand2.evaluate();
        }
        else if (operator == '/') {
            return operand1.evaluate() / operand2.evaluate();
        }
        else if (operator == '+') {
            return operand1.evaluate() + operand2.evaluate();
        }
        else if (operator == '-') {
            return operand1.evaluate() - operand2.evaluate();
        }
        else {
            throw new IllegalArgumentException("ERROR: Invalid operator" + operator);
        }
    }
}
