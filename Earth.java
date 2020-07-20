// Earth: Vf = Vi + aΔt
import java.util.*;

public class Earth extends KinematicEquation {

   public static final String equation = "Vf = Vi + aΔt";
   public static final boolean[] quantityRange = {true, true, true, true, false};

   public Earth() {
      setQuantity(1, askForQuantity("final velocity", 1));
      setQuantity(0, askForQuantity("initial velocity", 0));
      setQuantity(3, askForQuantity("acceleration", 3));
      setQuantity(2, askForQuantity("time", 2));
      System.out.println();
      if (numberOfKnownQuantities() == 4) {
         Algebra.verifyEquality(getNumericalQuantity(1), getNumericalQuantity(0) + (getNumericalQuantity(3) * getNumericalQuantity(2)));
      }
      if (numberOfKnownQuantities() < 3) {
         // If given all 4 quantities, will verify if correct
         throw new IllegalArgumentException("ERROR: Not enough quantities");
      }


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

   public Earth(boolean[] knownQuantities, String[] quantities) {
      setKnownQuantities(knownQuantities);
      setQuantities(quantities);
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