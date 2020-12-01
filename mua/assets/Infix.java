package mua.assets;

import java.util.Stack;

import mua.value.*;
import mua.operation.*;

/**
 * This class accepts a string of infix expression.
 *
 * <p>
 * To get the value of the infix expression, use {@code getValue} method.
 */
public class Infix {
    String value;

    public static void main(String[] args) {
        Infix exp = Infix.build("()");
        if (exp != null)
            System.out.println(exp.getValue().toString());
    }

    public Infix(String value) {
        this.value = value;
    }

    public static boolean isInfix(String value) {
        return value.startsWith("(") && value.endsWith(")");
    }

    /**
     * Try to build a infix expression class from given string. Given string should contains infix exp mark ().
     *
     * @param value infix exp string
     * @return infix expression object
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
        value = value.replaceAll("(?=[\\(\\)])", " ");
        value = value.replaceAll("(?<=[\\(\\)])", " ");
        value = value.replaceAll("(?<=([0-9A-Za-z]{1,50}|\\))\\s{0,50}[\\+\\-\\*/%])", " ");
        value = value.replaceAll("(?<=([0-9A-Za-z]{1,50}|\\))\\s{0,50})(?=[\\+\\-\\*/%])", " ");
    }

    /**
     * Get the value of the infix expression.
     *
     * @return The value of the infix expression
     */
    public Value getValue() {

        tokenize();
        Arguments args = new Arguments(value);

        Stack<String> s1 = new Stack<>();
        Stack<String> s2 = new Stack<>();

        while (!args.isEmpty()) {
            String arg = args.peekNextSubStr();
            if (arg.matches("[\\+\\-\\*/%\\(\\)]")) {
                // next arg is an operator
                String op = args.nextSubStr();
                while (true) {
                    if (op.equals(")")) {
                        while (true) {
                            // keep popping until the matched left( is found
                            String ops = s1.pop();
                            if (ops.equals("("))
                                break;
                            s2.push(ops);
                        }
                        break;
                    } else if (s1.empty() || s1.peek().equals("(")) {
                        // first in stack or after left(, just push it
                        s1.push(op);
                        break;
                    } else if ((s1.peek().matches("[\\+\\-]")) && op.matches("[\\*/%]")) {
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
            if (element.matches("[\\+\\-\\*/%]")) {
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
            } else if (element.matches("[\\(\\)]")) {
                // should not be any () exists
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
