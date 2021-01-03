package mua.assets;

import java.util.Stack;

import mua.value.*;
import mua.operation.*;

/**
 * This class accepts a string of infix expression. Using build method to build
 * a Infix object.
 *
 * <p>
 * To get the value of the infix expression, use {@code getValue} method.
 */
public class Infix {

    public static void main(String[] args) {
        Infix exp = Infix.build("((1+2)*3)");
        if (exp != null)
            System.out.println(exp.getValue().toString());
        else
            System.out.println("Not an infix exp");
        exp = Infix.build("((1+2)*3)()");
        if (exp != null)
            System.out.println(exp.getValue().toString());
        else
            System.out.println("Not an infix exp");
    }

    // The string of infix expression
    String value;

    public Infix(String value) {
        this.value = value;
    }

    /**
     * Test if the given string is an Infix exp. The string may contain blanks in
     * its front and back, but should contains only one infix exp.
     * 
     * @param value String that may contain infix exp
     * @return if the given string is an Infix exp
     */
    public static boolean isInfix(String value) {
        // does not start with ( or end with ), apparently not infix exp
        if (!value.startsWith("(") || !value.endsWith(")"))
            return false;

        int level = 0;
        for (int i = 0; i < value.length(); ++i) {
            if (value.charAt(i) == '(')
                level++;
            else if (value.charAt(i) == ')')
                level--;
            // Always check the balance of ( and ), in case that the string contains two or
            // more Infix exps
            if (level == 0) {
                // Found a complete Infix exp, if the balanced ) is not the end of this string,
                // then it is not a valid Infix exp
                if (i != value.length() - 1)
                    return false;
                return true;
            }
        }
        // imbalanced level, so not an infix exp
        return false;
    }

    /**
     * Try to build a infix expression class from given string. Given string should
     * contains infix exp mark '(',')'.
     *
     * @param value infix exp string
     * @return infix expression object, or null if it is not a valid infix exp
     */
    public static Infix build(String value) {
        value = value.trim();
        if (Infix.isInfix(value))
            return new Infix(value);
        else
            return null;
    }

    /**
     * Tokenize the original infix expression string. Add a space between any pair
     * of operators/operands.
     */
    void tokenize() {
        // Take "(1+2)*3" as an axample

        // Insert a blank into all that before ( or )
        // The example will be " (1+2 )*3"
        value = value.replaceAll("(?=[()])", " ");

        // Insert a blank into all that after ( or )
        // The example will be " ( 1+2 ) *3"
        value = value.replaceAll("(?<=[()])", " ");

        // Insert a blank after string like [operand, ) ] operator
        // The example will be "( 1+ 2 ) * 3"
        value = value.replaceAll("(?<=([0-9A-Za-z]{1,50}|\\))\\s{0,50}[+\\-*/%])", " ");

        // Insert a blank between [operand, ) ] and operator
        // The example will be "( 1 + 2 ) * 3"
        value = value.replaceAll("(?<=([0-9A-Za-z]{1,50}|\\))\\s{0,50})(?=[+\\-*/%])", " ");

        // Why {0,50}?
        // Java's regexp parser doesn't support using ?<=/?= and {n,inf} (For
        // example,\s+) at the same time.
        // Thus here I replaced * with {0,50}, replaced + with {1,50}. Normally it will
        // not exceed the upper bound 50.

        // Compress the blanks, make sure that only one space between every element in
        // the infix exp
        value = value.replaceAll("[\\s]+", " ");
    }

    /**
     * Get the result value of the infix expression.
     *
     * @return The value of the infix expression
     */
    public Value getValue() {

        tokenize();
        Arguments args = new Arguments(value);

        Stack<String> s1 = new Stack<>();
        Stack<String> s2 = new Stack<>();

        while (!args.isEmpty()) {
            String arg = args.peekNextSubStr(true);
            if (arg.matches("[+\\-*/%()]")) {
                // next arg is an operator
                String op = args.nextSubStr(true);
                while (true) {
                    if (op.equals(")")) {
                        while (true) {
                            // keep popping until the matched left( is found
                            String ops = s1.pop();
                            if (ops.equals("("))
                                break;
                            s2.push(ops);
                        }
                        // break from the while(true)
                        break;
                    } else if (s1.empty() || s1.peek().equals("(")) {
                        // it is the first one in the stack or after a left(, so just push it
                        s1.push(op);
                        break;
                    } else if ((s1.peek().matches("[+\\-]")) && op.matches("[*/%]")) {
                        // current operator has higher priority
                        s1.push(op);
                        break;
                    } else if (op.equals("(")) {
                        // current operator is left(, just push it
                        s1.push(op);
                        break;
                    } else {
                        // current operator has less or same priority
                        s2.push(s1.pop());
                    }
                }
            } else {
                // next arg is an operand(or a operation, execute it to get return value then)
                Value retVal = Operation.parse(args);
                s2.push(retVal.toString());
            }
        }
        while (!s1.empty()) {
            s2.push(s1.pop());
        }

        s1.clear();

        for (String element : s2) {
            if (element.matches("[+\\-*/%]")) {
                double val1 = Double.parseDouble(s1.pop());
                double val2 = Double.parseDouble(s1.pop());
                switch (element) {
                    case "+":
                        val2 += val1;
                        break;
                    case "-":
                        val2 -= val1;
                        break;
                    case "*":
                        val2 *= val1;
                        break;
                    case "/":
                        val2 /= val1;
                        break;
                    case "%":
                        val2 %= val1;
                        break;
                    default:
                        break;
                }
                s1.push(String.valueOf(val2));
            } else if (element.matches("[()]")) {
                // should not be any () exists, but who knows?
            } else {
                s1.push(element);
            }
        }
        if (s1.empty())
            return Value.build("0");
        Value v = Value.build(s1.pop());
        if (v == null)
            throw new IllegalStateException("Unexpected result in infix expression");
        return v;
    }

    public boolean isEmpty() {
        return value.equals("()");
    }

    @Override
    public String toString() {
        return value;
    }
}
