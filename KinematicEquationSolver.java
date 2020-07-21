import java.util.*;

public class KinematicEquationSolver {

   public static final Scanner SCAN = new Scanner(System.in);
   
   public static final String[][] EQUATIONS = { {"Earth", "Vf = Vi + aΔt"},
                                                {"Water", "ΔX = 1/2(Vf + Vi)Δt"}, 
                                                {"Fire", "Vf^2 = Vi^2 + 2aΔX"}, 
                                                {"Air", "ΔX = ViΔt + 1/2aΔt^2"},
                                                {"Shadow", "ΔX = VfΔt - 1/2aΔt^2"} };

   public static final String[] QUANTITY_NAMES = {"initial velocity", "final velocity", "time", "acceleration", "displacement"};

   public static final int[][] EQUATION_ASK_ORDER = { {1, 0, 3, 2}, // Earth
                                                      {4, 1, 0, 2}, // Water
                                                      {1, 0, 3, 4}, // Fire
                                                      {4, 0, 2, 3}, // Air
                                                      {4, 1, 2, 3} }; // Shadow

   public static void main(String[] args) {
      System.out.println(UnitConversion.getValidUnits());
      printWelcomeMessage();
      printEmptyLine();
      printKinematicEquations();
      printEmptyLine();
      String input = getEquationInput(true);
      while (! input.equalsIgnoreCase("quit")) {
         if (! input.equalsIgnoreCase("quit")) {
            processInput(input);   
         }
         input = getEquationInput(false);
      }
      printGoodbyeMessage();
   }
   
   public static void printEmptyLine() {
      System.out.println();
   }
   
   public static void printWelcomeMessage() {
      System.out.println("Hello and welcome to the kinematic equation solver");
   }
   
   public static void printGoodbyeMessage() {
      System.out.println("Ok. I get it. You don't want me. Come again for my daily uploads.");
   }
   
   public static void printKinematicEquations() {
      System.out.println("There are " + EQUATIONS.length + " kinematic equations:");
      for (int rowIndex = 0; rowIndex < EQUATIONS.length; rowIndex++) {
         System.out.println(displayKinematicEquation(rowIndex));
      }   
   }
   
   public static String displayKinematicEquation(int rowIndex) {
      return "\t" + (rowIndex + 1) + ". " + EQUATIONS[rowIndex][0] + ": " + EQUATIONS[rowIndex][1];
   }
   
   public static String getEquationCommand() {
      return "Enter an equation's name or number(1-" + EQUATIONS.length + ")" + "\n" + 
             "If you prefer to enter values and solve automatically, enter \"solve\"" + "\n" +
             "Enter \"quit\" to exit the program";
                 
   }
   
   public static String getEquationInput(boolean printEquationCommand) {
      if (printEquationCommand) {
         System.out.println(getEquationCommand()); 
      }
      else {
         System.out.println("Enter the name, number(1-" + EQUATIONS.length + "), \"solve\", or \"quit\"");
      }
      printEmptyLine();
      System.out.print("What can I help you with? ");
      String answer = "";

      return SCAN.nextLine();
   }
   
   public static void processInput(String input) {
      try {
         KinematicEquation solution = null;
         boolean[] knowns = new boolean[5];
         String[] quantities = {"Vi", "Vf", "Δt", "a", "ΔX"};
         if (input.equalsIgnoreCase("solve")) {
            solution = new KinematicEquation();
            solveEquation(solution, knowns, quantities);
            return;
         }
         else if (input.equalsIgnoreCase("1") || input.equalsIgnoreCase("Earth")) {
            System.out.println(displayKinematicEquation(0));
            setQuantities(quantities, knowns, 0);
            solution = new Earth(knowns, quantities);
         }
         else if (input.equalsIgnoreCase("2") || input.equalsIgnoreCase("Water")) {
            System.out.println(displayKinematicEquation(1));
            setQuantities(quantities, knowns, 1);
            solution = new Water(knowns, quantities);
         }
         else if (input.equalsIgnoreCase("3") || input.equalsIgnoreCase("Fire")) {
            System.out.println(displayKinematicEquation(2));
            setQuantities(quantities, knowns, 2);
            solution = new Fire(knowns, quantities);
         }
         else if (input.equalsIgnoreCase("4") || input.equalsIgnoreCase("Air")) {
            System.out.println(displayKinematicEquation(3));
            setQuantities(quantities, knowns, 3);
            solution = new Air(knowns, quantities);
         }
         else if (input.equalsIgnoreCase("5") || input.equalsIgnoreCase("Shadow")) {
            System.out.println(displayKinematicEquation(4));
            setQuantities(quantities, knowns, 4);
            solution = new Shadow(knowns, quantities);
         }
         else {
            throw new IllegalArgumentException("\tERROR: " + "Invalid input: \"" + input + "\"");
         }

         solution.doAlgebra();
         System.out.println(solution.getAnswer());
         System.out.print("Enter \"w\" to display the work for this problem. Press \"enter\" to continue: ");
         String answer = SCAN.nextLine();
         if (answer.equalsIgnoreCase("w")) {
            System.out.println(solution.getWork());
         }
      }
      catch (IllegalArgumentException ex) {
         if (ex.toString().contains("ERROR")) {
            System.out.println("\t" + ex.toString().substring(ex.toString().indexOf("ERROR")));
         }
         else if (ex.toString().contains("quit")) {
            printGoodbyeMessage();
            System.exit(0);
         }
         else {
            System.out.println("\t" + ex.toString());
         }
         printEmptyLine();
      }
   }

