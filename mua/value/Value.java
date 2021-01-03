package mua.value;

import java.io.Serializable;

import mua.assets.Infix;

/**
 * {@code Value} is the interface for all values in MUA. It has a set of useful
 * static methods.
 */
public interface Value extends Serializable {

    public static void main(String[] args) {
        Value number = Value.build("-1234.5678");
        System.out.println(number);
        Value bool = Value.build("false");
        System.out.println(bool);
        Value word = Value.build("true");
        System.out.println(word.toRawString());
        Value list = Value.build("[1 2.0 -3.1 false \"abab]");
        System.out.println(list);
        Value infix = Value.build("((1+3)*5)");
        System.out.println(infix);
        Value function = Value.build("[[] [print 1]]");
        System.out.println(function);
    }

    /**
     * Build a {@code Value} from given string. The string may have blank in its
     * front and back, but should contain only one Value.
     *
     * @param value a string that may contain a Value
     * @return {@code Value} object, or null if it has no valid value
     */
    static Value build(String value) {
        value = value.trim();
        // Every Value Class has two ways to instantialize: Use static build method, or
        // use constructor. Build method will trim the string and check if the string is
        // valid, but constructor doesn't check. Here we have trimmed the string and
        // will check the string, thus we use the constructor.
        if (Function.isFunction(value))
            return new Function(value);
        else if (Infix.isInfix(value))
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

    default boolean isCodeBlock() {
        return this instanceof CodeBlock;
    }

    /**
     * Try to convert this Value to Number. If it is a Number, return itself.
     */
    default Number toNumber() throws ClassCastException {
        if (isNumber())
            return (Number) this;
        else
            throw new ClassCastException("This Value cannot convert to Number");
    }

    /**
     * Try to convert this Value to Word. If it is a Word, return itself.
     */
    default Word toWord() throws ClassCastException {
        if (isWord())
            return (Word) this;
        else
            throw new ClassCastException("This Value cannot convert to Word");
    }

    /**
     * Try to convert this Value to List. If it is a List, return itself.
     */
    default List toList() throws ClassCastException {
        if (isList())
            return (List) this;
        else
            throw new ClassCastException("This Value cannot convert to List");
    }

    /**
     * Try to convert this Value to Bool. If it is a Bool, return itself.
     */
    default Bool toBool() throws ClassCastException {
        if (isBool())
            return (Bool) this;
        else
            throw new ClassCastException("This Value cannot convert to Bool");
    }

    /**
     * Try to convert this Value to Function. If it is a Function, return itself.
     */
    default Function toFunction() throws ClassCastException {
        if (isFunction())
            return (Function) this;
        else
            throw new ClassCastException("This Value cannot convert to Function");
    }

    /**
     * Try to convert this Value to CodeBlock. If it is a CodeBlock, return itself.
     */
    default CodeBlock toCodeBlock() throws ClassCastException {
        if (isCodeBlock())
            return (CodeBlock) this;
        else
            throw new ClassCastException("This Value cannot convert to CodeBlock");
    }

    /**
     * This will return the raw string of class {@code Value} stores.
     *
     * @return raw string of the value
     */
    String toRawString();

}
