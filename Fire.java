// Fire: Vf^2 = Vi^2 + 2aΔX

public class Fire extends KinematicEquation {

   public static final String equation = "Vf^2 = Vi^2 + 2aΔX";

   public Fire(boolean[] knownQuantities, String[] quantities) {
      setKnownQuantities(knownQuantities);
      setQuantities(quantities);

      setLeftSide(new BinaryExpression(new UnaryExpression(getQuantity(1)), new UnaryExpression("2"), '^'));
      setRightSide(new BinaryExpression(new BinaryExpression(new UnaryExpression(getQuantity(0)), new UnaryExpression("2"), '^'), new BinaryExpression(new UnaryExpression(getQuantity(3)), new BinaryExpression(new UnaryExpression(getQuantity(4)), new UnaryExpression("2"), '*'), '*'), '+'));

      checkNumberOfQuantities(numberOfKnownQuantities());
   }

   @Override
   public void doAlgebra() {

      setWork(new Steps(equation));
      Algebra.solveEquation(false, this.work, leftSide, rightSide);
   }
}