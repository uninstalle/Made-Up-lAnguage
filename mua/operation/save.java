package mua.operation;

import mua.assets.Arguments;
import mua.value.*;
import mua.assets.Namespace;

/**
 * save [Word]
 * 
 * <p>
 * Save current namespace in file [Word], return the file name
 */
public class save implements Operation {
    @Override
    public Value execute(Arguments args) throws RuntimeException {
        return execute(Operation.parseValue(args).toWord());
    }

    public Value execute(Word filename) {
        Namespace.save(filename.toString());
        return filename;
    }
}
