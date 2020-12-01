package mua.operation;

import mua.assets.Arguments;
import mua.value.*;

/**
 * if [Bool] [List1] [List2]
 * <p>
 * if [Bool] = true, execute [List1], else execute [List2]
 */
public class _if implements Operation {
    @Override
    public Value execute(Arguments args) {
        Value v1 = Operation.parseValue(args);
        Value v2 = Operation.parseValue(args);
        Value v3 = Operation.parseValue(args);
        if (v1.isBool() && v2.isList() && v3.isList())
            return execute(v1.toBool(), v2.toList(), v3.toList());
        else
            throw new IllegalStateException("Illegal if operation");
    }

    Value execute(Bool b, List br1, List br2) {
        if (b.get())
            return new run().execute(br1);
        else
            return new run().execute(br2);
    }
}
