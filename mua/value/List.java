package mua.value;

public class List extends Value {
    String value;

    public List(String value) {
        this.value = value;
    }

    public static boolean isList(String value) {
        if (!(value.startsWith("[") && value.endsWith("]")))
            return false;
        int level = 0;
        for (String element : value.split(" ")) {
            if (element.startsWith("["))
                level++;
            if (element.endsWith("]"))
                level--;
        }
        if (level != 0)
            return false;
        return true;
    }

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
}
