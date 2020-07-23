import java.util.*;

public class Algebra {

   public static final String[] standardUnits = {"m/s", "m/s", "s", "m/s^2", "m"};

   //prevents instantiation
   private Algebra() {}

   public static boolean isEqualEquation(Expression leftSide, Expression rightSide) {
      return Math.abs(leftSide.evaluate() - rightSide.evaluate()) < 0.000000001;
   }

   public static boolean isEqualTestEquation(double actual, double expected) {
      if (actual == expected) {
         return true;
      }
      return Math.abs((actual - expected) / expected) < 1; // Less than 1% error
   }

   // checks if a String can be parsed as Double
   public static boolean isNumber(String component) {
      try {
         double num = Double.parseDouble(component);
         return true;
      }
      catch (Exception ex) {
         return false;
      }
   }

   // given a left and right side of an equation, a Steps object, and if the unknown is time (for special cases)
   public static void solveEquation(boolean isTime, Steps showYourWork, Expression l, Expression r, int unknownQuantityIndex) {

      //write down current step of equation
      showYourWork.addStep(l.toString() + " = " + r.toString());

      //if the unknown is on the right side, then flip them
      Expression rightSide = null;
      Expression leftSide = null;

      if (l.getIsKnown()) {
         // leftSide is known, rightSide is NOT known
         leftSide = r;
         rightSide = l;
      }
      else if (r.getIsKnown()) {
         // leftSide is unknown, right is (leave the equation as is)
         leftSide = l;
         rightSide = r;
      }
      else {
         //if there are variables on both sides, then something went wrong
         throw new IllegalArgumentException("ERROR: Both sides of equation contain an unknown value");
      }

      //add step to work
      showYourWork.addStep(leftSide + " = " + rightSide.evaluate());

      //essentially does algebra until the variable is isolated on one side
      while (leftSide instanceof BinaryExpression) {

         BinaryExpression binaryLeftSide = (BinaryExpression) leftSide;
         char operator = binaryLeftSide.getOperator();
         boolean isOperand1Known;
         Expression knownExpression;

         // change the left side by taking the unknown branch and isolating it
         if (binaryLeftSide.getOperand1().getIsKnown()) {
            knownExpression = binaryLeftSide.getOperand1();
            isOperand1Known = true;

            leftSide = ((BinaryExpression) leftSide).getOperand2();
         } else if (binaryLeftSide.getOperand2().getIsKnown()) {
            knownExpression = binaryLeftSide.getOperand2();
            isOperand1Known = false;

            leftSide = ((BinaryExpression) leftSide).getOperand1();

         } else {
            throw new IllegalArgumentException("ERROR: Neither expression in the leftside contains a known value");
         }

         // Change right side in accordance with what was removed from the left side
         if (operator == '+') {
            rightSide = new BinaryExpression(rightSide, knownExpression, '-');
         } else if (operator == '-') {
            if (isOperand1Known) {
               rightSide = new BinaryExpression(knownExpression, rightSide, '-');
            } else {
               rightSide = new BinaryExpression(rightSide, knownExpression, '+');
            }
         } else if (operator == '*') {
            rightSide = new BinaryExpression(rightSide, knownExpression, '/');
         } else if (operator == '/') {
            if (isOperand1Known) {
               rightSide = new BinaryExpression(knownExpression, rightSide, '/');
            } else {
               rightSide = new BinaryExpression(rightSide, knownExpression, '*');
            }
         } else if (operator == '^' && !isOperand1Known) {
            double exponent = Math.pow(knownExpression.evaluate(), -1);
            UnaryExpression exponentExpression = new UnaryExpression(Double.toString(exponent));
            rightSide = new BinaryExpression(rightSide, exponentExpression, '^');
         } else {
            throw new IllegalArgumentException("ERROR: Invalid operator " + operator);
         }

         showYourWork.addStep(leftSide + " = " + rightSide.evaluate());
      }
      showYourWork.setAnswer(rightSide.evaluate());

      //If we are solving for time, the answer cannot be negative

      if (rightSide.evaluate() < 0 && isTime) {
         throw new IllegalArgumentException("ERROR: Time cannot be negative");
      }
      showYourWork.replaceLastValue(leftSide + " = " + rightSide.evaluate() + " " + standardUnits[unknownQuantityIndex]);
   }


   //Quadratic equation for special case (AIR) (Also cannot return negative because isTime is guaranteed)
   public static void getPositiveQuadraticRoot(Steps showYourWork, double a, double b, double c) {
      showYourWork.addStep("Δt = (-b ± √(b^2 - 4ac) / 2a");
      showYourWork.addStep("Δt = (" + (-1 * b) + " ± √(" + Math.pow(b, 2) + " - " + (4 * a * c) + ") / " + (2 * a));
      double[] roots = new double[2];
      roots[0] = ((b * -1) + Math.sqrt(Math.pow(b, 2) - 4 * a * c)) / (2 * a);
      roots[1] = ((b * -1) - Math.sqrt(Math.pow(b, 2) - 4 * a * c)) / (2 * a);
      Arrays.sort(roots);
      if (roots[1] > 0) {
         showYourWork.addStep("Δt = " + roots[1]);
         showYourWork.setAnswer(roots[1]);
      }
      else {
         throw new IllegalArgumentException("ERROR: Both values for possible time are negative");
      }
   }
}