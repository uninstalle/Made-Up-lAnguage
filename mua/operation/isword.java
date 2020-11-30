package mua.operation;

import mua.value.*;

/**
 * isword [Value]
 * <p>
 * Return if the value is Word
 */
class isword extends Operation {
    @Override
    Value execute(Arguments args) {
        return execute(parseValue(args));
    }

    Value execute(Value value) {
        return Bool.build(value.isWord());
    }
}
