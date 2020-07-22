public class Air extends KinematicEquation {

   public static final String equation = "ΔX = ViΔt + 1/2aΔt^2";
   public static final boolean[] presentQuantities = {true, false, true, true, true};

   public Air(boolean[] knownQuantities, String[] quantities) {
      super.absentQuantityIndex = 1;
      setKnownQuantities(knownQuantities);
      super.setQuantities(quantities);

      setLeftSide(new UnaryExpression(getQuantity(4)));
      setRightSide(new BinaryExpression(new BinaryExpression(new UnaryExpression(getQuantity(0)), new UnaryExpression(getQuantity(2)), '*'), new BinaryExpression(new BinaryExpression("0.5", new UnaryExpression(getQuantity(3)), '*'), new BinaryExpression(new UnaryExpression(getQuantity(2)), "2", '^'), '*'), '+'));

      checkNumberOfQuantities(numberOfKnownQuantities());
   }

   @Override
   public void doAlgebra() {

      setWork(new Steps(equation));
      if (!isTimeKnown()) {
         Algebra.getPositiveQuadraticRoot(work, 0.5 * getNumericalQuantity(3), getNumericalQuantity(0), -1 * getNumericalQuantity(4));
      }
      else {
         Algebra.solveEquation(false, work, leftSide, rightSide, getMissingQuantityIndex());
      }

      setQuantity(getMissingQuantityIndex(), this.work.getNumericalAnswer()); //adds answer to array and updates knowns
   }
}