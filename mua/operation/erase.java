package mua.operation;

import mua.value.*;
import mua.Name;

class erase extends Operation {
    @Override
    Value execute(Arguments args) {
        return execute(parseName(args));
    }

    Value execute(Name name) {
        return Name.erase(name);
    }
}