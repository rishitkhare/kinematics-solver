import java.util.*;

public class Algebra {

   public static final String[][] QUANTITY_INDEXES = { {"Vi", "0"},
                                                      {"Vf", "1"},
                                                      {"Δt", "2"},
                                                      {"a", "3"},
                                                      {"ΔX", "4"} };

   //prevents instantiation
   private Algebra() {}

   public static void verifyEquality(double value1, double value2) {
      if (value1 != value2) {
         throw new IllegalArgumentException("ERROR: Unequal equation");
      }
      else {
         throw new IllegalArgumentException("ERROR: Umm you already know all the quantities");
      }
   }
   
   public static boolean isOperand(String component) {
      return (component.equals("+") || component.equals("-") || component.equals("(") || 
             component.equals("*") || component.equals("/") || component.equals(")") ||
             component.equals("^"));      
   }
   
   public static boolean isNumber(String component) {
      try {
         double num = Double.parseDouble(component);
         return true;
      }
      catch (Exception ex) {
         return false;
      }
   }

   
   public static ArrayList<String> getExpression(int operandIndex, ArrayList<String> equationSide) {
      ArrayList<String> expression = new ArrayList<String>();
      for (int index = operandIndex - 1; index <= operandIndex + 1; index++) {
         expression.add(equationSide.get(index));
      }
      return expression;
   }

   public static double solveEquation(/*Steps showYourWork,*/ Expression l, Expression r) {
      Expression rightSide = null;
      Expression leftSide = null;
      if (l.getIsKnown() && r.getIsKnown()) {
//         Algebra.verifyEquality(l, r);
      }
      else if (l.getIsKnown()) {
         // leftSide is known, rightSide is NOT known
         leftSide = r;
         rightSide = l;
      }
      else if (r.getIsKnown()) {
         leftSide = l;
         rightSide = r;
      }
      else {

         throw new IllegalArgumentException("ERROR: Both sides of equation contain an unknown value");
      }

      while (leftSide instanceof BinaryExpression) {
         BinaryExpression binaryLeftSide = (BinaryExpression) leftSide;
         char operator = binaryLeftSide.getOperator();
         boolean isOperand1Known;
         Expression knownExpression;
         if (binaryLeftSide.getOperand1().getIsKnown()) {
            knownExpression = binaryLeftSide.getOperand1();
            isOperand1Known = true;

            leftSide = ((BinaryExpression) leftSide).getOperand2();
         }
         else if (binaryLeftSide.getOperand2().getIsKnown()) {
            knownExpression = binaryLeftSide.getOperand2();
            isOperand1Known = false;

            leftSide = ((BinaryExpression) leftSide).getOperand1();

         }
         else {
            throw new IllegalArgumentException("ERROR: Neither expression in the leftside contains a known value");
         }

         // Change right side
         if (operator == '+') {
            rightSide = new BinaryExpression(rightSide, knownExpression, '-');
         }
         else if (operator == '-') {
            if (isOperand1Known) {
               rightSide = new BinaryExpression(knownExpression, rightSide, '-');
            }
            else {
              rightSide = new BinaryExpression(rightSide, knownExpression, '+');
            }
         }
         else if (operator == '*') {
            rightSide = new BinaryExpression(rightSide, knownExpression, '/');
         }
         else if (operator == '/') {
            if (isOperand1Known) {
               rightSide = new BinaryExpression(knownExpression, rightSide, '/');
            }
            else {
               rightSide = new BinaryExpression(rightSide, knownExpression, '*');
            }
         }
         else if (operator == '^' && !isOperand1Known) {
            double exponent = Math.pow(knownExpression.evaluate(), -1);
            UnaryExpression exponentExpression = new UnaryExpression(Double.toString(exponent));
            rightSide = new BinaryExpression(rightSide, exponentExpression, '^');
         }
         else {
            throw new IllegalArgumentException("ERROR: Invalid operator " + operator);
         }

      }
      return rightSide.evaluate();

   }

   public static double getPositiveQuadraticRoot(double a, double b, double c) {
      double[] roots = new double[2];
      roots[0] = ((b * -1) + Math.sqrt(Math.pow(b, 2) - 4 * a * c)) / (2 * a);
      roots[1] = ((b * -1) - Math.sqrt(Math.pow(b, 2) - 4 * a * c)) / (2 * a);
      Arrays.sort(roots);
      if (roots[1] > 0) {
         return roots[1];
      }
      throw new IllegalArgumentException("ERROR: Both values for possible time are negative");
   }
   
}