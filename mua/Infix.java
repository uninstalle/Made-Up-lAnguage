package mua;

import java.util.Stack;

import mua.value.*;
import mua.operation.Arguments;
import mua.operation.*;

public class Infix {
    String value;

    public static void main(String[] args) {
        Infix exp = Infix.build("(add (5%3-3*3/(5+4)) 5)");
        System.out.println(exp.getValue().toString());
    }

    public Infix(String value) {
        this.value = value;
    }

    public static boolean isInfix(String value) {
        if (!(value.startsWith("(") && value.endsWith(")")))
            return false;
        return true;
    }

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
                            String ops = s1.pop();
                            if (ops.equals("(")) {
                                break;
                            } else
                                s2.push(ops);
                        }
                        break;
                    } else if (s1.empty() || s1.peek().equals("(")) {
                        s1.push(op);
                        break;
                    } else if ((s1.peek().equals("+") || s1.peek().equals("-"))
                            && (op.equals("*") || op.equals("/") || op.equals("%"))) {
                        s1.push(op);
                        break;
                    } else if (op.equals("(")) {
                        s1.push(op);
                        break;
                    } else {
                        s2.push(s1.pop());
                    }
                }
            } else {
                // next arg is an operand(or a operation, execute it to get return value then)
                Value retVal = Operation.parse(args);
                s2.push(((mua.value.Number) retVal).toString());
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
        return Value.build(s1.pop());
    }

    public boolean isEmpty() {
        return value.equals("()");
    }

    @Override
    public String toString() {
        return value;
    }
}
