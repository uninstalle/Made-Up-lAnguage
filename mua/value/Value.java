package mua.value;

import mua.Infix;

abstract public class Value {

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
