package mua.operation;

import mua.assets.Arguments;
import mua.value.*;

/**
 * run [List]
 * <p>
 * Run a code segment inside the List, and return the last value of the code
 * segment.
 */
class run implements Operation {
    @Override
    public Value execute(Arguments args) {
        Value v = Operation.parseValue(args);
        if (v.isList())
            return execute(v.toList());
        else
            throw new IllegalArgumentException("Operation run expects List as its argument");

    }

    Value execute(List list) {
        Value retVal = Value.build("[]");
        if (list.isEmpty())
            return retVal;
        String op = list.getElements().get(0).toString();
        Arguments args = new Arguments(op);
        while (!args.isEmpty())
            retVal = Operation.parse(args);
        return retVal;
    }
}