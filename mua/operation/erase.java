package mua.operation;

import mua.assets.Arguments;
import mua.value.*;
import mua.assets.Name;
import mua.assets.Namespace;

/**
 * erase [Name]
 * <p>
 * Erase the variable with given name in current namespace, return the erased
 * value
 */
class erase implements Operation {
    @Override
    public Value execute(Arguments args) {
        return execute(Operation.parseName(args));
    }

    Value execute(Name name) {
        return Namespace.erase(name);
    }
}