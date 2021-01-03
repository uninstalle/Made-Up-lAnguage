package mua.operation;

import mua.assets.Arguments;
import mua.value.*;

/**
 * butlast [Word/List]
 * 
 * <p>
 * Return all but the last character of a Word, or all but the last element of a
 * List
 */
public class butlast implements Operation {
    @Override
    public Value execute(Arguments args) throws RuntimeException {
        Value v = Operation.parseValue(args);
        if (v.isList())
            return execute(v.toList());
        else
            return execute(v.toWord());
    }

    public Value execute(Word word) {
        return Word.build("\"" + word.toString().substring(0, word.toString().length() - 1));
    }

    public Value execute(List list) {
        List l = List.build("[]");
        for (int i = 0; i < (list.getElements().size() - 1); ++i) {
            l.append(list.getElements().get(i));
        }
        return l;
    }
}
