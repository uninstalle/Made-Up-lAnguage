package mua.operation;

import mua.value.*;
import mua.Name;
import mua.Namespace;

/**
 * erase [Name]
 * <p>
 * Erase the variable with given name in current namespace, return the erased
 * value
 */
class erase extends Operation {
    @Override
    Value execute(Arguments args) {
        return execute(parseName(args));
    }

    Value execute(Name name) {
        return Namespace.erase(name);
    }
}