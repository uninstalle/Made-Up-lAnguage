package mua.assets;

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

    public static void main(String[] args) {
        Arguments arg = new Arguments("[][][[()]](())()");
        while (!arg.isEmpty())
            System.out.println(arg.nextToken());
    }

    String args;

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
            args = Main.getNextCommand();

        if (args.contains(" ")) {
            substr = args.substring(0, args.indexOf(" "));
            args = args.substring(args.indexOf(" ") + 1);
        } else {
            substr = args;
            args = "";
        }
        return substr;
    }

    /**
     * Get the next substring but will not change the original string
     *
     * @return Substring
     * @see #nextSubStr().
     */
    public String peekNextSubStr() {
        String backup = args;
        String substr = nextSubStr();
        args = backup;
        return substr;
    }

    /**
     * Insert a string in the front of the Argument string
     *
     * @param str string for inserting
     */
    public void insertStr(String str) {
        args = str + " " + args;
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
        String str = nextSubStr();
        String op_name;

        // The token is a list, creating list
        if (str.startsWith("["))
            op_name = parseList(str);
            // Token is an infix exp, creating infix exp
        else if (str.startsWith("("))
            op_name = parseInfix(str);
        else
            op_name = str;

        return op_name;
    }

    String parseList(String str) {
        int level = 0;
        boolean brk = false;
        StringBuilder op_name = new StringBuilder();

        do {
            for (int i = 0; i < str.length(); ++i) {
                if (str.charAt(i) == '[')
                    level++;
                else if (str.charAt(i) == ']')
                    level--;
                if (level == 0) {
                    // if i == str.length() - 1, then nothing needs to be sent back
                    if (i != str.length() - 1)
                        insertStr(str.substring(i + 1));
                    str = str.substring(0, i + 1);
                    brk = true;
                    break;
                }
            }
            if (!op_name.toString().equals(""))
                op_name.append(" ").append(str);
            else
                op_name.append(str);
            if (brk)
                break;
            str = nextSubStr();
        } while (level != 0);

        return op_name.toString();
    }

    String parseInfix(String str) {
        int level = 0;
        boolean brk = false;
        StringBuilder op_name = new StringBuilder();

        do {
            for (int i = 0; i < str.length(); ++i) {
                if (str.charAt(i) == '(')
                    level++;
                else if (str.charAt(i) == ')')
                    level--;
                if (level == 0) {
                    // if i == str.length() - 1, then nothing needs to be sent back
                    if (i != str.length() - 1)
                        insertStr(str.substring(i + 1));
                    str = str.substring(0, i + 1);
                    brk = true;
                    break;
                }
            }
            if (!op_name.toString().equals(""))
                op_name.append(" ").append(str);
            else
                op_name.append(str);
            if (brk)
                break;
            str = nextSubStr();
        } while (level != 0);

        return op_name.toString();
    }
}