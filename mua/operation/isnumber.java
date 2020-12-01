package mua.operation;

import mua.assets.Arguments;
import mua.value.*;

/**
 * isnumber [Value]
 * <p>
 * Return if the value is Number or can be converted to Number
 */
class isnumber implements Operation {
    @Override
    public Value execute(Arguments args) {
        return execute(Operation.parseValue(args));
    }

    Value execute(Value value) {
        return Bool.build(value.isNumber());
    }
}
