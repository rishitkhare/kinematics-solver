public class UnitConversion {
    private String time;
    private String displacement;

    private boolean isTime;
    private boolean isDisplacement;
    private boolean isVelocity;

    private UnitConversion displacementConversion;
    private UnitConversion timeConversion;

    private static final String[] validTimeUnits = {"ms", "s", "sec", "min", "h", "hr", "hrs"};
    private static final double[] convertToSeconds = {0.001, 1, 1, 60, 3600, 3600, 3600};

    private static final String[] validDisplacementUnits = {"in", "ft", "yd", "furlong", "mi", "mm", "cm", "m", "km"};
    private static final double[] convertToMeters = {0.0254, 0.3048, 0.9144, 201.168, 1609.34, 0.001, 0.01, 1, 1000};

    private int conversionIndex;

    public UnitConversion(String type) {
        System.out.println(type);
        String[] fraction = type.split("/");
        if (fraction.length == 2) {
            if (contains(validTimeUnits, fraction[1]) && contains(validDisplacementUnits, fraction[0])) {
                isVelocity = true;
                displacementConversion = new UnitConversion(fraction[0]);
                timeConversion = new UnitConversion(fraction[1]);
            }
            else {
                // isAcceleration = true;
            }
        }
        else if(contains(validTimeUnits, type)) {
            isTime = true;
            this.time = type;
            conversionIndex = getConversionIndex(validTimeUnits, type);
        }
        else if (contains(validDisplacementUnits, type)) {
            isDisplacement = true;
            this.displacement = type;
            conversionIndex = getConversionIndex(validDisplacementUnits, type);
        }
        else {
            // will add velocity and acceleration
            throw new IllegalArgumentException("ERROR: Invalid unit");
        }

    }

    public boolean contains(String[] units, String unit) {
        for (String value : units) {
            if (value.equalsIgnoreCase(unit)) {
                return true;
            }
        }
        return false;
    }

    public int getConversionIndex(String[] units, String unit) {
        for (int index = 0; index < units.length; index++) {
            if (units[index].equalsIgnoreCase(unit)) {
                return index;
            }
        }
        return -1;
    }

    public double toBaseUnit() {
        if (isTime) {
            return convertToSeconds[conversionIndex];
        }
        else if (isDisplacement) {
            return convertToMeters[conversionIndex];
        }
        else if (isVelocity) {
            return displacementConversion.toBaseUnit() / timeConversion.toBaseUnit();
        }
        else {
            // will add velocity and acceleration
            throw new IllegalArgumentException("ERROR: Invalid unit");
        }

    }

}
