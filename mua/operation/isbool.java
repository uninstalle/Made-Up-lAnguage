package mua.operation;

import mua.assets.Arguments;
import mua.value.*;

/**
 * isbool [Value]
 * <p>
 * Return if the value is Bool
 */
class isbool implements Operation {
    @Override
    public Value execute(Arguments args) {
        return execute(Operation.parseValue(args));
    }

    Value execute(Value value) {
        return Bool.build(value.isBool());
    }
}
