package mua.value;

import mua.assets.Infix;

/**
 * {@code Value} is the interface for all value in MUA. It has a set of useful static methods.
 */
public interface Value {

    /**
     * Build a {@code Value} from given string.
     * @param value a string that may contain a Value
     * @return {@code Value} object, or null if it has no valid value
     */
    public static Value build(String value) {
        value = value.trim();
        if (Infix.isInfix(value))
            return new Infix(value).getValue();
        if (Number.isNumber(value))
            return new Number(value);
        else if (Word.isWord(value))
            return new Word(value);
        else if (List.isList(value))
            return new List(value);
        else if (Bool.isBool(value))
            return new Bool(value);
        else
            return null;
    }

    default public boolean isNumber() {
        return this instanceof Number;
    }

    default public boolean isWord() {
        return this instanceof Word;
    }

    default public boolean isList() {
        return this instanceof List;
    }

    default public boolean isBool() {
        return this instanceof Bool;
    }

    default public boolean isFunction() {return this instanceof  Function;}

    /**
     * This will return the raw string of class {@code Value} stores.
     * @return raw string of the value
     */
    String toRawString();

}
