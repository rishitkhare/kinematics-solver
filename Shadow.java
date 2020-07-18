// Shadow: ΔX = VfΔt - 1/2aΔt^2
import java.util.*;

public class Shadow extends KinematicEquation {

   private String equation = "ΔX = Vf * Δt - 0.5 * a * Δt ^ 2";
   private double unknownValue;

   public Shadow() {
      setQuantity(4, askForQuantity("displacement", 4));
      setQuantity(1, askForQuantity("final velocity", 1));
      setQuantity(2, askForQuantity("time", 2));
      setQuantity(3, askForQuantity("acceleration", 3));
      System.out.println();
      if (numberOfKnownQuantities() == 4) {
         Algebra.verifyEquality(getNumericalQuantity(4), getNumericalQuantity(1) * getNumericalQuantity(2) - 0.5 * getNumericalQuantity(3) * Math.pow(getNumericalQuantity(2), 2));
      }
      if (numberOfKnownQuantities() < 3) {
         // If given all 4 quantities, will verify if correct
         throw new IllegalArgumentException ("ERROR: Not enough quantities");
      }

      Expression leftSide = new UnaryExpression(getQuantity(4));
      Expression rightSide = new BinaryExpression(new BinaryExpression(new UnaryExpression(getQuantity(1)), new UnaryExpression(getQuantity(2)), '*'), new BinaryExpression(new BinaryExpression("0.5", new UnaryExpression(getQuantity(3)), '*'), new BinaryExpression(new UnaryExpression(getQuantity(2)), "2", '^'), '*'), '-');
//      Algebra.solveEquation(equation, getKnownQuantities(), getQuantities());

      if (!getKnownQuantities()[2]) {
         unknownValue = Algebra.getPositiveQuadraticRoot(-0.5 * getNumericalQuantity(3), getNumericalQuantity(1), -1 * getNumericalQuantity(4));
      }
      else {
         unknownValue = Algebra.solveEquation(leftSide, rightSide);
      }
      System.out.println(unknownValue);
   }
}