package mua.operation;

import mua.assets.Arguments;
import mua.value.*;
import mua.assets.Name;
import mua.assets.Namespace;

/**
 * make [Name] [Value] or make [Name] [[List] [List]]
 * <p>
 * Assign a variable with the given Name with the given Value. The Name should
 * be a raw string with a Word mark "
 * <p>
 * Define a function with the given Name with the given code. The Name should be
 * a raw string with a Word mark ". The first sub List is parameters, and the
 * second is function body.
 */
class make implements Operation {

    @Override
    public Value execute(Arguments args) {
        return execute(Operation.parseName(args), Operation.parseValue(args));
    }

    Value execute(Name name, Value value) {
        if (Function.isFunction(value))
            Namespace.assignFunction(name.toString(), Function.build((List) value));
        else
            Namespace.assign(name, value);
        return value;
    }
}
