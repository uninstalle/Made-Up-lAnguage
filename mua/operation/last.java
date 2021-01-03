package mua.operation;

import mua.assets.Arguments;
import mua.value.*;

/**
 * last [Word/List]
 * 
 * <p>
 * Return the last character of a Word, or the last element of a List
 */
public class last implements Operation {
    @Override
    public Value execute(Arguments args) throws RuntimeException {
        Value v = Operation.parseValue(args);
        if (v.isList())
            return execute(v.toList());
        else
            return execute(v.toWord());
    }

    public Value execute(Word word) {
        return Word.build("\"" + word.toString().substring(word.toRawString().length() - 1));
    }

    public Value execute(List list) {
        return list.getElements().get(list.getElements().size() - 1);
    }
}
