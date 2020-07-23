import java.util.*;
import java.io.*;

public class KinematicEquationSolver {

   //class constant input file
   public static Scanner scan = new Scanner(System.in);

   //Used to printKinematicEquations();
   public static final String[][] EQUATIONS = { {"Earth", "Vf = Vi + aΔt"},
                                                {"Water", "ΔX = 1/2(Vf + Vi)Δt"},
                                                {"Fire", "Vf^2 = Vi^2 + 2aΔX"},
                                                {"Air", "ΔX = ViΔt + 1/2aΔt^2"},
                                                {"Shadow", "ΔX = VfΔt - 1/2aΔt^2"} };

   //Each variable in the kinematics equations is stored in an array, and the indexes are as follows:
   public static final String[] QUANTITY_NAMES = {"initial velocity", "final velocity", "time", "acceleration", "displacement"};

   //Uses the indexes of QUANTITY_NAMES to write the order in which each of the values are asked of the user
   public static final int[][] EQUATION_ASK_ORDER = { {1, 0, 3, 2}, // Earth
                                                      {4, 1, 0, 2}, // Water
                                                      {1, 0, 3, 4}, // Fire
                                                      {4, 0, 2, 3}, // Air
                                                      {4, 1, 2, 3},  // Shadow
                                                      {0, 1, 2, 3, 4} }; // Solving from given unknowns

   //main method
   public static void main(String[] args) {
      //prints out information for the user
      runTests();
      scan = new Scanner(System.in);
      printWelcomeMessage();
      printEmptyLine();
      printKinematicEquations();
      printEmptyLine();

      //input loop that continues to ask for kinematics problems until user types "quit"
      String input = getEquationInput(true);
      while (! input.equalsIgnoreCase("quit")) {
         if (! input.equalsIgnoreCase("quit")) {
            Steps solution = processInput(input);
            System.out.println(solution.getLastStep());
            System.out.print("Enter \"w\" to display the work for this problem. Press \"enter\" to continue: ");
            String answer = scan.nextLine();
            if (answer.equalsIgnoreCase("w")) {
               System.out.println(solution.toString());
            }
         }
         input = getEquationInput(false);
      }


      printGoodbyeMessage();
   }

   public static void printEmptyLine() {
      System.out.println();
   }

   //Welcome message for the program
   public static void printWelcomeMessage() {
      System.out.println("Hello and welcome to the kinematic equation solver");
   }

   //Final message upon quitting program
   public static void printGoodbyeMessage() {
      System.out.println("Ok. I get it. You don't want me. Come again for my daily uploads.");
   }

   //prints all the kinematic equations for the user
   public static void printKinematicEquations() {
      System.out.println("There are " + EQUATIONS.length + " kinematic equations:");
      for (int rowIndex = 0; rowIndex < EQUATIONS.length; rowIndex++) {
         System.out.println(displayKinematicEquation(rowIndex));
      }   
   }

   //used in printKinematicEquations() to print ONE equation
   public static String displayKinematicEquation(int rowIndex) {
      return "\t" + (rowIndex + 1) + ". " + EQUATIONS[rowIndex][0] + ": " + EQUATIONS[rowIndex][1];
   }

   //text prompt for user to pick an equation (should only happen the first time)
   public static String getEquationCommand() {
      return "Enter an equation's name or number(1-" + EQUATIONS.length + ")" + "\n" + 
             "If you prefer to enter values and solve automatically, enter \"solve\"" + "\n" +
             "Enter \"quit\" to exit the program";
                 
   }

   //boolean parameter tells it whether or not to print the long version or the short version
   //the long version is only printed the first time upon running program, as seen in main()
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

