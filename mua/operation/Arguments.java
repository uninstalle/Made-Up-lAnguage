package mua.operation;

import mua.Main;

/**
 * A wrapper of command string.
 *
 * <p>
 * This class stores the command string. Since the recursive parsing is
 * operating at one string, simply taking {@code String} as a parameter cannot
 * make the caller function get the modified command string.
 * </p>
 * <p>
 * The class also has some useful methods like getting a token.
 * </p>
 */
public class Arguments {
    String args;

    Arguments() {
        args = "";
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

    /**
     * Get the next substring separated by space, and erase this substring from the
     * original string.
     *
     * <p>
     * If the string contains only one substring(which is itself), then return the
     * string and set the original string as "".
     * </p>
     * 
     * @return Substring
     */
    public String nextSubStr() {
        String substr;
        if (args == null)
            throw new IllegalStateException("Argument is null");
        else if (args.equals(""))
            args = Main.getNextCommand().trim();

        if (args.contains(" ")) {
            substr = args.substring(0, args.indexOf(" "));
            args = args.substring(args.indexOf(" ") + 1).trim();
        } else {
            substr = args;
            args = "";
        }
        return substr;
    }

    /**
     * Get the next substring but will not change the original string
     *
     * @see #nextSubStr().
     * @return Substring
     */
    public String peekNextSubStr() {
        String backup = args;
        String substr = nextSubStr();
        args = backup;
        return substr;
    }

    public boolean isEmpty() {
        return args == null || args.equals("");
    }

    /**
     * Get the next token of this command string. If it is a list or a infix
     * expression, then returns the full object.
     *
     * @return String contains the next token
     */
    public String nextToken() {
        StringBuilder opname = new StringBuilder(nextSubStr());

        // The token is a list, creating list
        if (opname.toString().startsWith("[")) {
            int level = 0;
            level += opname.length() - opname.toString().replace("[", "").length();
            level -= opname.length() - opname.toString().replace("]", "").length();

            while (level != 0) {
                String nextArg = " " + nextSubStr();
                opname.append(nextArg);
                level += nextArg.length() - nextArg.replace("[", "").length();
                level -= nextArg.length() - nextArg.replace("]", "").length();
            }
        }
        // Token is an infix exp, creating infix exp
        else if (opname.toString().startsWith("(")) {
            int level = 0;
            level += opname.length() - opname.toString().replace("(", "").length();
            level -= opname.length() - opname.toString().replace(")", "").length();

            while (level != 0) {
                String nextArg = " " + nextSubStr();
                opname.append(nextArg);
                level += nextArg.length() - nextArg.replace("(", "").length();
                level -= nextArg.length() - nextArg.replace(")", "").length();
            }
        }

        return opname.toString();
    }
}