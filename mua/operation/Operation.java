package mua.operation;

import java.lang.reflect.*;

import mua.Main;
import mua.Name;
import mua.Value;

public abstract class Operation {
    static public void main(String[] args) {
        System.out.println(parseValue(new Arguments("make \"a read")));
        System.out.println(parseValue(new Arguments(":a")));
    }

    abstract Value execute(Arguments args) throws RuntimeException;

    // build operation via the op name
    static Operation build(String opname) throws ClassNotFoundException, NoSuchMethodException, InstantiationException,
            IllegalAccessException, InvocationTargetException {
        Class<?> opClass = Class.forName("mua.operation." + opname);
        Operation op = (Operation) opClass.getDeclaredConstructor().newInstance();
        return op;
    }

    public static Value parse(String str) {
        return parseValue(new Arguments(str));
    }

    public static Value parseValue(Arguments args) {
        String opname = args.nextToken();
        Operation op;

        try {
            op = Operation.build(opname);
            return op.execute(args);
        } catch (Exception e) {
            return Value.build(opname);
        }
    }

    static Name parseName(Arguments args) {
        String opname = args.nextToken();
        Operation op;

        try {
            // Try to build a operation first to avoid implement a keyword mechanic
            op = Operation.build(opname);
            return parseName(new Arguments(op.execute(args).toString()));
        } catch (Exception e) {
            return Name.build(opname);
        }
    }
}

class Arguments {
    String args;

    Arguments() {
        args = new String();
    }

    Arguments(String str) {
        args = str;
    }

    String get() {
        return args;
    }

    void set(String str) {
        args = str;
    }

    String nextSubStr() throws RuntimeException {
        String substr;
        if (args == null || args == "")
            throw new RuntimeException("Empty argument");
        if (args.contains(" ")) {
            substr = args.substring(0, args.indexOf(" "));
            args = args.substring(args.indexOf(" ") + 1);
        } else {
            substr = args;
            args = "";
        }
        return substr;
    }

    String nextToken() {
        String opname = nextSubStr();

        // creating list
        if (opname.startsWith("[")) {
            int level = 1;
            // special case []
            if (opname.endsWith("]"))
                level--;
            while (level != 0) {
                String nextArg = nextSubStr();
                opname += nextArg;
                if (nextArg.startsWith("["))
                    level++;
                if (nextArg.endsWith("]"))
                    level--;
            }
        }

        return opname;
    }
}

class make extends Operation {

    @Override
    Value execute(Arguments args) {
        return execute(parseName(args), parseValue(args));
    }

    Value execute(Name name, Value value) {
        try {
            Name.assign(name, value);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return value;
    }
}

class thing extends Operation {
    @Override
    Value execute(Arguments args) {
        return execute(parseName(args));
    }

    Value execute(Name name) {
        return Name.get(name);
    }
}

class print extends Operation {
    @Override
    Value execute(Arguments args) {
        return execute(parseValue(args));
    }

    Value execute(Value value) {
        System.out.println(value);
        return value;
    }
}

class read extends Operation {
    @Override
    Value execute(Arguments args) {
        return execute();
    }

    Value execute() {
        String val = Main.stdin.nextLine();
        try {
            return Value.build(val);
        } catch (Exception e) {
            try {
                return Value.build("\"" + val);
            } catch (Exception e2) {

            }
        }
        return null;
    }
}

class erase extends Operation {
    @Override
    Value execute(Arguments args) {
        return execute(parseName(args));
    }

    Value execute(Name name) {
        return Name.erase(name);
    }
}

class isname extends Operation {
    @Override
    Value execute(Arguments args) {
        Value v = parseValue(args);
        if (v.isWord())
            return execute(v.toString());
        else
            throw new RuntimeException("Type Word expected in isname");
    }

    Value execute(String name) {
        boolean isName = Name.get(name) != null;
        if (isName)
            return Value.build("true");
        else
            return Value.build("false");
    }
}

class run extends Operation {
    @Override
    Value execute(Arguments args) {
        Value v = parseValue(args);
        if (v.isList())
            return execute(v);
        else
            throw new RuntimeException("Type List expected in run");

    }
    
    Value execute(Value list) {
        //TODO:
        return null;
    }
}

abstract class Operator extends Operation {
    @Override
    Value execute(Arguments args) {
        return execute(parseValue(args), parseValue(args));
    }

    abstract Value execute(Value a, Value b);
}

class add extends Operator {

    @Override
    Value execute(Value a, Value b) {
        try {
            return Value.build(String.valueOf(a.toDouble() + b.toDouble()));
        } catch (Exception e) {
        }
        return null;
    }
}

class sub extends Operator {

    @Override
    Value execute(Value a, Value b) {
        try {
            return Value.build(String.valueOf(a.toDouble() - b.toDouble()));
        } catch (Exception e) {
        }
        return null;
    }
}

class mul extends Operator {

    @Override
    Value execute(Value a, Value b) {
        try {
            return Value.build(String.valueOf(a.toDouble() * b.toDouble()));
        } catch (Exception e) {
        }
        return null;
    }
}

class div extends Operator {

    @Override
    Value execute(Value a, Value b) {
        try {
            return Value.build(String.valueOf(a.toDouble() / b.toDouble()));
        } catch (Exception e) {
        }
        return null;
    }
}

class mod extends Operator {

    @Override
    Value execute(Value a, Value b) {
        try {
            return Value.build(String.valueOf(a.toDouble() % b.toDouble()));
        } catch (Exception e) {
        }
        return null;
    }
}

class eq extends Operator {

    @Override
    Value execute(Value a, Value b) {
        if ((a.isNumber() || a.isWord()) && (b.isNumber() || b.isWord()))
            try {
                // TODO: fix equals
                return Value.build(a.toDouble() == b.toDouble() ? "true" : "false");
            } catch (Exception e) {
            }
        return null;
    }
}
