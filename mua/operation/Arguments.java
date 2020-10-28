package mua.operation;

// Class Arguments serve as a wrapper of String

public class Arguments {
    String args;

    Arguments() {
        args = new String();
    }

    public Arguments(String str) {
        args = str.trim();
    }

    public String get() {
        return args;
    }

    public void set(String str) {
        args = str;
    }

    public String nextSubStr() throws RuntimeException {
        String substr;
        if (args == null || args == "")
            throw new RuntimeException("Empty argument");
        if (args.contains(" ")) {
            substr = args.substring(0, args.indexOf(" "));
            args = args.substring(args.indexOf(" ") + 1).trim();
        } else {
            substr = args;
            args = "";
        }
        return substr;
    }

    public String peekNextSubStr() throws RuntimeException {
        String substr;
        if (args == null || args == "")
            throw new RuntimeException("Empty argument");
        if (args.contains(" ")) {
            substr = args.substring(0, args.indexOf(" "));
        } else {
            substr = args;
        }
        return substr;
    }

    public boolean isEmpty() {
        return args == null || args.equals("");
    }

    public String nextToken() {
        String opname = nextSubStr();

        // creating list
        if (opname.startsWith("[")) {
            int level = 1;
            // special case []
            if (opname.endsWith("]"))
                level--;
            while (level != 0) {
                String nextArg = " " + nextSubStr();
                opname += nextArg;
                if (nextArg.startsWith("["))
                    level++;
                if (nextArg.endsWith("]"))
                    level--;
            }
        }
        // creating infix exp
        else if (opname.startsWith("(")) {
            int level = 0;
            level += opname.length() - opname.replaceAll("\\(", "").length();
            level -= opname.length() - opname.replaceAll("\\)", "").length();

            while (level != 0) {
                String nextArg = " " + nextSubStr();
                opname += nextArg;
                level += nextArg.length() - nextArg.replaceAll("\\(", "").length();
                level -= nextArg.length() - nextArg.replaceAll("\\)", "").length();
            }
        }

        return opname;
    }
}