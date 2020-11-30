package mua.operation;

import mua.value.*;
import mua.value.Number;

/**
 * The abstract class for all operators of MUA. Assuming that all operators are
 * binary.
 */
abstract class Operator extends Operation {
    @Override
    Value execute(Arguments args) {
        return execute(parseValue(args), parseValue(args));
    }

    abstract Value execute(Value a, Value b);
}

/**
 * add [Value] [Value]
 */
class add extends Operator {

    @Override
    Value execute(Value a, Value b) {
        if (a.isWord())
            a = ((Word) a).toNumber();
        if (b.isWord())
            b = ((Word) b).toNumber();
        return execute((Number) a, (Number) b);
    }

    Value execute(Number a, Number b) {
        return Value.build(String.valueOf(a.toDouble() + b.toDouble()));
    }
}

/**
 * substract [Value] [Value]
 */
class sub extends Operator {

    @Override
    Value execute(Value a, Value b) {
        if (a.isWord())
            a = ((Word) a).toNumber();
        if (b.isWord())
            b = ((Word) b).toNumber();
        return execute((Number) a, (Number) b);
    }

    Value execute(Number a, Number b) {
        return Value.build(String.valueOf(a.toDouble() - b.toDouble()));
    }
}

/**
 * multiply [Value] [Value]
 */
class mul extends Operator {

    @Override
    Value execute(Value a, Value b) {
        if (a.isWord())
            a = ((Word) a).toNumber();
        if (b.isWord())
            b = ((Word) b).toNumber();
        return execute((Number) a, (Number) b);
    }

    Value execute(Number a, Number b) {
        return Value.build(String.valueOf(a.toDouble() * b.toDouble()));
    }
}

/**
 * divide [Value] [Value]
 */
class div extends Operator {

    @Override
    Value execute(Value a, Value b) {
        if (a.isWord())
            a = ((Word) a).toNumber();
        if (b.isWord())
            b = ((Word) b).toNumber();
        return execute((Number) a, (Number) b);
    }

    Value execute(Number a, Number b) {
        return Value.build(String.valueOf(a.toDouble() / b.toDouble()));
    }
}

/**
 * mod [Value] [Value]
 */
class mod extends Operator {

    @Override
    Value execute(Value a, Value b) {
        if (a.isWord())
            a = ((Word) a).toNumber();
        if (b.isWord())
            b = ((Word) b).toNumber();
        return execute((Number) a, (Number) b);
    }

    Value execute(Number a, Number b) {
        return Value.build(String.valueOf(a.toDouble() % b.toDouble()));
    }
}

/**
 * equal [Value] [Value]
 */
class eq extends Operator {

    @Override
    Value execute(Value a, Value b) {
        if (a.isWord() && b.isWord())
            return execute((Word) a, (Word) b);
        else {
            if (a.isWord())
                a = ((Word) a).toNumber();
            if (b.isWord())
                b = ((Word) b).toNumber();
            return execute((Number) a, (Number) b);
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
class gt extends Operator {

    @Override
    Value execute(Value a, Value b) {
        if (a.isWord() && b.isWord())
            return execute((Word) a, (Word) b);
        else {
            if (a.isWord())
                a = ((Word) a).toNumber();
            if (b.isWord())
                b = ((Word) b).toNumber();
            return execute((Number) a, (Number) b);
        }
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
class lt extends Operator {

    @Override
    Value execute(Value a, Value b) {
        if (a.isWord() && b.isWord())
            return execute((Word) a, (Word) b);
        else {
            if (a.isWord())
                a = ((Word) a).toNumber();
            if (b.isWord())
                b = ((Word) b).toNumber();
            return execute((Number) a, (Number) b);
        }
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
class and extends Operator {

    @Override
    Value execute(Value a, Value b) {
        return execute((Bool) a, (Bool) b);
    }

    Value execute(Bool a, Bool b) {
        return Bool.build(a.get() && b.get());
    }
}

/**
 * or [Value] [Value]
 */
class or extends Operator {

    @Override
    Value execute(Value a, Value b) {
        return execute((Bool) a, (Bool) b);
    }

    Value execute(Bool a, Bool b) {
        return Bool.build(a.get() || b.get());
    }
}

/**
 * not [Value]
 */
class not extends Operator {
    @Override
    Value execute(Arguments args) {
        return execute(parseValue(args), null);
    }

    @Override
    Value execute(Value a, Value b) {
        // b is a dummy argument
        return execute((Bool) a);
    }

    Value execute(Bool a) {
        return Bool.build(!a.get());
    }
}
