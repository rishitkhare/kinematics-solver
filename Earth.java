// Earth: Vf = Vi + aΔt
import java.util.*;

public class Earth extends KinematicEquation {

   private String equation = "Vf = Vi + a * Δt";
   private double unknownValue;

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

      unknownValue = Algebra.solveEquation(leftSide, rightSide);
      System.out.println(unknownValue);
   }
}