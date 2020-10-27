package mua.operation;

import mua.value.*;
import mua.Name;

class thing extends Operation {
    @Override
    Value execute(Arguments args) {
        return execute(parseName(args));
    }

    Value execute(Name name) {
        return Name.get(name);
    }
}
