public abstract class Expression {

    private boolean isKnown;

    public void setIsKnown(boolean isKnown) {
        this.isKnown = isKnown;
    }

    public boolean getIsKnown() {
        return isKnown;
    }

    public abstract double evaluate();
}