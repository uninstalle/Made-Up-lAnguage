package mua.operation;

import mua.assets.Arguments;
import mua.value.*;

/**
 * isempty [Word/List]
 * <p>
 * Return if the Word/List is empty
 */
class isempty implements Operation {
    @Override
    public Value execute(Arguments args) {
        return execute(Operation.parseValue(args));
    }

    Value execute(Value value) {
        if (value.isWord())
            return Bool.build(value.toWord().isEmpty());
        else if (value.isList())
            return Bool.build(value.toList().isEmpty());
        else
            throw new IllegalArgumentException("Operation isempty expects Word or List as its argument");
    }
}
