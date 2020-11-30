package mua.operation;

import mua.value.*;

/**
 * print [Value]
 * <p>
 * Print the value in the console
 */
class print extends Operation {
    @Override
    Value execute(Arguments args) {
        return execute(parseValue(args));
    }

    Value execute(Value value) {
        System.out.println(value);
        return value;
    }
}
