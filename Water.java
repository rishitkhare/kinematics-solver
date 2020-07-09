// Water: ΔX = 1/2(Vf + Vi)Δt
import java.util.*;

public class Water extends KinematicEquation {
   public Water() {
      setQuantity(4, askForQuantity("displacement", 4));
      setQuantity(1, askForQuantity("final velocity", 1));
      setQuantity(0, askForQuantity("initial velocity", 0));
      setQuantity(2, askForQuantity("time", 2));
      System.out.println();
      if (numberOfKnownQuantities() == 4) {
         Algebra.verifyEquality(getQuantity(4), 0.5 * (getQuantity(1) + getQuantity(0)) * getQuantity(2));
      }
      if (numberOfKnownQuantities() < 3) {
         // If given all 4 quantities, will verify if correct
         throw new IllegalArgumentException ("ERROR: Not enough quantities");
      }  
   }
}