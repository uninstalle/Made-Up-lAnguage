package mua.value;

/**
 * This class contains a boolean value. It can be true or false.
 */
public class Bool extends Value {
    Boolean value;

    Bool(String value) {
        if (value.equals("true"))
            this.value = true;
        else if (value.equals("false"))
            this.value = false;
        else
            throw new IllegalArgumentException("Trying building a Bool with illegal value:" + value);
    }

    public static boolean isBool(String value) {
        return value.equals("true") || value.equals("false");
    }

    /**
     * Build a {@code Bool} from given string.
     * 
     * @param value String contains a {@code Bool} value
     * @return {@code Bool} object, or null if the string contains no {@code Bool}
     *         value
     */
    public static Bool build(String value) {
        value = value.trim();
        if (Bool.isBool(value))
            return new Bool(value);
        else
            return null;
    }

    /**
     * Build a {@code Bool} from given Boolean
     * 
     * @param value Boolean value
     * @return {@code Bool} object
     */
    public static Bool build(Boolean value) {
        return new Bool(value.toString());
    }

    public boolean get() {
        return value;
    }

    @Override
    public String toString() {
        return value.toString();
    }

    @Override
    public String toRawString() {
        return value.toString();
    }
}