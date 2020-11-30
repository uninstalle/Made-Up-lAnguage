package mua.operation;

import mua.value.*;
import mua.Main;

/**
 * read
 * <p>
 * Read a Number or Word from the stdin
 */
class read extends Operation {
    @Override
    Value execute(Arguments args) {
        return execute();
    }

    Value execute() {
        String val = Main.stdin.nextLine();
        Value v = Value.build(val);
        if (v != null)
            return v;
        else
            return Value.build("\"" + val);
    }
}
