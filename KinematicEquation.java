/* - THE KINEMATICS EQUATION SOLVER -
 *
 * This program works by essentially taking what is known, what is not known, and using algebra to solve
 * for the unknowns. The quantities that are used in kinematic equations are:
 * 
 * Vi - velocity the object starts at
 * Vf - velocity the object ends at
 * Δt - the amount of time the object takes to travel
 * a  - the acceleration at which the object travels
 * ΔX - the total displacement of the object across the motion
 *
 * The solver uses five kinematic equations (as subclasses of this one), for each of which we have created
 * 'codewords' in order to make them easier write into code. The key is as follows:
 *
 * Earth  -  Vf = Vi + aΔt
 * Air    -  ΔX = ViΔt + 1/2aΔt^2
 * Fire   -  Vf^2 = Vi^2 + 2aΔx
 * Water  -  ΔX = t * 0.5 * (Vf + Vi)
 * Shadow -  ΔX = VfΔt - 1/2aΔt^2
 *
 * If the superclass constructor is called, then that means that the user does not know which equation
 * to use and the computer needs to decide. This will be handled in main.
 */



// Superclass represents generic blueprint for a kinematic equation
import java.util.*;

public class KinematicEquation {

   // Represents whether each quantity is known {Vi, Vf, Δt, a, ΔX}
   private boolean[] knownQuantities = new boolean[5];
   private String[] quantities = {"Vi", "Vf", "Δt", "a", "ΔX"};

   protected Steps work;

   protected Expression leftSide;
   protected Expression rightSide;

   //method for actually solving equation
   public void doAlgebra() {
      //should never be called from a generic KinematicEquation object
      //(overidden by subclasses)
   }


   //*** ACCESSORS ***\\

   //accessor for work
   public String getWork() {
      return work.toString();
   }

   //accessor method for all quantities
   public String getQuantity(int quantityIndex) {
      return quantities[quantityIndex];
   }

   //accesses quantities but as double
   public double getNumericalQuantity(int quantityIndex) {
      if (Algebra.isNumber(quantities[quantityIndex])) {
         return Double.parseDouble(quantities[quantityIndex]);
      }
      throw new IllegalArgumentException("ERROR: " + quantities[quantityIndex] + " is not a number");
   }

   //accessor for knownQuantities
   public boolean[] getKnownQuantities() {
      return this.knownQuantities;
   }

   //accessor for quantities
   public String[] getQuantities() {
      return quantities;
   }

   //counts number of 'true' in knownQuantities
   public int numberOfKnownQuantities() {
      int count = 0;
      for (int index = 0; index < knownQuantities.length; index++) {
         if (knownQuantities[index]) {
            count++;
         }
      }
      return count;
   }


   //*** MUTATORS ***\\

   //sets quantity in array and updates knownQuantities
   public void setQuantity(int quantityIndex, String quantity) {
      if (! quantity.equalsIgnoreCase("?")) {
         quantities[quantityIndex] = quantity;
         knownQuantities[quantityIndex] = true;
      }
   }

   //mutator for work
   public void setWork(Steps work) {
      this.work = work;
   }

   //mutator for leftSide
   public void setLeftSide(Expression leftSide) {
      this.leftSide = leftSide;
   }

   //mutator for rightSide
   public void setRightSide(Expression rightSide) {
      this.rightSide = rightSide;
   }

   //mutator for knownQuantities
   public void setKnownQuantities(boolean[] knownQuantities) {
      this.knownQuantities = knownQuantities;
   }

   //mutator for quantities
   public void setQuantities(String[] quantities) {
      this.quantities = quantities;
   }

   //*** OTHER METHODS ***\\

   //TODO: add units in steps class to final answer
   //returns the answer by getting the last step of the work (ex: Vi = 5.0)
   public String getAnswer() {
      return work.getLastStep();
   }

   //checks for the first unknown on the list
   public int getMissingQuantityIndex() {
      for (int quantityIndex = 0; quantityIndex < knownQuantities.length; quantityIndex++) {
         if (knownQuantities[quantityIndex] == false) {
            return quantityIndex;
         }
      }
      throw new IllegalArgumentException("ERROR: All values are known");
   }

   //Used for special case when solving quadratic equation in AIR
   public boolean isTimeKnown() {
      return knownQuantities[2];
   }

   //
   public void checkNumberOfQuantities(int quantities) {
      if (numberOfKnownQuantities() == 4) {
         // if all four quantities of equation are known, then throw error (error may differ based on correctness of values given)
         Algebra.verifyEquality(leftSide.evaluate(), rightSide.evaluate());
      }
      if (numberOfKnownQuantities() < 3) {
         // if there isn't enough information, throw error
         throw new IllegalArgumentException ("ERROR: Not enough information");
      }
   }
}