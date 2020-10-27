package mua.operation;

import mua.value.*;
import mua.Name;

class make extends Operation {

    @Override
    Value execute(Arguments args) {
        return execute(parseName(args), parseValue(args));
    }

    Value execute(Name name, Value value) {
        try {
            Name.assign(name, value);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return value;
    }
}
