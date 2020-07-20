//Hisham, I think this class should be static. Creating a "UnitConversion" Object every time we convert units is just messy.

import java.util.*;

public class UnitConversion {

    private UnitConversion displacementConversion;
    private UnitConversion timeConversion;


    private static final String[] allValidUnits = {"ms", "s", "sec", "min", "h", "hr", "hrs", "in", "ft", "yd", "fur", "mi", "mm", "cm", "m", "km"};
    private static final double[] convertToBase = {0.001, 1, 1, 60, 3600, 3600, 3600, 0.0254, 0.3048, 0.9144, 201.168, 1609.34, 0.001, 0.01, 1, 1000};

    private static final String[][] allValidUnitsAGAIN = { {"in", "ft", "yd", "fm", "cbl", "furlong", "mi", "nmi", "nm", "mm", "cm", "m", "km", "au", "lyr"},
                                                           {"ps", "ms", "jiffy", "s", "sec", "min", "h", "hr", "hrs", "day", "wk", "ftn", "mo", "yr"} };

    //technically these aren't even used in the code, but we'll leave them here in case.
    private static final String[] validTimeUnits = {"ps", "ms", "jiffy", "s", "sec", "min", "h", "hr", "hrs", "day", "wk", "ftn", "mo", "yr"};
    private static final double[] convertToSeconds = {1E-12, 0.001, 0.01, 1, 1, 60, 3600, 3600, 3600, 86400, 604800, 1209600, 2.628E6, 3.154E7};
    private static final String[] validDisplacementUnits = {"in", "ft", "yd", "fm", "cbl", "furlong", "mi", "nmi", "nm", "mm", "cm", "m", "km", "au", "lyr"};
    private static final double[] convertToMeters = {0.0254, 0.3048, 0.9144, 1.8288, 185.2, 201.168, 1609.34, 1852, 1E-9, 0.001, 0.01, 1, 1000, 1.496E11, 9.461E15};

    private int conversionIndex;

    //prevents instantiation
    private UnitConversion() {}

    //Warning: as of now, nonsensical units that aren't acceleration or velocity will still be 'converted'

    //takes ANY unit and finds a conversion factor to base units
    public static double unitToConversionFactor(String unit, int quantityIndex) {

        if (quantityIndex != getQuantityIndex(unit)) {
            throw new IllegalArgumentException("ERROR: Invalid unit: " + unit);
        }

        //if singular unit, skip the dimensional analysis //TODO : Squared units will break this system (not necessary for kinematics)
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
            if (stringArrayContains(unit, allValidUnitsAGAIN[0])) {
                return 4; // isDisplacement
            }
            else if (stringArrayContains(unit, allValidUnitsAGAIN[1])) {
                return 2; // isTime
            }
            else if (stringArrayContains(unit.substring(0, unit.indexOf("^2")), allValidUnitsAGAIN[1])) {
                return 3; // isAcceleration
            }
            else {
                throw new IllegalArgumentException("ERROR: Invalid unit: " + unit);
            }
        }
    }

    public static String getValidUnits() {
        String[][] validDisplacementUnits = { {"inches", "in"},
                                              {"feet", "ft"},
                                              {"yards", "yd"},
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
