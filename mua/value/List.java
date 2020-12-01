package mua.value;

/**
 * This class contains a list value. It stores the raw string of the list.
 */
public class List implements Value {
    String value;

    List() {
        this.value = "";
    }

    List(String value) {
        this.value = value;
    }

    /**
     * Test if the string has a valid {@code List}. It should starts with '[', ends
     * with ']' and has the same number of '[' and ']'.
     *
     * @param value String that may contain a list
     * @return Whether the string contains a list
     */
    public static boolean isList(String value) {
        value = value.trim();
        if (!(value.startsWith("[") && value.endsWith("]")))
            return false;

        int level = 0;
        level += value.length() - value.replace("[", "").length();
        level -= value.length() - value.replace("]", "").length();

        return level == 0;
    }

    /**
     * Build a {@code List} from given string.
     *
     * @return {@code List} object, or null if it has no list object
     */
    public static List build(String value) {
        value = value.trim();
        if (List.isList(value))
            return new List(value);
        else
            return null;
    }

    public boolean isEmpty() {
        return value.equals("[]");
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public String toRawString() {
        return value;
    }
}
