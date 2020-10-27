package mua.operation;

import mua.value.*;

class run extends Operation {
    @Override
    Value execute(Arguments args) {
        Value v = parseValue(args);
        if (v.isList())
            return execute((List)v);
        else
            throw new RuntimeException("Type List expected in run");

    }

    Value execute(List list) {
        String op = list.toString().substring(1, list.toString().length() - 2);
        Value retval = Value.build("[]");
        while (!op.isEmpty())
            retval = parse(op);
        return retval;
    }
}