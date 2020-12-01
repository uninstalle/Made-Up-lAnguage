package mua.operation;

import mua.assets.Name;
import mua.assets.Namespace;
import mua.assets.Arguments;
import mua.value.Function;
import mua.value.Value;

/**
 * export [Name]
 * <p>
 * Export the variable inside a function namespace to global namespace, and
 * return the value, Export a function is supported, but cannot return the
 * function itself
 */
public class export implements Operation {
    @Override
    public Value execute(Arguments args) {
        return execute(Operation.parseName(args));
    }

    Value execute(Name name) {
        Function f = Namespace.getFunction(name.toString());
        Value v = Namespace.get(name);
        if (f != null) {
            Namespace.assignGlobalFunction(name.toString(), f);
            // TODO: Function is a type of Value?
            return null;
        } else if (v != null) {
            Namespace.assignGlobal(name, v);
            return v;
        } else
            return null;
    }
}
