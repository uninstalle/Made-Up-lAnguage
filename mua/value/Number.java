package mua.value;

public class Number extends Value {
    String value;

    public Number(String value) {
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

    public int toInt() {
        return Integer.parseInt(value);
    }

    public double toDouble() {
        return Double.parseDouble(value);
    }
}
