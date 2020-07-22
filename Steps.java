//This class records the algebraic work of the problem

import java.util.*;

public class Steps {
    private ArrayList<String> steps;


    //constructor starts with the unchanged equation
    public Steps(String baseEquation) {
        steps = new ArrayList<String>();
        steps.add(baseEquation);
    }

    //add a step to the final thing
    public void addStep(String newStep) {
        steps.add(newStep);
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
}
