package mua.operation;

import mua.assets.Arguments;
import mua.value.Value;

/**
 * return [Value]
 * <p>
 * Return the value as the result of a function. Parse the first value in the
 * args and set the rest args to empty string, return the first value
 */
public class _return implements Operation {
    @Override
    public Value execute(Arguments args) {
        Value v = Operation.parseValue(args);
        args.set("");
        return execute(v);
    }

    Value execute(Value value) {
        return value;
    }
}
