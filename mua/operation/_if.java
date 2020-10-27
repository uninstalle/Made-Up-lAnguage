package mua.operation;

import mua.value.*;

public class _if extends Operation {
    @Override
    Value execute(Arguments args) throws RuntimeException {
        Value v1 = parseValue(args);
        Value v2 = parseValue(args);
        Value v3 = parseValue(args);
        if (v1.isBool()&&v2.isList()&&v3.isList())
            return execute((Bool) v1, (List) v2, (List) v3);
        else
            return null;
    }

    Value execute(Bool b, List br1, List br2) {
        if (b.get())
            return new run().execute(br1);
        else
            return new run().execute(br2);
    }
}
