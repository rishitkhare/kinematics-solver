// Fire: Vf^2 = Vi^2 + 2aΔX
import java.util.*;

public class Fire extends KinematicEquation {

   public static final String equation = "Vf ^ 2 = Vi ^ 2 + 2 * a * ΔX";
   public static final boolean[] quantityRange = {true, true, false, true, true};

   public Fire() {
      setQuantity(1, askForQuantity("final velocity", 1));
      setQuantity(0, askForQuantity("initial velocity", 0));
      setQuantity(3, askForQuantity("acceleration", 3));
      setQuantity(4, askForQuantity("displacement", 4));
      System.out.println();
      if (numberOfKnownQuantities() == 4) {
         Algebra.verifyEquality(Math.pow(getNumericalQuantity(1), 2), Math.pow(getNumericalQuantity(0), 2) + 2 * getNumericalQuantity(3) * getNumericalQuantity(4));
      }
      if (numberOfKnownQuantities() < 3) {
         // If given all 4 quantities, will verify if correct
         throw new IllegalArgumentException ("ERROR: Not enough quantities");
      }

      Expression leftSide = new BinaryExpression(new UnaryExpression(getQuantity(1)), new UnaryExpression("2"), '^');

      Expression rightSide = new BinaryExpression(new BinaryExpression(new UnaryExpression(getQuantity(0)), new UnaryExpression("2"), '^'), new BinaryExpression(new UnaryExpression(getQuantity(3)), new BinaryExpression(new UnaryExpression(getQuantity(4)), new UnaryExpression("2"), '*'), '*'), '+');

      setWork(new Steps(equation));
      Algebra.solveEquation(false, this.work, leftSide, rightSide);
   }
   public Fire(boolean[] knownQuantities, String[] quantities) {
      setKnownQuantities(knownQuantities);
      setQuantities(quantities);
      Expression leftSide = new BinaryExpression(new UnaryExpression(getQuantity(1)), new UnaryExpression("2"), '^');

      Expression rightSide = new BinaryExpression(new BinaryExpression(new UnaryExpression(getQuantity(0)), new UnaryExpression("2"), '^'), new BinaryExpression(new UnaryExpression(getQuantity(3)), new BinaryExpression(new UnaryExpression(getQuantity(4)), new UnaryExpression("2"), '*'), '*'), '+');

      setWork(new Steps(equation));
      Algebra.solveEquation(false, this.work, leftSide, rightSide);
   }
}