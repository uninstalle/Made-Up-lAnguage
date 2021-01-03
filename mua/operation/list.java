package mua.operation;

import mua.assets.Arguments;
import mua.value.*;

/**
 * sentence [Value] [Value]
 * 
 * <p>
 * Concatenate two values to a List.
 */
public class list implements Operation {
    @Override
    public Value execute(Arguments args) throws RuntimeException {
        return execute(Operation.parseValue(args), Operation.parseValue(args));
    }

    public Value execute(Value v1, Value v2) {
        List l = List.build("[]");
        l.append(v1);
        l.append(v2);
        return l;
    }
}
