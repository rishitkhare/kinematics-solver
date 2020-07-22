public class Water extends KinematicEquation {

   public static final String equation = "ΔX = 1/2(Vf + Vi)Δt";

   public Water(boolean[] knownQuantities, String[] quantities) {
      setKnownQuantities(knownQuantities);
      setQuantities(quantities);

      setLeftSide(new UnaryExpression(getQuantity(4)));
      setRightSide(new BinaryExpression(new BinaryExpression(new BinaryExpression(new UnaryExpression(getQuantity(1)), new UnaryExpression(getQuantity(0)), '+'), new UnaryExpression("0.5"), '*'), new UnaryExpression(getQuantity(2)), '*'));

      checkNumberOfQuantities(numberOfKnownQuantities());
   }

   @Override
   public void doAlgebra() {

      setWork(new Steps(equation));

      if (isTimeKnown()) {
         Algebra.solveEquation(false, this.work, leftSide, rightSide);
      }
      else {
         Algebra.solveEquation(true, this.work, leftSide, rightSide);
      }
   }
}