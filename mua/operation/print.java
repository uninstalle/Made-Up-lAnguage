package mua.operation;

import mua.assets.Arguments;
import mua.value.*;

/**
 * print [Value]
 * <p>
 * Print the value in the console
 */
class print implements Operation {
    @Override
    public Value execute(Arguments args) {
        return execute(Operation.parseValue(args));
    }

    Value execute(Value value) {
        System.out.println(value);
        return value;
    }
}
