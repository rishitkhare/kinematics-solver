// Water: ΔX = 1/2(Vf + Vi)Δt
import java.util.*;

public class Water extends KinematicEquation {

   public static final String equation = "ΔX = 0.5 * ( Vf + Vi ) * Δt";
   public static final boolean[] quantityRange = {true, true, true, false, true};

   public Water() {
      setQuantity(4, askForQuantity("displacement", 4));
      setQuantity(1, askForQuantity("final velocity", 1));
      setQuantity(0, askForQuantity("initial velocity", 0));
      setQuantity(2, askForQuantity("time", 2));
      System.out.println();
      if (numberOfKnownQuantities() == 4) {
         Algebra.verifyEquality(getNumericalQuantity(4), 0.5 * (getNumericalQuantity(0) + getNumericalQuantity(1)) * getNumericalQuantity(2));
      }
      if (numberOfKnownQuantities() < 3) {
         // If given all 4 quantities, will verify if correct
         throw new IllegalArgumentException ("ERROR: Not enough quantities");
      }

      Expression leftSide = new UnaryExpression(getQuantity(4));
      Expression rightSide = new BinaryExpression(new BinaryExpression(new BinaryExpression(new UnaryExpression(getQuantity(1)), new UnaryExpression(getQuantity(0)), '+'), new UnaryExpression("0.5"), '*'), new UnaryExpression(getQuantity(2)), '*');

      setWork(new Steps(equation));
      if (isTimeKnown()) {
         Algebra.solveEquation(false, this.work, leftSide, rightSide);
      }
      else {
         Algebra.solveEquation(true, this.work, leftSide, rightSide);
      }

   }
   public Water(boolean[] knownQuantities, String[] quantities) {
      setKnownQuantities(knownQuantities);
      setQuantities(quantities);
      Expression leftSide = new UnaryExpression(getQuantity(4));
      Expression rightSide = new BinaryExpression(new BinaryExpression(new BinaryExpression(new UnaryExpression(getQuantity(1)), new UnaryExpression(getQuantity(0)), '+'), new UnaryExpression("0.5"), '*'), new UnaryExpression(getQuantity(2)), '*');

      setWork(new Steps(equation));
      if (isTimeKnown()) {
         Algebra.solveEquation(false, this.work, leftSide, rightSide);
      }
      else {
         Algebra.solveEquation(true, this.work, leftSide, rightSide);
      }
   }
}