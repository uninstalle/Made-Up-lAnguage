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
 * Or define a function with the given Name with the given code. The Name should
 * be a raw string with a Word mark ". Function is similar to List. It is
 * surrounded by a pair of [], and has two objects. The first is a parameter
 * list(not List type), and the second is a CodeBlock containing the function
 * body.
 */
class make implements Operation {

    @Override
    public Value execute(Arguments args) {
        return execute(Operation.parseName(args), Operation.parseValue(args));
    }

    Value execute(Name name, Value value) {
        Namespace.assign(name, value);
        return value;
    }
}
