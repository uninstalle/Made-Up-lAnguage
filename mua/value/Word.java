package mua.value;

public class Word extends Value {
    // including type Word's mark "
    String value;

    public Word(String value) {
        this.value = value;
    }

    public Number toNumber() throws ClassCastException {
        if (Number.isNumber(value.substring(1)))
            return new Number(value.substring(1));
        else
            throw new ClassCastException("Cannot convert word to number");
    }

    public Bool toBool() throws ClassCastException {
        if (Bool.isBool(value.substring(1)))
            return new Bool(value.substring(1));
        else
            throw new ClassCastException("Cannot convert word to bool");
    }
    
    public static Word build(String value) {
        value = value.trim();
        if (Word.isWord(value))
            return new Word(value);
        else
            return null;
    }

    public static boolean isWord(String value) {
        return value.startsWith("\"");
    }

    public boolean isEmpty() {
        return value.equals("\"");
    }

    @Override
    public String toString() {
        return value.substring(1);
    }

    @Override
    public String toRawString() {
        return value;
    }
}
