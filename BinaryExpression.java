public class BinaryExpression extends Expression {

    private String value;
    private Expression operand1;
    private Expression operand2;
    private char operator;

    public BinaryExpression(Expression operand1, Expression operand2, char operator) {
        setIsKnown(operand1.getIsKnown() && operand2.getIsKnown());
        this.operand1 = operand1;
        this.operand2 = operand2;
        this.operator = operator;
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
