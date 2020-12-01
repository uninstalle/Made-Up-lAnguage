package mua.operation;

import mua.assets.Arguments;
import mua.value.*;

/**
 * islist [Value]
 * <p>
 * Return if the value is List
 */
class islist implements Operation {
    @Override
    public Value execute(Arguments args) {
        return execute(Operation.parseValue(args));
    }

    Value execute(Value value) {
        return Bool.build(value.isList());
    }
}
