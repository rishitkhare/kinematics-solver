// Earth: Vf = Vi + aΔt
import java.util.*;

public class Earth extends KinematicEquation {
   public Earth() {
      setQuantity(1, askForQuantity("final velocity", 1));
      setQuantity(0, askForQuantity("initial velocity", 0));
      setQuantity(3, askForQuantity("acceleration", 3));
      setQuantity(2, askForQuantity("time", 2));
      System.out.println();
      if (numberOfKnownQuantities() == 4) {
         Algebra.verifyEquality(getQuantity(1), getQuantity(0) + (getQuantity(3) * getQuantity(2)));
      }
      if (numberOfKnownQuantities() < 3) {
         // If given all 4 quantities, will verify if correct
         throw new IllegalArgumentException ("ERROR: Not enough quantities");
      }  
   }
}