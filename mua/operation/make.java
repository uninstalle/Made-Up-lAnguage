package mua.operation;

import mua.value.*;
import mua.Name;
import mua.Namespace;

class make extends Operation {

    @Override
    Value execute(Arguments args) {
        return execute(parseName(args), parseValue(args));
    }

    Value execute(Name name, Value value) {
        if (Function.isFunction(value))
            Namespace.assignFunction(name.toString(), Function.build((List) value));
        else
            Namespace.assign(name, value);
        return value;
    }
}
