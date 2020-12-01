package mua.value;

/**
 * This class contains a word object. It stores the word mark " too, but
 * discards it when converting to string.
 */
public class Word implements Value {
    // including type Word's mark "
    String value;

    public Word(String value) {
        this.value = value;
    }

    /**
     * Try to convert the {@code Word} to {@code Number}
     * 
     * @return {@code Number} object built from the Word
     * @throws ClassCastException the value contains no valid number
     */
    public Number toNumber() {
        if (Number.isNumber(value.substring(1)))
            return new Number(value.substring(1));
        else
            throw new ClassCastException("Cannot convert word to number");
    }

    /**
     * Try to convert the {@code Word} to {@code Bool}
     * 
     * @return {@code Bool} object built from the Word
     * @throws ClassCastException the value contains no valid bool
     */
    public Bool toBool() {
        if (Bool.isBool(value.substring(1)))
            return new Bool(value.substring(1));
        else
            throw new ClassCastException("Cannot convert word to bool");
    }

    /**
     * Build a {@code Word} object from given string
     * 
     * @param value String that may contains a Word
     * @return {@code Word} object, or null if it contains no Word
     */
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

    /**
     * Return the raw value of this object. It contains a Word mark "
     */
    @Override
    public String toRawString() {
        return value;
    }
}
