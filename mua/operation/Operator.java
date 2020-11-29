package mua.operation;

import mua.value.*;
import mua.value.Number;

/**
 * Class for all operators. Assuming that all operators are binary.
 */
abstract class Operator extends Operation {
    @Override
    Value execute(Arguments args) {
        return execute(parseValue(args), parseValue(args));
    }

    abstract Value execute(Value a, Value b);
}

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

class and extends Operator {

    @Override
    Value execute(Value a, Value b) {
        return execute((Bool) a, (Bool) b);
    }

    Value execute(Bool a, Bool b) {
        return Bool.build(a.get() & b.get());
    }
}

class or extends Operator {

    @Override
    Value execute(Value a, Value b) {
        return execute((Bool) a, (Bool) b);
    }

    Value execute(Bool a, Bool b) {
        return Bool.build(a.get() | b.get());
    }
}

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
