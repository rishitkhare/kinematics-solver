import java.util.*;

public class KinematicEquationSolver {

   public static final Scanner SCAN = new Scanner(System.in);
   
   public static final String[][] EQUATIONS = { {"Earth", "Vf = Vi + aΔt"},
                                                {"Water", "ΔX = 1/2(Vf + Vi)Δt"}, 
                                                {"Fire", "Vf^2 = Vi^2 + 2aΔX"}, 
                                                {"Air", "ΔX = ViΔt + 1/2aΔt^2"},
                                                {"Shadow", "ΔX = VfΔt - 1/2aΔt^2"} };
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
         if (input.equalsIgnoreCase("solve")) {
            solution = new KinematicEquation();
            solveEquation(solution);
            return;
         }
         else if (input.equalsIgnoreCase("1") || input.equalsIgnoreCase("Earth")) {
            // runEarthEquation();
            System.out.println(displayKinematicEquation(0));
            solution = new Earth();
         }
         else if (input.equalsIgnoreCase("2") || input.equalsIgnoreCase("Water")) {
            // runWaterEquation();
            System.out.println(displayKinematicEquation(1));
            solution = new Water();
         }
         else if (input.equalsIgnoreCase("3") || input.equalsIgnoreCase("Fire")) {
            // runFireEquation();
            System.out.println(displayKinematicEquation(2));
            solution = new Fire();
         }
         else if (input.equalsIgnoreCase("4") || input.equalsIgnoreCase("Air")) {
            // runAirEquation();
            System.out.println(displayKinematicEquation(3));
            solution = new Air();
         }
         else if (input.equalsIgnoreCase("5") || input.equalsIgnoreCase("Shadow")) {
            // runShadowEquation();
            System.out.println(displayKinematicEquation(4));
            solution = new Shadow();
         }
         else {
            System.out.println("\t" + "Invalid input: \"" + input + "\"");
         }
         System.out.println(solution.getAnswer());
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

   public static void solveEquation(KinematicEquation generalEquation) {
      Steps work;
      generalEquation.setQuantity(0, generalEquation.askForQuantity("initial velocity", 0));
      generalEquation.setQuantity(1, generalEquation.askForQuantity("final velocity", 1));
      generalEquation.setQuantity(2, generalEquation.askForQuantity("time", 2));
      generalEquation.setQuantity(3, generalEquation.askForQuantity("acceleration", 3));
      generalEquation.setQuantity(4, generalEquation.askForQuantity("displacement", 4));
      if (generalEquation.numberOfKnownQuantities() < 3) {
         throw new IllegalArgumentException("ERROR: Must have at least 3 quantities");
      }
      else if (generalEquation.numberOfKnownQuantities() == 3) {

      }
      else if ((generalEquation.numberOfKnownQuantities() == 4)) {
         int unknownIndex = generalEquation.getMissingQuantityIndex();
         if (unknownIndex != 4) {
            generalEquation = new Earth(generalEquation.getKnownQuantities(), generalEquation.getQuantities());
            System.out.println(generalEquation.getAnswer());
         }
         else {
            generalEquation = new Water(generalEquation.getKnownQuantities(), generalEquation.getQuantities());
            System.out.println(generalEquation.getAnswer());
         }
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
}
