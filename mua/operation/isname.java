package mua.operation;

import mua.value.*;
import mua.Name;
import mua.Namespace;

class isname extends Operation {
    @Override
    Value execute(Arguments args) {
        Value v = parseValue(args);
        if (v.isWord())
            return execute((Word) v);
        else
            throw new RuntimeException("Type Word expected in isname");
    }

    Value execute(Word name) {
        boolean isName = Namespace.get(name.toString()) != null;
        return Bool.build(isName);
    }
}
