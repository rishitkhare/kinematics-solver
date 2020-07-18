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
      }
      if (numberOfKnownQuantities() < 3) {
         // If given all 4 quantities, will verify if correct
         throw new IllegalArgumentException ("ERROR: Not enough quantities");
      }
//      unknownValue = Algebra.solveEquation(equation, getKnownQuantities(), getQuantities());
   }
}