// Fire: Vf^2 = Vi^2 + 2aΔX
import java.util.*;

public class Fire extends KinematicEquation {
   public Fire() {
      setQuantity(1, askForQuantity("final velocity", 1));
      setQuantity(0, askForQuantity("initial velocity", 0));
      setQuantity(3, askForQuantity("acceleration", 3));
      setQuantity(4, askForQuantity("displacement", 4));
      System.out.println();
      if (numberOfKnownQuantities() == 4) {
         Algebra.verifyEquality(Math.pow(getQuantity(1), 2), 
                                Math.pow(getQuantity(0), 2) + (2 * getQuantity(3) * getQuantity(4)));
      }
      if (numberOfKnownQuantities() < 3) {
         // If given all 4 quantities, will verify if correct
         throw new IllegalArgumentException ("ERROR: Not enough quantities");
      }  
   }
}