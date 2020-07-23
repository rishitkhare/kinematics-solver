// This is a static class used to convert to the base units: meters & seconds
// It works by having an array of units and a parallel array of conversion factors, and uses dimensional analysis
// to convert between compound units, like m/s

import java.util.*;

public class UnitConversion {

    private UnitConversion displacementConversion;
    private UnitConversion timeConversion;

    private static final String[] validTimeUnits = {"ps", "ms", "jiffy", "s", "sec", "min", "h", "hr", "hrs", "day", "wk", "ftn", "mo", "yr"};
    private static final double[] convertToSeconds = {1E-12, 0.001, 0.01, 1, 1, 60, 3600, 3600, 3600, 86400, 604800, 1209600, 2.628E6, 3.154E7};
    private static final String[] validDisplacementUnits = {"in", "ft", "yd", "ftbl", "fm", "cbl", "fur", "mi", "nmi", "nm", "mm", "cm", "m", "km", "au", "lyr"};
    private static final double[] convertToMeters = {0.0254, 0.3048, 0.9144, 91.44, 1.8288, 185.2, 201.168, 1609.34, 1852, 1E-9, 0.001, 0.01, 1, 1000, 1.496E11, 9.461E15};

    private int conversionIndex;

    //prevents instantiation
    private UnitConversion() {}

    //takes ANY unit and finds a conversion factor to base units
    public static double unitToConversionFactor(String unit, int quantityIndex) {

        if (quantityIndex != getQuantityIndex(unit)) {
            throw new IllegalArgumentException("ERROR: Invalid unit: " + unit);
        }

        //if singular unit, skip the dimensional analysis
        if(! unit.contains("/")) {
            return singularUnitToConversionFactor(unit);
        }

        //essentially does dimensional analysis
        double numerator = 1;
        double denominator = 1;
        boolean denominatorsquared = false; //TODO : add a bool numeratorSquared for later equations (not necessary for kinematics)

        String numeratorUnit = unit.substring(0, unit.indexOf('/'));
        String denominatorUnit = unit.substring(unit.indexOf('/') + 1);

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
    private static double singularUnitToConversionFactor(String unit) {
        if (stringArrayContains(unit, validTimeUnits)) {
            return convertToSeconds[stringArrayIndexOf(unit, validTimeUnits)];
        }
        else if (stringArrayContains(unit, validDisplacementUnits)) {
            return convertToMeters[stringArrayIndexOf(unit, validDisplacementUnits)];
        }
        throw new IllegalArgumentException("not (yet) a valid unit: " + unit);
    }

    public String getUnits(String fullText) {
        return fullText.substring(fullText.indexOf(" ") + 1);
    }

    //some basic array methods that are for utility:

    //takes an array and checks if it contains an item (only works for String[])
    private static boolean stringArrayContains(String s, String[] array) {
        for(String item : array) {
            if(item.equals(s)) {
                return true;
            }
        }
        return false;
    }

    //finds the first index of a string in an array
    private static int stringArrayIndexOf(String s, String[] array) {
        for(int i = 0; i < array.length; i ++) {
            if(s.equals(array[i])) {
                return i;
            }
        }
        return -1;
    }

    // returns whether or not the given unit is valid acceleration, velocity, time, or displacement unit
    // Velocity = 1, Time = 2, Acceleration = 3, Displacement = 4
    public static int getQuantityIndex(String unit) {
        if (unit.contains("/")) {
            String[] fraction = unit.split("/");
            if (getQuantityIndex(fraction[0]) == 4 && getQuantityIndex(fraction[1]) == 2) {
                return 1;
            }
            else if (getQuantityIndex(fraction[0]) == 4 && getQuantityIndex(fraction[1]) == 3) {
                return 3;
            }
            else {
                throw new IllegalArgumentException("ERROR: Invalid unit: " + unit);
            }
        }
        else {
            if (stringArrayContains(unit, validDisplacementUnits)) {
                return 4; // isDisplacement
            }
            else if (stringArrayContains(unit, validTimeUnits)) {
                return 2; // isTime
            }
            else if (stringArrayContains(unit.substring(0, unit.indexOf("^2")), validTimeUnits)) {
                return 3; // isAcceleration
            }
            else {
                throw new IllegalArgumentException("ERROR: Invalid unit: " + unit);
            }
        }
    }

    //returns a table of possible units for the user
    public static String getValidUnits() {
        String[][] validDisplacementUnits = { {"inches", "in"},
                                              {"feet", "ft"},
                                              {"yards", "yd"},
                                              {"football fields", "ftbl"},
                                              {"fathoms", "fm"},
                                              {"cable length", "cbl"},
                                              {"furlongs", "fur"},
                                              {"miles", "mi"},
                                              {"nautical miles", "nmi"},
                                              {"nanometers", "nm"},
                                              {"millimeters", "mm"},
                                              {"centimeters", "cm"},
                                              {"meters", "m"},
                                              {"kilometers", "km"},
                                              {"astronomical units", "au"},
                                              {"light years", "lyr"} };
        String[][] validTimeUnits = { {"picoseconds", "ps"},
                                      {"milliseconds", "ms"},
                                      {"jiffies", "jiffy"},
                                      {"seconds", "s or sec"},
                                      {"minutes", "min"},
                                      {"hours", "h or hr(s)"},
                                      {"days", "day"},
                                      {"weeks", "wk"},
                                      {"fortnights", "ftn"},
                                      {"months", "mo"},
                                      {"years", "yr"} };

        String validUnits = "";
        validUnits += "Valid Displacement Units:" + "\n";
        for (int row = 0; row < validDisplacementUnits.length; row++) {
            validUnits += "\t" + validDisplacementUnits[row][0] + " - " + validDisplacementUnits[row][1] + "\n";
        }
        validUnits += "Valid Time Units:" + "\n";
        for (int row = 0; row < validTimeUnits.length; row++) {
            validUnits += "\t" + validTimeUnits[row][0] + " - " + validTimeUnits[row][1] + "\n";
        }
        return validUnits;
    }
}
