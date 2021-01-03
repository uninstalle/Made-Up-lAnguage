package mua.operation;

import mua.assets.Arguments;
import mua.value.*;
import mua.Main;

/**
 * readlist
 * <p>
 * Read a line from the stdin, create a List from this line whose elements are
 * from the line seperated by blank.
 */
class readlist implements Operation {
    @Override
    public Value execute(Arguments args) {
        return execute();
    }

    Value execute() {
        // add a blank for regexp matching
        String val = " " + Main.stdin.nextLine();
        val = val.replaceAll("\\s+", " ");
        // match all place between blank characters and non-blank characters and insert
        // a Word marker "
        val = val.replaceAll("(?<=\\s+)(?=\\S+)", "\"");
        val = "[" + val + "]";
        Value v = Value.build(val);
        if (v != null)
            return v;
        else
            return null;
    }
}
