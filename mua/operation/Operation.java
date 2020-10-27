package mua.operation;

import mua.Name;
import mua.value.*;

// force to compile all operation class
import mua.operation.make;
import mua.operation.thing;
import mua.operation.print;
import mua.operation.read;
import mua.operation.Operator;
import mua.operation.erase;
import mua.operation.isname;
import mua.operation.run;
import mua.operation._if;
import mua.operation.isnumber;
import mua.operation.isword;
import mua.operation.islist;
import mua.operation.isbool;
import mua.operation.isempty;

public abstract class Operation {

    abstract Value execute(Arguments args) throws RuntimeException;

    // build operation via the op name
    static Operation build(String opname) {
        Operation op;
        try {
            Class<?> opClass = Class.forName("mua.operation." + opname);
            op = (Operation) opClass.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            return null;
        }
        return op;
    }

    public static Value parse(String str) {
        return parseValue(new Arguments(str));
    }

    public static Value parseValue(Arguments args) {
        String opname = args.nextToken();
        Operation op;

        op = Operation.build(opname);
        if (op != null)
            return op.execute(args);
        else
            return Value.build(opname);
    }

    static Name parseName(Arguments args) {
        String opname = args.nextToken();
        Operation op;

        // Try to build a operation first to avoid implement a keyword mechanic
        op = Operation.build(opname);
        if (op != null)
            return parseName(new Arguments(op.execute(args).toString()));
        else
            return Name.build(opname);
    }
}
