package mua.operation;

import mua.assets.Arguments;
import mua.assets.Namespace;
import mua.value.*;

/**
 * erall
 * 
 * <p>
 * Erase all variables from current namespace
 */
public class erall implements Operation {
    @Override
    public Value execute(Arguments args) throws RuntimeException {
        return execute();
    }

    public Value execute() {
        Namespace.eraseAll();
        return Bool.build("true");
    }
}
