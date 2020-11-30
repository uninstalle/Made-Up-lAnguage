package mua.operation;

import mua.value.*;

/**
 * isnumber [Value]
 * <p>
 * Return if the value is Number or can be converted to Number
 */
class isnumber extends Operation {
    @Override
    Value execute(Arguments args) {
        return execute(parseValue(args));
    }

    Value execute(Value value) {
        return Bool.build(value.isNumber() || value.isWord() && mua.value.Number.isNumber(((Word) value).toString()));
    }
}
