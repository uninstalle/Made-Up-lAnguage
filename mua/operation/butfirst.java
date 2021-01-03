package mua.operation;

import mua.assets.Arguments;
import mua.value.*;

/**
 * butfirst [Word/List]
 * 
 * <p>
 * Return all but the first character of a Word, or all but the first element of
 * a List
 */
public class butfirst implements Operation {
    @Override
    public Value execute(Arguments args) throws RuntimeException {
        Value v = Operation.parseValue(args);
        if (v.isList())
            return execute(v.toList());
        else
            return execute(v.toWord());
    }

    public Value execute(Word word) {
        return Word.build("\"" + word.toString().substring(1));
    }

    public Value execute(List list) {
        List l = List.build("[]");
        for (int i = 1; i < list.getElements().size(); ++i) {
            l.append(list.getElements().get(i));
        }
        return l;
    }
}
