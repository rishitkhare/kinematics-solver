public class Shadow extends KinematicEquation {

   public static final String equation = "ΔX = VfΔt - 1/2aΔt^2";

   public Shadow(boolean[] knownQuantities, String[] quantities) {
      setKnownQuantities(knownQuantities);
      setQuantities(quantities);

      setLeftSide(new UnaryExpression(getQuantity(4)));
      setRightSide(new BinaryExpression(new BinaryExpression(new UnaryExpression(getQuantity(1)), new UnaryExpression(getQuantity(2)), '*'), new BinaryExpression(new BinaryExpression("0.5", new UnaryExpression(getQuantity(3)), '*'), new BinaryExpression(new UnaryExpression(getQuantity(2)), "2", '^'), '*'), '-'));

      checkNumberOfQuantities(numberOfKnownQuantities());
   }

   public void doAlgebra() {
      Expression leftSide = new UnaryExpression(getQuantity(4));
      Expression rightSide = new BinaryExpression(new BinaryExpression(new UnaryExpression(getQuantity(1)), new UnaryExpression(getQuantity(2)), '*'), new BinaryExpression(new BinaryExpression("0.5", new UnaryExpression(getQuantity(3)), '*'), new BinaryExpression(new UnaryExpression(getQuantity(2)), "2", '^'), '*'), '-');
      setWork(new Steps(equation));
      if (!isTimeKnown()) {
         Algebra.getPositiveQuadraticRoot(work, -0.5 * getNumericalQuantity(3), getNumericalQuantity(1), -1 * getNumericalQuantity(4));
      }
      else {
         Algebra.solveEquation(false, this.work, leftSide, rightSide);
      }
   }
}