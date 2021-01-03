package mua.operation;

import java.util.Map;
import mua.assets.Arguments;
import mua.assets.Namespace;
import mua.value.*;

/**
 * poall
 * 
 * <p>
 * Return a List containing all variable names of current namespace
 */
public class poall implements Operation {
    @Override
    public Value execute(Arguments args) throws RuntimeException {
        return execute();
    }

    public Value execute() {
        Map<String, Value> m = Namespace.getMap();
        List l = List.build("[]");
        for (String str : m.keySet()) {
            l.append(Word.build(str));
        }
        return l;
    }
}
