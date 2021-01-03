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
        Arguments arg = new Arguments("[][][[()]](())( )");
        while (!arg.isEmpty())
            System.out.println(arg.nextToken(true));
        System.out.println(new Arguments(" ").nextToken(true));
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
     * original string. Every element in the string must be seperated by one and
     * only one blank. The returned substring contains no blank.
     *
     * <p>
     * If the string contains only one substring(which is itself), then return the
     * string and set the original string as "". If the string is empty, it assumes
     * that the command string is not complete, and wait the users to enter the next
     * part of the command in stdin.
     * </p>
     *
     * @param isIncompletedCommandAccepted when set to true, if the string is not
     *                                     enough to invoke a command, stdin will be
     *                                     open for user input
     * @return Substring
     */
    public String nextSubStr(boolean isIncompletedCommandAccepted) {
        String substr;
        if (args == null)
            throw new IllegalStateException("Argument is null");
        else if (args.equals("") && isIncompletedCommandAccepted)
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
     * Get the next substring but not apply any change to the original string.
     * 
     * @param isIncompletedCommandAccepted when set to true, if the string is not
     *                                     enough to invoke a command, stdin will be
     *                                     open for user input
     * @return Substring
     * @see #nextSubStr().
     */
    public String peekNextSubStr(boolean isIncompletedCommandAccepted) {
        String backup = args;
        String substr = nextSubStr(isIncompletedCommandAccepted);
        args = backup;
        return substr;
    }

    /**
     * Insert a string segment in the front of the Argument string, will append a '
     * ' after this string segment automatically.
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
     * Get the next token of this command string. If it is a list or an infix
     * expression, returns the string containing full object.
     *
     * @return String contains the next token
     * @param isIncompletedCommandAccepted when set to true, if the string is not
     *                                     enough to invoke a command, stdin will be
     *                                     open for user input
     */
    public String nextToken(boolean isIncompletedCommandAccepted) {
        String str = nextSubStr(isIncompletedCommandAccepted);
        String op_name;

        // Token is a list, creating list
        if (str.startsWith("["))
            op_name = parseList(str, isIncompletedCommandAccepted);
        // Token is an infix exp, creating infix exp
        else if (str.startsWith("("))
            op_name = parseInfix(str, isIncompletedCommandAccepted);
        else
            op_name = str;

        return op_name;
    }
    
    /**
     * Get the next token but not apply any change to the original string.
     * 
     * @param isIncompletedCommandAccepted when set to true, if the string is not
     *                                     enough to invoke a command, stdin will be
     *                                     open for user input
     * @return Substring
     * @see #nextSubStr().
     */
    public String peekNextToken(boolean isIncompletedCommandAccepted) {
        String backup = args;
        String token = nextToken(isIncompletedCommandAccepted);
        args = backup;
        return token;
    }

    /**
     * Get the List string from a string containing List in its front.
     * 
     * @param str                          The string containing a List
     * @param isIncompletedCommandAccepted when set to true, if the string is not
     *                                     enough to invoke a command, stdin will be
     *                                     open for user input
     * @return A string of the List
     */
    String parseList(String str, boolean isIncompletedCommandAccepted) {
        int level = 0;
        boolean brk = false;
        StringBuilder op_name = new StringBuilder();

        do {
            for (int i = 0; i < str.length(); ++i) {
                if (str.charAt(i) == '[')
                    level++;
                else if (str.charAt(i) == ']')
                    level--;
                // Always check the balance of [ and ], in case that the string contains two or
                // more Lists
                if (level == 0) {
                    // Find a complete List, send back the left string to the Arguments and break
                    // the loop
                    if (i != str.length() - 1)
                        insertStr(str.substring(i + 1));
                    str = str.substring(0, i + 1);
                    brk = true;
                    break;
                }
            }
            // Tokens parsed above doesn't have any blank, thus here a blank is added before
            // concatenating to the List
            if (!op_name.toString().isEmpty())
                op_name.append(" ").append(str);
            else
                op_name.append(str);
            if (brk)
                break;
            str = nextSubStr(isIncompletedCommandAccepted);
        } while (level != 0);

        return op_name.toString();
    }

    /**
     * Get the Infix exp string from a string containing Infix exp in its front.
     * 
     * @param str                          The string containing a Infix exp
     * @param isIncompletedCommandAccepted when set to true, if the string is not
     *                                     enough to invoke a command, stdin will be
     *                                     open for user input
     * @return A string of the Infix exp
     */
    String parseInfix(String str, boolean isIncompletedCommandAccepted) {
        int level = 0;
        boolean brk = false;
        StringBuilder op_name = new StringBuilder();

        do {
            for (int i = 0; i < str.length(); ++i) {
                if (str.charAt(i) == '(')
                    level++;
                else if (str.charAt(i) == ')')
                    level--;
                // Always check the balance of ( and ), in case that the string contains two or
                // more Lists
                if (level == 0) {
                    // Find a complete Infix, send back the left string to the Arguments and break
                    // the loop
                    if (i != str.length() - 1)
                        insertStr(str.substring(i + 1));
                    str = str.substring(0, i + 1);
                    brk = true;
                    break;
                }
            }
            // Tokens parsed above doesn't have any blank, thus here a blank is added before
            // concatenating to the Infix
            if (!op_name.toString().isEmpty())
                op_name.append(" ").append(str);
            else
                op_name.append(str);
            if (brk)
                break;
            str = nextSubStr(isIncompletedCommandAccepted);
        } while (level != 0);

        return op_name.toString();
    }
}