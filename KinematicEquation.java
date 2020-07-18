// Superclass represents generic blueprint for a kinematic equation
import java.util.*;

public abstract class KinematicEquation {

   // Represents whether each quantity is known {Vi, Vf, Δt, a, Δx} 
   private boolean[] knownQuantities = new boolean[5];
   private String[] quantities = new String[5];
   
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
   
   public String askForQuantity(String quantity, int quantityIndex) {
      Scanner scan = new Scanner(System.in);
      System.out.print("What is the value for the " + quantity + ". Enter \"?\" if unknown: ");
      String input = scan.nextLine();
      if (input.equalsIgnoreCase("?")) {
         return input;
      } else if (Algebra.isNumber(input)) {
         knownQuantities[quantityIndex] = true;
         return input;
      } else if (input.equalsIgnoreCase("quit")) {
         throw new IllegalArgumentException("quit");
      } else {
         System.out.println("Invalid input: \"" + input + "\"");
         return askForQuantity(quantity, quantityIndex);
      }
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
}