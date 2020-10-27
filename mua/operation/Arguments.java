package mua.operation;

// Class Arguments serve as a wrapper of String

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