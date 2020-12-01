package mua.operation;

import mua.assets.Arguments;
import mua.value.*;
import mua.assets.Name;
import mua.assets.Namespace;

/**
 * thing [Name]
 * <p>
 * Return the value of the variable with given Name. Name should not contain
 * Word mark "
 */
class thing implements Operation {
    @Override
    public Value execute(Arguments args) {
        return execute(Operation.parseNameLabel(args));
    }

    Value execute(Name name) {
        return Namespace.get(name);
    }
}
