package mua.operation;

import mua.assets.Arguments;
import mua.value.*;

/**
 * word [Word] [Word/Number/Bool]
 * <p>
 * concatenate the first Word and the second Value to a new Word
 */
public class word implements Operation {
    @Override
    public Value execute(Arguments args) throws RuntimeException {

        return execute(Operation.parseValue(args).toWord(), Operation.parseValue(args));
    }

    public Value execute(Word word, Value value) {
        return Word.build(word.toRawString() + value.toString());
    }
}
