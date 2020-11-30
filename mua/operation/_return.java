package mua.operation;

import mua.value.Value;

public class _return extends Operation{
    @Override
    Value execute(Arguments args) throws RuntimeException {
        Value v = parseValue(args);
        args.set("");
        return execute(v);
    }

    Value execute(Value value) {
        return value;
    }
}
