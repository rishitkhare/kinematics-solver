import java.util.*;

public class KinematicEquationSolver {

   public static final Scanner SCAN = new Scanner(System.in);
   
   public static final String[][] EQUATIONS = { {"Earth", "Vf = Vi + aΔt"},
                                                {"Water", "ΔX = 1/2(Vf + Vi)Δt"}, 
                                                {"Fire", "Vf^2 = Vi^2 + 2aΔX"}, 
                                                {"Air", "ΔX = ViΔt + 1/2aΔt^2"},
                                                {"Shadow", "ΔX = VfΔt - 1/2aΔt^2"} };
   public static void main(String[] args) {
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
         if (input.equalsIgnoreCase("solve")) {
            // solveEquation()
            System.out.println("solve");
         }
         else if (input.equalsIgnoreCase("1") || input.equalsIgnoreCase("Earth")) {
            // runEarthEquation();
            System.out.println(displayKinematicEquation(0));
            Earth earth = new Earth();
         }
         else if (input.equalsIgnoreCase("2") || input.equalsIgnoreCase("Water")) {
            // runWaterEquation();
            System.out.println(displayKinematicEquation(1));
            Water water = new Water();
         }
         else if (input.equalsIgnoreCase("3") || input.equalsIgnoreCase("Fire")) {
            // runFireEquation();
            System.out.println(displayKinematicEquation(2));
            Fire fire = new Fire();
         }
         else if (input.equalsIgnoreCase("4") || input.equalsIgnoreCase("Air")) {
            // runAirEquation();
            System.out.println(displayKinematicEquation(3));
            Air air = new Air();
         }
         else if (input.equalsIgnoreCase("5") || input.equalsIgnoreCase("Shadow")) {
            // runShadowEquation();
            System.out.println(displayKinematicEquation(4));
            Shadow shadow = new Shadow();
         }
         else {
            System.out.println("\t" + "Invalid input: \"" + input + "\"");
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
}
