// Air: ΔX = ViΔt + 1/2aΔt^2
import java.util.*;

public class Air extends KinematicEquation {

   private String equation = "ΔX = Vi * Δt + 0.5 * a * Δt ^ 2";
   private double unknownValue;

   public Air() {
      setQuantity(4, askForQuantity("displacement", 4));
      setQuantity(0, askForQuantity("initial velocity", 0));
      setQuantity(2, askForQuantity("time", 2));
      setQuantity(3, askForQuantity("acceleration", 3));
      System.out.println();
      if (numberOfKnownQuantities() == 4) {
         Algebra.verifyEquality(getNumericalQuantity(4), getNumericalQuantity(0) * getNumericalQuantity(2) + 0.5 * getNumericalQuantity(3) * Math.pow(getNumericalQuantity(2), 2));
      }
      if (numberOfKnownQuantities() < 3) {
         // If given all 4 quantities, will verify if correct
         throw new IllegalArgumentException ("ERROR: Not enough quantities");
      }

      Expression leftSide = new UnaryExpression(getQuantity(4));
      //Expression ViTimesT = new BinaryExpression(new UnaryExpression(getQuantity(0)), new UnaryExpression(getQuantity(2)), '*');
      //Expression halfA = new BinaryExpression("0.5", new UnaryExpression(getQuantity(3)), '*');
      //Expression timeSquared = new BinaryExpression(new UnaryExpression(getQuantity(2)), "2", '^');
      //Expression halfATimeSquared = new BinaryExpression(halfA, timeSquared, '*');
      Expression rightSide = new BinaryExpression(new BinaryExpression(new UnaryExpression(getQuantity(0)), new UnaryExpression(getQuantity(2)), '*'), new BinaryExpression(new BinaryExpression("0.5", new UnaryExpression(getQuantity(3)), '*'), new BinaryExpression(new UnaryExpression(getQuantity(2)), "2", '^'), '*'), '+');


      if (!getKnownQuantities()[2]) {
         unknownValue = Algebra.getPositiveQuadraticRoot(0.5 * getNumericalQuantity(3), getNumericalQuantity(0), -1 * getNumericalQuantity(4));
      }
      else {
         unknownValue = Algebra.solveEquation(leftSide, rightSide);
      }
      System.out.println(unknownValue);
//      unknownValue = Algebra.solveEquation(equation, getKnownQuantities(), getQuantities());
   }
}