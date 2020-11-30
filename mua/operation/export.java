package mua.operation;

import mua.Name;
import mua.Namespace;
import mua.value.Value;

public class export extends Operation {
    @Override
    Value execute(Arguments args) throws RuntimeException {
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
