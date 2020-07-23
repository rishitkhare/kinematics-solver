public class Water extends KinematicEquation {

   public static final String equation = "ΔX = 1/2(Vf + Vi)Δt";
   public static final boolean[] presentQuantities = {true, true, true, false, true};

   public Water(boolean[] knownQuantities, String[] quantities) {
      super.absentQuantityIndex = 3;
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
         Algebra.solveEquation(false, this.work, leftSide, rightSide, getMissingQuantityIndex());
      }
      else {
         Algebra.solveEquation(true, this.work, leftSide, rightSide, getMissingQuantityIndex());
      }
      setQuantity(getMissingQuantityIndex(), Double.toString(this.work.getNumericalAnswer())); //adds answer to array and updates knowns
   }
}