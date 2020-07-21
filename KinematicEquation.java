// Superclass represents generic blueprint for a kinematic equation
import java.util.*;

public class KinematicEquation {

   // Represents whether each quantity is known {Vi, Vf, Δt, a, ΔX}
   private boolean[] knownQuantities = new boolean[5];
   private String[] quantities = {"Vi", "Vf", "Δt", "a", "ΔX"};

   protected Steps work;

   public void doAlgebra() {
      //should never be called from a generic object
      //(overidden by subclasses)
   }

   public void setQuantity(int quantityIndex, String quantity) {
      if (! quantity.equalsIgnoreCase("?")) {
         quantities[quantityIndex] = quantity;
      }
   }
   
   public String getQuantity(int quantityIndex) {
      return quantities[quantityIndex];
   }

   public double getNumericalQuantity(int quantityIndex) {
      if (Algebra.isNumber(quantities[quantityIndex])) {
         return Double.parseDouble(quantities[quantityIndex]);
      }
      throw new IllegalArgumentException("ERROR: " + quantities[quantityIndex] + " is not a number");
   }
   
   public boolean[] getKnownQuantities() {
      return this.knownQuantities;
   }
   
   public String[] getQuantities() {
      return quantities;
   }
   
   public int numberOfKnownQuantities() {
      int count = 0;
      for (int index = 0; index < knownQuantities.length; index++) {
         if (knownQuantities[index]) {
            count++;
         }
      }
      return count;
   }

   public void setWork(Steps work) {
      this.work = work;
   }

   public void setKnownQuantities(boolean[] knownQuantities) {
      this.knownQuantities = knownQuantities;
   }

   public void setQuantities(String[] quantities) {
      this.quantities = quantities;
   }

   public String getAnswer() {
      return work.getLastStep();
   }

   public int getMissingQuantityIndex() {
      for (int quantityIndex = 0; quantityIndex < knownQuantities.length; quantityIndex++) {
         if (knownQuantities[quantityIndex] == false) {
            return quantityIndex;
         }
      }
      throw new IllegalArgumentException("ERROR: All values are known");
   }

   public boolean isTimeKnown() {
      return knownQuantities[2];
   }

   public String getWork() {
      return work.toString();
   }

   public void checkNumberOfQuantities(int quantities) {
      if (numberOfKnownQuantities() == 4) {
         Algebra.verifyEquality(getNumericalQuantity(4), 0.5 * (getNumericalQuantity(0) + getNumericalQuantity(1)) * getNumericalQuantity(2));
      }
      if (numberOfKnownQuantities() < 3) {
         // If given all 4 quantities, will verify if correct
         throw new IllegalArgumentException ("ERROR: Not enough quantities");
      }
   }
}