//Hisham, I think this class should be static. Creating a "UnitConversion" Object every time we convert units is just messy.

import java.util.*;

public class UnitConversion {
    private String time;
    private String displacement;

    private boolean isTime;
    private boolean isDisplacement;
    private boolean isVelocity;

    private UnitConversion displacementConversion;
    private UnitConversion timeConversion;

    private static final String[] allValidUnits = {"ms", "s", "sec", "min", "h", "hr", "hrs", "in", "ft", "yd", "furlong", "mi", "mm", "cm", "m", "km"};
    private static final double[] convertToBase = {0.001, 1, 1, 60, 3600, 3600, 3600, 0.0254, 0.3048, 0.9144, 201.168, 1609.34, 0.001, 0.01, 1, 1000};

    //technically these aren't even used in the code, but we'll leave them here in case.
    private static final String[] validTimeUnits = {"ms", "s", "sec", "min", "h", "hr", "hrs"};
    private static final double[] convertToSeconds = {0.001, 1, 1, 60, 3600, 3600, 3600};
    private static final String[] validDisplacementUnits = {"in", "ft", "yd", "furlong", "mi", "mm", "cm", "m", "km"};
    private static final double[] convertToMeters = {0.0254, 0.3048, 0.9144, 201.168, 1609.34, 0.001, 0.01, 1, 1000};

    private int conversionIndex;

    //prevents instantiation
    private UnitConversion() {}

    //Warning: as of now, nonsensical units that aren't acceleration or velocity will still be 'converted'

    //takes ANY unit and finds a conversion factor to base units
    public static double unitToConversionFactor(String unit) {
        //if singular unit, skip the dimensional analysis //TODO : Squared units will break this system (not necessary for kinematics)
        if(! unit.contains("/")) {
            return singularUnitToConversionFactor(unit);
        }

        //essentially does dimensional analysis
        double numerator = 1;
        double denominator = 1;
        boolean denominatorsquared = false; //TODO : add a bool numeratorSquared for later equations (not necessary for kinematics)

        String numeratorUnit = unit;
        String denominatorUnit = "";

        numeratorUnit = unit.substring(0, unit.indexOf('/'));
        denominatorUnit = unit.substring(unit.indexOf('/') + 1);

        //if unit is acceleration
        if(denominatorUnit.contains("^2")) {
            denominatorsquared = true;
            denominatorUnit = denominatorUnit.substring(0, denominatorUnit.length() - 2);
        }

        //actually setting the conversion factors
        numerator = singularUnitToConversionFactor(numeratorUnit);
        denominator = singularUnitToConversionFactor(denominatorUnit);

        //acts accordingly if unit is acceleration
        if(denominatorsquared) {
            denominator = Math.pow(denominator, 2);
        }

        return numerator / denominator;
    }


    //method will only work on singular units of displacement or time
    public static double singularUnitToConversionFactor(String unit) {
        if(! stringArrayContains(unit, allValidUnits)) {
            throw new IllegalArgumentException("not (yet) a valid unit");
        }

        return convertToBase[stringArrayIndexOf(unit, allValidUnits)];
    }

    public String getUnits(String fullText) {
        return fullText.substring(fullText.indexOf(" ") + 1);
    }

    //why do these methods not exist in the Arrays class???? (.contains(), .indexOf())

    private static boolean stringArrayContains(String s, String[] array) {
        for(String item : array) {
            if(item.equals(s)) {
                return true;
            }
        }

        return false;
    }

    private static int stringArrayIndexOf(String s, String[] array) {
        for(int i = 0; i < array.length; i ++) {
            if(s.equals(array[i])) {
                return i;
            }
        }

        return -1;
    }


}
