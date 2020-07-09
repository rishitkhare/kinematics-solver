// Air: ΔX = ViΔt + 1/2aΔt^2
import java.util.*;

public class Air extends KinematicEquation {
   public Air() {
      setQuantity(4, askForQuantity("displacement", 4));
      setQuantity(0, askForQuantity("initial velocity", 0));
      setQuantity(2, askForQuantity("time", 2));
      setQuantity(3, askForQuantity("acceleration", 3));
      System.out.println();
      if (numberOfKnownQuantities() == 4) {
         Algebra.verifyEquality(getQuantity(4), 
                               (getQuantity(0) * getQuantity(2)) + (0.5 * getQuantity(3) * Math.pow(getQuantity(2), 2))); 
      }
      if (numberOfKnownQuantities() < 3) {
         // If given all 4 quantities, will verify if correct
         throw new IllegalArgumentException ("ERROR: Not enough quantities");
      }  
   }
}