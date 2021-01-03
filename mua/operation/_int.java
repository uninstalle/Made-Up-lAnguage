package mua.operation;

import mua.assets.Arguments;
import mua.value.*;

/**
 * int [Number]
 * 
 * <p>
 * Return the floor of the number
 */
public class _int implements Operation {
    @Override
    public Value execute(Arguments args) throws RuntimeException {
        mua.value.Number n = Operation.parseValue(args).toNumber();
        return execute(n);
    }

    public Value execute(mua.value.Number n) {
        return mua.value.Number.build(Double.toString(Math.floor(n.toDouble())));
    }
}
