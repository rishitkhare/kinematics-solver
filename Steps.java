//This class records the algebraic work of the problem

import java.util.*;

public class Steps {
    private ArrayList<String> steps;
    private double answer;

    //constructor starts with the unchanged equation
    public Steps(String baseEquation) {
        steps = new ArrayList<String>();
        steps.add(baseEquation);
    }

    //add a step to the end of the list
    public void addStep(String newStep) {
        steps.add(newStep);
    }

    // Replaces the last value with the given parameter
    public void replaceLastValue(String value) {
        steps.set(steps.size() - 1, value);
    }

    //toString for printing
    public String toString() {
        String output = steps.get(0);

        for(int i = 1; i < steps.size(); i ++) {
            output += "\n" + steps.get(i);
        }
        return output;
    }

    //gets the most recent step. At the end of the problem, this will be the answer (Ex: a = 4.2)
    public String getLastStep() {
        return steps.get(steps.size() - 1);
    }

    //gets the answer but as a parsable double
    public String getNumericalAnswer() { return Double.toString(this.answer); }

    public void setAnswer(double answer) { this.answer = answer; }
}
