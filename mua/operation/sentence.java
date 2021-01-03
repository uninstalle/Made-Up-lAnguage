package mua.operation;

import mua.assets.Arguments;
import mua.value.*;

/**
 * sentence [Value] [Value]
 * 
 * <p>
 * Concatenate two values to a List. If the value is also a List, divide the
 * List into elements and join to the new List
 */
public class sentence implements Operation {
    @Override
    public Value execute(Arguments args) throws RuntimeException {
        return execute(Operation.parseValue(args), Operation.parseValue(args));
    }

    public Value execute(Value v1, Value v2) {
        List l = List.build("[]");
        if (v1.isList()) {
            List l1 = v1.toList();
            for (Value v : l1.getElements()) {
                l.append(v);
            }
        } else
            l.append(v1);
        if (v2.isList()) {
            List l2 = v2.toList();
            for (Value v : l2.getElements()) {
                l.append(v);
            }
        } else
            l.append(v2);
        return l;
    }
}
