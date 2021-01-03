package mua.operation;

import mua.assets.Arguments;
import mua.value.*;

/**
 * join [List] [Value]
 * 
 * <p>
 * Append the Value to the List.
 */
public class join implements Operation {
    @Override
    public Value execute(Arguments args) throws RuntimeException {
        return execute(Operation.parseValue(args).toList(), Operation.parseValue(args));
    }

    public Value execute(List list, Value value) {
        List l = List.build("[]");
        for (Value v : list.getElements())
            l.append(v);
        l.append(value);
        return l;
    }
}
