package mua.value;

/**
 * This class contains a number value. It can be integer or float, but always convert to float in calculations.
 */
public class Number implements Value {
    String value;

    Number(String value) {
        this.value = value;
    }

    public static boolean isNumber(String value) {
        return isInteger(value) || isFloat(value);
    }

    static boolean isInteger(String value) {
        return value.matches("^-?\\d+");
    }

    static boolean isFloat(String value) {
        return value.matches("^(-?\\d+)?\\.\\d+");
    }

    /**
     * Build a {@code Number} from given string.
     * @param value String that may contain a {@code Number}
     * @return {@code Number} object, or null if it has no valid {@code Number}
     */
    public static Number build(String value) {
        value = value.trim();
        if (Number.isNumber(value))
            return new Number(value);
        else
            return null;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public String toRawString() {
        return value;
    }

    public int toInt() {
        return Integer.parseInt(value);
    }

    public double toDouble() {
        return Double.parseDouble(value);
    }
}
