package mua.operation;

import mua.value.*;
import mua.Name;
import mua.Namespace;

/**
 * thing [Name]
 * <p>
 * Return the value of the variable with given Name. Name should not contain
 * Word mark "
 */
class thing extends Operation {
    @Override
    Value execute(Arguments args) {
        return execute(parseNameLabel(args));
    }

    Value execute(Name name) {
        return Namespace.get(name);
    }
}
