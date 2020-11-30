package mua.operation;

import mua.Name;
import mua.Namespace;
import mua.value.Value;

/**
 * export [Name]
 * <p>
 * Export the variable inside a function namespace to global namespace, and
 * return the value, Export a function is supported, but cannot return the
 * function itself
 */
public class export extends Operation {
    @Override
    Value execute(Arguments args) {
        return execute(parseName(args));
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
