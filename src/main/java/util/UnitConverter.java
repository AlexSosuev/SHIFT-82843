package util;

public class UnitConverter {
    public static double fahrenheitToCelsius(double f) {
        return (f - 32) * 5.0 / 9.0;
    }

    public static double knotsToMps(double knots) {
        return knots * 0.514444;
    }

    public static double inchesToMm(double inches) {
        return inches * 25.4;
    }

    public static double feetToMeters(double feet) {
        return feet * 0.3048;
    }
}