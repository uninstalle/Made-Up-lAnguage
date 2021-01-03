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

    /**
     * Test if the given string is a valid Word raw string
     */
    public static boolean isWord(String value) {
        return value.startsWith("\"");
    }

    /**
     * Test if this Word is a string of Number
     */
    @Override
    public boolean isNumber() {
        return Number.isNumber(value.substring(1));
    }

    /**
     * Test if this Word is a string of Bool
     */
    @Override
    public boolean isBool() {
        return Bool.isBool(value.substring(1));
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
    
    /**
     * Try to convert the {@code Word} to {@code Number}
     *
     * @return {@code Number} object built from the Word
     * @throws ClassCastException the value contains no valid number
     */
    @Override
    public Number toNumber() throws ClassCastException {
        if (isNumber())
            return new Number(value.substring(1));
        else
            throw new ClassCastException("Cannot convert Word to Number");
    }

    /**
     * Try to convert the {@code Word} to {@code Bool}
     *
     * @return {@code Bool} object built from the Word
     * @throws ClassCastException the value contains no valid bool
     */
    @Override
    public Bool toBool() throws ClassCastException {
        if (isBool())
            return new Bool(value.substring(1));
        else
            throw new ClassCastException("Cannot convert Word to Bool");
    }
}
