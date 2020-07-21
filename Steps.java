import java.util.*;

public class Steps {
    private ArrayList<String> steps;

    public Steps(String baseEquation) {
        steps = new ArrayList<String>();
        steps.add(baseEquation);
    }

    public void addStep(String newStep) {
        steps.add(newStep);
    }

    public String toString() {
        String output = steps.get(0);

        for(int i = 1; i < steps.size(); i ++) {
            output += "\n" + steps.get(i);
        }
        return output;
    }

    public String getLastStep() {
        return steps.get(steps.size() - 1);
    }
}
