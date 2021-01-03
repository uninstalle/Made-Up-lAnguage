package mua.operation;

import mua.assets.Arguments;
import mua.value.*;
import mua.value.Number;

/**
 * The interface for all operators of MUA. Assuming that all operators are
 * binary.
 */
interface Operator extends Operation {
    @Override
    default Value execute(Arguments args) {
        return execute(Operation.parseValue(args), Operation.parseValue(args));
    }

    Value execute(Value a, Value b);
}

/**
 * add [Value] [Value]
 */
class add implements Operator {

    @Override
    public Value execute(Value a, Value b) {
        return execute(a.toNumber(), b.toNumber());
    }

    Value execute(Number a, Number b) {
        return Value.build(String.valueOf(a.toDouble() + b.toDouble()));
    }
}

/**
 * subtract [Value] [Value]
 */
class sub implements Operator {

    @Override
    public Value execute(Value a, Value b) {
        return execute(a.toNumber(), b.toNumber());
    }

    Value execute(Number a, Number b) {
        return Value.build(String.valueOf(a.toDouble() - b.toDouble()));
    }
}

/**
 * multiply [Value] [Value]
 */
class mul implements Operator {

    @Override
    public Value execute(Value a, Value b) {
        return execute(a.toNumber(), b.toNumber());
    }

    Value execute(Number a, Number b) {
        return Value.build(String.valueOf(a.toDouble() * b.toDouble()));
    }
}

/**
 * divide [Value] [Value]
 */
class div implements Operator {

    @Override
    public Value execute(Value a, Value b) {
        return execute(a.toNumber(), b.toNumber());
    }

    Value execute(Number a, Number b) {
        return Value.build(String.valueOf(a.toDouble() / b.toDouble()));
    }
}

/**
 * mod [Value] [Value]
 */
class mod implements Operator {

    @Override
    public Value execute(Value a, Value b) {
        return execute(a.toNumber(), b.toNumber());
    }

    Value execute(Number a, Number b) {
        return Value.build(String.valueOf(a.toDouble() % b.toDouble()));
    }
}

/**
 * equal [Value] [Value]
 */
class eq implements Operator {

    @Override
    public Value execute(Value a, Value b) {
        if (a.isWord() && b.isWord())
            return execute(a.toWord(), b.toWord());
        else
            try {
                return execute(a.toNumber(), b.toNumber());
            } catch (ClassCastException e) {
                return Bool.build("false");
            }
    }

    Value execute(Number a, Number b) {
        return Value.build(String.valueOf(a.toDouble() == b.toDouble()));
    }

    Value execute(Word a, Word b) {
        return Value.build(String.valueOf(a.toString().equals(b.toString())));
    }
}

/**
 * greater [Value] [Value]
 */
class gt implements Operator {

    @Override
    public Value execute(Value a, Value b) {
        if (a.isWord() && b.isWord())
            return execute(a.toWord(), b.toWord());
        else
            return execute(a.toNumber(), b.toNumber());
    }

    Value execute(Number a, Number b) {
        return Value.build(String.valueOf(a.toDouble() > b.toDouble()));
    }

    Value execute(Word a, Word b) {
        return Value.build(String.valueOf(a.toString().compareTo(b.toString()) > 0));
    }
}

/**
 * less than [Value] [Value]
 */
class lt implements Operator {

    @Override
    public Value execute(Value a, Value b) {
        if (a.isWord() && b.isWord())
            return execute(a.toWord(), b.toWord());
        else
            return execute(a.toNumber(), b.toNumber());
    }

    Value execute(Number a, Number b) {
        return Value.build(String.valueOf(a.toDouble() < b.toDouble()));
    }

    Value execute(Word a, Word b) {
        return Value.build(String.valueOf(a.toString().compareTo(b.toString()) < 0));
    }
}

/**
 * and [Value] [Value]
 */
class and implements Operator {

    @Override
    public Value execute(Value a, Value b) {
        return execute(a.toBool(), b.toBool());
    }

    Value execute(Bool a, Bool b) {
        return Bool.build(a.get() && b.get());
    }
}

/**
 * or [Value] [Value]
 */
class or implements Operator {

    @Override
    public Value execute(Value a, Value b) {
        return execute(a.toBool(), b.toBool());
    }

    Value execute(Bool a, Bool b) {
        return Bool.build(a.get() || b.get());
    }
}

/**
 * not [Value]
 */
class not implements Operator {
    @Override
    public Value execute(Arguments args) {
        return execute(Operation.parseValue(args), null);
    }

    @Override
    public Value execute(Value a, Value b) {
        // b is a dummy argument
        return execute(a.toBool());
    }

    Value execute(Bool a) {
        return Bool.build(!a.get());
    }
}
