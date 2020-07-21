public class Earth extends KinematicEquation {

   public static final String equation = "Vf = Vi + aΔt";

   public Earth(boolean[] knownQuantities, String[] quantities) {
      setKnownQuantities(knownQuantities);
      setQuantities(quantities);
      checkNumberOfQuantities(numberOfKnownQuantities());
   }

   @Override
   public void doAlgebra() {
      Expression leftSide = new UnaryExpression(getQuantity(1));
      Expression rightSide = new BinaryExpression(new UnaryExpression(getQuantity(0)),
              new BinaryExpression(new UnaryExpression(getQuantity(3)), new UnaryExpression(getQuantity(2)), '*'), '+');

      setWork(new Steps(equation));
      if (isTimeKnown()) {
         Algebra.solveEquation(false, this.work, leftSide, rightSide);
      }
      else {
         Algebra.solveEquation(true, this.work, leftSide, rightSide);
      }
   }
}