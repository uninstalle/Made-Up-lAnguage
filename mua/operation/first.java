package mua.operation;

import mua.assets.Arguments;
import mua.value.*;

/**
 * first [Word/List]
 * 
 * <p>
 * Return the first character of a Word, or the first element of a List
 */
public class first implements Operation {
    @Override
    public Value execute(Arguments args) throws RuntimeException {
        Value v = Operation.parseValue(args);
        if (v.isList())
            return execute(v.toList());
        else
            return execute(v.toWord());
    }

    public Value execute(Word word) {
        return Word.build("\"" + word.toString().substring(0, 1));
    }

    public Value execute(List list) {
        return list.getElements().get(0);
    }
}
