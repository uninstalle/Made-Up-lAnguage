package mua.value;

import mua.assets.Infix;

/**
 * {@code Value} is the interface for all value in MUA. It has a set of useful static methods.
 */
public interface Value {

    static void main(String[] args) {
        Function f = Function.build(List.build("[[][]]"));
        System.out.println(f.toList());
    }

    /**
     * Build a {@code Value} from given string.
     *
     * @param value a string that may contain a Value
     * @return {@code Value} object, or null if it has no valid value
     */
    static Value build(String value) {
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

    default boolean isNumber() {
        return this instanceof Number;
    }

    default boolean isWord() {
        return this instanceof Word;
    }

    default boolean isList() {
        return this instanceof List;
    }

    default boolean isBool() {
        return this instanceof Bool;
    }

    default boolean isFunction() {
        return this instanceof Function;
    }

    default Number toNumber() {
        if (isNumber()) return (Number) this;
        else throw new ClassCastException("Cannot convert to Number");
    }

    default Word toWord() {
        if (isWord()) return (Word) this;
        else throw new ClassCastException("Cannot convert to Word");
    }

    default List toList() {
        if (isList()) return (List) this;
        else throw new ClassCastException("Cannot convert to List");
    }

    default Bool toBool() {
        if (isBool()) return (Bool) this;
        else throw new ClassCastException("Cannot convert to Bool");
    }

    default Function toFunction() {
        if(isFunction()) return (Function) this;
        else throw new ClassCastException("Cannot convert to Function");
    }

    /**
     * This will return the raw string of class {@code Value} stores.
     *
     * @return raw string of the value
     */
    String toRawString();

}