      return scan.nextLine();
   }

   //takes user input and begins constructing the KinematicEquation object to solve the problem
   public static Steps processInput(String input) {
      //try catch loop will catch impossible problems and invalid user input. Any other errors should not occur.
      //try {
         //creates object as null, arrays store info that will be passed to constructor of one of the KinematicEquation subclasses
         KinematicEquation solution = null;
         boolean[] knowns = new boolean[5];
         String[] quantities = {"Vi", "Vf", "Δt", "a", "ΔX"}; //Make this part of KinematicEquation constructor

         //parses user input to decide which equation to use and then asks for required quantities
         if (input.equalsIgnoreCase("solve")) {
            //equation is not given
            solution = new KinematicEquation();
            solveFromUnknowns(solution, knowns, quantities); //this method handles getting necessary quantities, solving, and printing
         }
         else {
            if (input.equalsIgnoreCase("1") || input.equalsIgnoreCase("Earth")) {
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
         }


         //solves the problem
         solution.doAlgebra();

         //print the answer
         return solution.getWork();

     /* }
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
      }*/
   }

   public static void solveFromUnknowns(KinematicEquation generalEquation, boolean[] knowns, String[] quantities) {

      //ask for all quantities
      setQuantities(quantities, knowns, 5);
      generalEquation.setQuantities(quantities);
      generalEquation.setKnownQuantities(knowns);

      ArrayList<Steps> allWork = new ArrayList<Steps>();

      //depending on how many knowns we have, different approach will be taken
      if (generalEquation.numberOfKnownQuantities() < 3) {
         //Not enough info (5 variables, we must know at least 3)
         throw new IllegalArgumentException("ERROR: Not enough information");
      }
      else if (generalEquation.numberOfKnownQuantities() == 3 || generalEquation.numberOfKnownQuantities() == 4)  {
         while(generalEquation.numberOfKnownQuantities() != 5) {
            if (know3SharedValues(knowns, Earth.presentQuantities)) {
               generalEquation = new Earth(knowns, quantities);
            }
            else if (know3SharedValues(knowns, Water.presentQuantities)) {
               generalEquation = new Water(knowns, quantities);
            }
            else if (know3SharedValues(knowns, Fire.presentQuantities)) {
               generalEquation = new Fire(knowns, quantities);
            }
            else if (know3SharedValues(knowns, Air.presentQuantities)) {
               generalEquation = new Air(knowns, quantities);
            }
            else if (know3SharedValues(knowns, Shadow.presentQuantities)) {
               generalEquation = new Shadow(knowns, quantities);
            }
            else {
               throw new IllegalArgumentException("ERROR: HOW DID WE GET HERE.");
            }

            //solves the problem
            generalEquation.doAlgebra();
            //print the answer
            System.out.println(generalEquation.getAnswer());

            allWork.add(generalEquation.getWork());

            generalEquation = new KinematicEquation(generalEquation.getKnownQuantities(), generalEquation.getQuantities());
         }
      }
      else { // knows all 5 values
         //verifies two equations to verify all five values
         //this else statement will always throw an error, because the equation will either already be solved or be incorrect
         //the following code checks to make sure

         boolean isValid = false;
         Earth verifyEarth = new Earth(knowns, quantities);
         Water verifyWater = new Water(knowns, quantities);
         if (Algebra.isEqualEquation(verifyEarth.getLeftSide(), verifyEarth.getRightSide()) && Algebra.isEqualEquation(verifyWater.getLeftSide(), verifyWater.getRightSide())) {
            throw new IllegalArgumentException("ERROR: Umm you already know all the quantities");
         }
         else {
            throw new IllegalArgumentException("ERROR: Unequal equation");
         }
      }

      System.out.print("Enter \"w\" to display the work for this problem. Press \"enter\" to continue: ");
      String answer = scan.nextLine();
      if (answer.equalsIgnoreCase("w")) {
         for(Steps eachWork : allWork) {
            System.out.println(eachWork.toString());
            printEmptyLine();
         }
      }
   }

   public static String askForQuantity(String[] quantities, String quantity, boolean[] knowns, int quantityIndex) {

      System.out.println("What is the value for the " + quantity + ". Enter \"?\" if unknown. Enter \"u\" to display possible units and proper abbreviations: ");
      String input = scan.nextLine();
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

   public static void setQuantity(String[] quantities, boolean[] knowns, int quantityIndex, String quantity) {
      String[] terms = quantity.split(" ");
      if (! quantity.equalsIgnoreCase("?")) {
         knowns[quantityIndex] = true;
         if (Algebra.isNumber(terms[0])) {
            if (terms.length == 1) {
               quantities[quantityIndex] = terms[0].toString();
            }
            else {
               if (quantityIndex == 0) {
                  quantityIndex = 1;
               }
               quantities[quantityIndex] = Double.toString(Double.parseDouble(terms[0]) * UnitConversion.unitToConversionFactor(terms[1], quantityIndex));
            }
         }
      }
   }

   public static boolean know3SharedValues(boolean[] knowns1, boolean[] knowns2) {
      // Assuming that knowns1.length = knowns2.length = 5
      int count = 0;
      for (int index = 0; index < knowns1.length; index++) {
         if (knowns1[index] && knowns2[index]) {
            count++;
         }
      }

      return count == 3;
   }

   public static void runTests() {

      int count = 0;

      File testFile = new File("TestCases.txt");
      try {
         scan = new Scanner(new File("TestCases.txt"));
      }
      catch (FileNotFoundException ex) { }
      int testIndex = 0;
      while (scan.hasNextLine()) {
         if (processInput(scan.nextLine()).getNumericalAnswer() == testsAnswers[testIndex])
         scan.nextLine();
      }

      double [] testsAnswers = {3, -2879.167, 11.003, 40, 161.622,
                                77, 496.792, 20.082, 0, 2939.288,
                                4.0, 3.081, 2157.788, 66.865, 0,
                                -5.336, 124.261, 23.064, 10.435, 2.346,
                                14.313};
   }

}
