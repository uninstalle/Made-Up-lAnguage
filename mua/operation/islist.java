package mua.operation;

import mua.value.*;

/**
 * islist [Value]
 * <p>
 * Return if the value is List
 */
class islist extends Operation {
    @Override
    Value execute(Arguments args) {
        return execute(parseValue(args));
    }

    Value execute(Value value) {
        return Bool.build(value.isList());
    }
}
