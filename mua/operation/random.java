package mua.operation;

import mua.assets.Arguments;
import mua.value.*;

/**
 * random [Number]
 * 
 * <p>
 * Generate a random number in [0,Number)
 */
public class random implements Operation {
    @Override
    public Value execute(Arguments args) throws RuntimeException {
        mua.value.Number n = Operation.parseValue(args).toNumber();
        return execute(n);
    }

    public Value execute(mua.value.Number n) {
        return mua.value.Number.build(Double.toString(Math.random() * n.toDouble()));
    }
}
