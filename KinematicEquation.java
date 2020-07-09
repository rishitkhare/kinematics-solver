// Superclass represents generic blueprint for a kinematic equation
import java.util.*;

public abstract class KinematicEquation {

   // Represents whether each quantity is known {Vi, Vf, Δt, a, Δx} 
   private boolean[] knownQuantities = new boolean[5];
   private double[] quantities = new double[5];
   
   public void setQuantity(int quantityIndex, String quantity) {
      if (! quantity.equalsIgnoreCase("?")) {
         quantities[quantityIndex] = Double.parseDouble(quantity);
      }
   }
   
   public double getQuantity(int quantityIndex) {
      return quantities[quantityIndex];
   }
   
   public boolean[] getKnownQuantities() {
      return this.knownQuantities;
   }
   
   public String askForQuantity(String quantity, int quantityIndex) {
      Scanner scan = new Scanner(System.in);
      System.out.print("What is the value for the " + quantity + ". Enter \"?\" if unknown: ");
      String input = scan.nextLine();
      if (input.equalsIgnoreCase("?")) {
         return input;
      }
      else if (isNumber(input)) {
         knownQuantities[quantityIndex] = true;
         return input;
      }
      else {
         System.out.println("Invalid input: \"" + input + "\"");
         return askForQuantity(quantity, quantityIndex);
      }
   }
   
   public boolean isNumber(String input) {
      try {
         double answer = Double.parseDouble(input);
         return true;
      }
      catch (Exception ex) {
         return false;
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