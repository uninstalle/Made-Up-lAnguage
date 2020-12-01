package mua.operation;

import mua.assets.Arguments;
import mua.value.*;
import mua.assets.Namespace;

/**
 * isname [Word]
 * <p>
 * Return if a variable of the name given exists in current namespace
 */
class isname implements Operation {
    @Override
    public Value execute(Arguments args) {
        Value v = Operation.parseValue(args);
        if (v.isWord())
            return execute((Word) v);
        else
            throw new IllegalArgumentException("Operation isname expects Word as its argument");
    }

    Value execute(Word name) {
        boolean isName = Namespace.get(name.toString()) != null;
        return Bool.build(isName);
    }
}
