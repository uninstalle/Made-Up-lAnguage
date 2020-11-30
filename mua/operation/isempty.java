package mua.operation;

import mua.value.*;

/**
 * isempty [Word/List]
 * <p>
 * Return if the Word/List is empty
 */
class isempty extends Operation {
    @Override
    Value execute(Arguments args) {
        return execute(parseValue(args));
    }

    Value execute(Value value) {
        if (value.isWord())
            return Bool.build(((Word) value).isEmpty());
        else if (value.isList())
            return Bool.build(((List) value).isEmpty());
        else
            throw new IllegalArgumentException("Operation isempty expects Word or List as its argument");
    }
}
