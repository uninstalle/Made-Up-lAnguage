package mua.operation;

import mua.assets.Arguments;
import mua.value.*;
import mua.assets.Namespace;

/**
 * save [Word]
 * 
 * <p>
 * Load current namespace in file [Word], return true
 */
public class load implements Operation {
    @Override
    public Value execute(Arguments args) throws RuntimeException {
        return execute(Operation.parseValue(args).toWord());
    }

    public Value execute(Word filename) {
        if (Namespace.load(filename.toString()))
            return Bool.build(true);
        else {
            System.out.println("bbbb");
            return Bool.build(false);
        }
    }
}
