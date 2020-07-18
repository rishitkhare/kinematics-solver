// Water: ΔX = 1/2(Vf + Vi)Δt
import java.util.*;

public class Water extends KinematicEquation {

   private String equation = "ΔX = 0.5 * ( Vf + Vi ) * Δt";

   public Water() {
      setQuantity(4, askForQuantity("displacement", 4));
      setQuantity(1, askForQuantity("final velocity", 1));
      setQuantity(0, askForQuantity("initial velocity", 0));
      setQuantity(2, askForQuantity("time", 2));
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