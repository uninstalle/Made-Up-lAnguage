package mua;

abstract public class Value {
    static public void main(String[] args) {
        System.out.println(build("-1").getClass().getName());
        System.out.println(build("-1").toString());
        System.out.println(build("-114.0").getClass().getName());
        System.out.println(build("-114.0").toString());
        System.out.println(build("\"114514").getClass().getName());
        System.out.println(build("\"114514").toString());
        System.out.println(build("[1 2]").getClass().getName());
        System.out.println(build("[1 2]").toString());
        System.out.println(build("false").getClass().getName());
        System.out.println(build("false").toString());
        System.out.println(build("falsE").getClass().getName());
    }

    public static Value build(String value) throws RuntimeException {
        value = value.trim();
        if (Number.isNumber(value))
            return new Number(value);
        else if (Word.isWord(value))
            return new Word(value);
        else if (List.isList(value))
            return new List(value);
        else if (Bool.isBool(value))
            return new Bool(value);
        else
            throw new RuntimeException("Not a value");
    }

    public int toInt() throws ClassCastException {
        if (this instanceof Number)
            return ((Number) this).toInt();
        else
            throw new ClassCastException("Cannot convert value to integer");
    }

    public double toDouble() throws ClassCastException {
        if (this instanceof Number)
            return ((Number) this).toDouble();
        else
            throw new ClassCastException("Cannot convert value to double");
    }

    public boolean isNumber() {
        return this instanceof Number;
    }

    public boolean isWord() {
        return this instanceof Word;
    }

    public boolean isList() {
        return this instanceof List;
    }

    public boolean isBool() {
        return this instanceof Bool;
    }

}

class Number extends Value {
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

class Word extends Value {
    // Value of Word stores its mark "
    String value;

    public Word(String value) {
        this.value = value;
    }

    Number toNumber() throws ClassCastException {
        if (Number.isNumber(value.substring(1)))
            return new Number(value.substring(1));
        else
            throw new ClassCastException("Cannot convert word to number");
    }

    public int toInt() throws ClassCastException {
        return Integer.parseInt(toNumber().value);
    }

    public double toDouble() throws ClassCastException {
        return Double.parseDouble(toNumber().value);
    }

    public Bool toBool() throws ClassCastException {
        if (Bool.isBool(value.substring(1)))
            return new Bool(value.substring(1));
        else
            throw new ClassCastException("Cannot convert word to bool");
    }

    public static boolean isWord(String value) {
        return value.startsWith("\"");
    }

    @Override
    public String toString() {
        return value.substring(1);
    }

}

class List extends Value {
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
            if (level != 0)
                return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return value;
    }
}

class Bool extends Value {
    String value;

    public Bool(String value) {
        this.value = value;
    }

    public static boolean isBool(String value) {
        return value.equals("true") || value.equals("false");
    }

    @Override
    public String toString() {

        return value;
    }
}