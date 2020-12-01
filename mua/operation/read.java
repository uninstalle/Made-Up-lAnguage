package mua.operation;

import mua.assets.Arguments;
import mua.value.*;
import mua.Main;

/**
 * read
 * <p>
 * Read a Number or Word from the stdin
 */
class read implements Operation {
    @Override
    public Value execute(Arguments args) {
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
