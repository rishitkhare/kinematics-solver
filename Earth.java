public class Earth extends KinematicEquation {

   public static final String equation = "Vf = Vi + aΔt";
   public static final boolean[] presentQuantities = {true, true, true, true, false};

   public Earth(boolean[] knownQuantities, String[] quantities) {
      super.absentQuantityIndex = 4;
      setKnownQuantities(knownQuantities);
      setQuantities(quantities);

      setLeftSide(new UnaryExpression(getQuantity(1)));
      setRightSide(new BinaryExpression(new UnaryExpression(getQuantity(0)),
              new BinaryExpression(new UnaryExpression(getQuantity(3)), new UnaryExpression(getQuantity(2)), '*'), '+'));

      checkNumberOfQuantities(numberOfKnownQuantities());
   }

   @Override
   public void doAlgebra() {
      setWork(new Steps(equation));
      if (isTimeKnown()) {
         Algebra.solveEquation(false, this.work, this.leftSide, this.rightSide, getMissingQuantityIndex());
      }
      else {
         Algebra.solveEquation(true, this.work, this.leftSide, this.rightSide, getMissingQuantityIndex());
      }

      setQuantity(getMissingQuantityIndex(), Double.toString(this.work.getNumericalAnswer())); //adds answer to array and updates knowns
   }
}