   public static void solveEquation(KinematicEquation generalEquation, boolean[] knowns, String[] quantities) {
      Steps work;
      generalEquation.setQuantity(0, askForQuantity(quantities, "initial velocity", knowns,0));
      generalEquation.setQuantity(1, askForQuantity(quantities, "final velocity", knowns, 1));
      generalEquation.setQuantity(2, askForQuantity(quantities, "time", knowns,2));
      generalEquation.setQuantity(3, askForQuantity(quantities, "acceleration", knowns,3));
      generalEquation.setQuantity(4, askForQuantity(quantities, "displacement", knowns,4));
      if (generalEquation.numberOfKnownQuantities() < 3) {
         throw new IllegalArgumentException("ERROR: Must have at least 3 quantities");
      }
      else if (generalEquation.numberOfKnownQuantities() == 3) {

      }
      else if ((generalEquation.numberOfKnownQuantities() == 4)) {
         int unknownIndex = generalEquation.getMissingQuantityIndex();
         if (unknownIndex != 4) {
            generalEquation = (Earth) new Earth(generalEquation.getKnownQuantities(), generalEquation.getQuantities());
         }
         else {
            generalEquation = new Water(generalEquation.getKnownQuantities(), generalEquation.getQuantities());
         }

         generalEquation.doAlgebra();
         System.out.println(generalEquation.getAnswer());
      }
      else { // knows all 5 values
         //verifies two equations to verify all five values

         boolean isValid = false;

         try {
            //verifies earth
            //(this will always throw an error)
            Algebra.verifyEquality(generalEquation.getNumericalQuantity(1), generalEquation.getNumericalQuantity(0) + (generalEquation.getNumericalQuantity(3) * generalEquation.getNumericalQuantity(2)));
         } catch(IllegalArgumentException earthResult){
            //updates isValid accordingly
            if(earthResult.toString().equals("ERROR: Umm you already know all the quantities")) {
               //verifies water
               try {
                  Algebra.verifyEquality(generalEquation.getNumericalQuantity(4), 0.5 * (generalEquation.getNumericalQuantity(0) + generalEquation.getNumericalQuantity(1)) * generalEquation.getNumericalQuantity(2));
               } catch(IllegalArgumentException waterResult) {
                  if(waterResult.toString().equals("ERROR: Umm you already know all the quantities")) {
                     isValid = true;
                  }
               }
            }
         }

         if(isValid) {
            throw new IllegalArgumentException("ERROR: Umm you already know all the quantities");
         }
         else {
            throw new IllegalArgumentException("ERROR: Unequal equation");
         }
      }

   }

   public static String askForQuantity(String[] quantities, String quantity, boolean[] knowns, int quantityIndex) {
      System.out.print("What is the value for the " + quantity + ". Enter \"?\" if unknown. Enter \"u\" to display possible units and proper abbreviations: ");
      String input = SCAN.nextLine();
      if(input.equalsIgnoreCase("u")) {
         System.out.println(UnitConversion.getValidUnits());
         input = askForQuantity(quantities, quantity, knowns, quantityIndex);
      }

      //unit conversion
      String[] terms = input.split(" ");


      if (input.equalsIgnoreCase("?")) {
         return quantities[quantityIndex];
      } else if (input.equalsIgnoreCase("quit")) {
         throw new IllegalArgumentException("quit");
      } else if (Algebra.isNumber(terms[0])) {
         knowns[quantityIndex] = true;

         if (terms.length == 1) {
            return terms[0].toString();
         }
         else {

            if (quantityIndex == 0) {
               quantityIndex = 1;
            }
            return Double.toString(Double.parseDouble(terms[0]) * UnitConversion.unitToConversionFactor(terms[1], quantityIndex));
         }
      } else {
         System.out.println("Invalid input: \"" + input + "\"");
         return askForQuantity(quantities, quantity, knowns, quantityIndex);
      }
   }

   public static void setQuantities(String[] quantities, boolean[] knowns, int row) {
      for (int index = 0; index < EQUATION_ASK_ORDER[row].length; index++) {
         int quantityIndex = EQUATION_ASK_ORDER[row][index];
         quantities[quantityIndex] = askForQuantity(quantities, QUANTITY_NAMES[quantityIndex], knowns, quantityIndex);
      }
   }
}
