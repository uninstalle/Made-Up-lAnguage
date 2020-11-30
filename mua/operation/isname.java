package mua.operation;

import mua.value.*;
import mua.Namespace;

/**
 * isname [Word]
 * <p>
 * Return if a variable of the name given exists in current namespace
 */
class isname extends Operation {
    @Override
    Value execute(Arguments args) {
        Value v = parseValue(args);
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
