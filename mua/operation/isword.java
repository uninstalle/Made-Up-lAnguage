package mua.operation;

import mua.assets.Arguments;
import mua.value.*;

/**
 * isword [Value]
 * <p>
 * Return if the value is Word
 */
class isword implements Operation {
    @Override
    public Value execute(Arguments args) {
        return execute(Operation.parseValue(args));
    }

    Value execute(Value value) {
        return Bool.build(value.isWord());
    }
}
