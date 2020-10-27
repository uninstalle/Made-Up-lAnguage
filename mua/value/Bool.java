package mua.value;

public class Bool extends Value {
    String value;

    public Bool(String value) {
        this.value = value;
    }

    public static boolean isBool(String value) {
        return value.equals("true") || value.equals("false");
    }

    public static Bool build(String value) {
        value = value.trim();
        if (Bool.isBool(value))
            return new Bool(value);
        else
            return null;
    }

    public static Bool build(Boolean value) {
        if (value)
            return new Bool("true");
        else
            return new Bool("false");
    }

    public boolean get() {
        return value.equals("true");
    }

    @Override
    public String toString() {
        return value;
    }
}