package mua.operation;

import mua.value.*;

class isnumber extends Operation {
    @Override
    Value execute(Arguments args) {
        return execute(parseValue(args));
    }

    Value execute(Value value) {
        return Bool.build(value.isNumber());
    }
}
