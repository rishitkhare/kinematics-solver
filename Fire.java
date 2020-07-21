// Fire: Vf^2 = Vi^2 + 2aΔX

public class Fire extends KinematicEquation {

   public static final String equation = "Vf^2 = Vi^2 + 2aΔX";

   public Fire(boolean[] knownQuantities, String[] quantities) {
      setKnownQuantities(knownQuantities);
      setQuantities(quantities);
      checkNumberOfQuantities(numberOfKnownQuantities());
   }

   @Override
   public void doAlgebra() {
      Expression leftSide = new BinaryExpression(new UnaryExpression(getQuantity(1)), new UnaryExpression("2"), '^');
      Expression rightSide = new BinaryExpression(new BinaryExpression(new UnaryExpression(getQuantity(0)), new UnaryExpression("2"), '^'), new BinaryExpression(new UnaryExpression(getQuantity(3)), new BinaryExpression(new UnaryExpression(getQuantity(4)), new UnaryExpression("2"), '*'), '*'), '+');
      setWork(new Steps(equation));
      Algebra.solveEquation(false, this.work, leftSide, rightSide);
   }
}