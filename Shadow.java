// Shadow: ΔX = VfΔt - 1/2aΔt^2
import java.util.*;

public class Shadow extends KinematicEquation {

   private String equation = "ΔX = Vf * Δt - 0.5 * a * Δt ^ 2";

   public Shadow() {
      setQuantity(4, askForQuantity("displacement", 4));
      setQuantity(1, askForQuantity("final velocity", 1));
      setQuantity(2, askForQuantity("time", 2));
      setQuantity(3, askForQuantity("acceleration", 3));
      System.out.println();
      if (numberOfKnownQuantities() == 4) {
      }
      if (numberOfKnownQuantities() < 3) {
         // If given all 4 quantities, will verify if correct
         throw new IllegalArgumentException ("ERROR: Not enough quantities");
      }
//      Algebra.solveEquation(equation, getKnownQuantities(), getQuantities());
   
   }
}