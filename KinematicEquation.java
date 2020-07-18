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
      String[] terms = input.split(" ");
      if (input.equalsIgnoreCase("?")) {
         return input;
      } else if (input.equalsIgnoreCase("quit")) {
         throw new IllegalArgumentException("quit");
      } else if (Algebra.isNumber(terms[0])) {
         knownQuantities[quantityIndex] = true;
         if (terms.length == 1) {
            return input;
         }
         else {
            UnitConversion conversion = new UnitConversion(terms[1]);
            return Double.toString(Double.parseDouble(terms[0]) * conversion.toBaseUnit());
         }
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