// Fire: Vf^2 = Vi^2 + 2aΔX
import java.util.*;

public class Fire extends KinematicEquation {

   private String equation = "Vf ^ 2 = Vi ^ 2 + 2 * a * ΔX";

   public Fire() {
      setQuantity(1, askForQuantity("final velocity", 1));
      setQuantity(0, askForQuantity("initial velocity", 0));
      setQuantity(3, askForQuantity("acceleration", 3));
      setQuantity(4, askForQuantity("displacement", 4));
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