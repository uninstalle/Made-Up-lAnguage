package mua.operation;

import mua.value.*;

/**
 * isbool [Value]
 * <p>
 * Return if the value is Bool
 */
class isbool extends Operation {
    @Override
    Value execute(Arguments args) {
        return execute(parseValue(args));
    }

    Value execute(Value value) {
        return Bool.build(value.isBool());
    }
}
