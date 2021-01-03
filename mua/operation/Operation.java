package mua.operation;

import mua.assets.Name;
import mua.assets.Namespace;
import mua.assets.Arguments;
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
import mua.operation._return;
import mua.operation.export;

/**
 * The interface of predefined operations of MUA.
 *
 * <p>
 * This class consists a set of static methods to parse the command string and
 * build objects. All {@code Operation} class is built by it. To execute the
 * operation, invoke the {@code execute} function of the {@code Operation} class
 * with {@code Arguments} as parameters.
 *
 * @see #execute(Arguments)
 */
public interface Operation {

    public static void main(String[] args) {
        String test = "add";
        System.out.println(isOperation(test));
        System.out.println(isOperator(test));
    }

    final String CLASSPREFIX = "mua.operation.";

    /**
     * The only abstract function of {@code Operation}.
     *
     * <p>
     * This function accepts a {@code Arguments} and try to parse it and execute it
     * to get a return value.
     *
     * @param args The Arguments object containing command string
     * @return The return value of the command string
     * @throws RuntimeException When the operation causes an exception
     */
    Value execute(Arguments args) throws RuntimeException;

    /**
     * Try to build an operation object via the op name.
     *
     * <p>
     * This function will try to build predefined operation according to the op name
     * string. It doesn't contain the customized functions.
     * </p>
     *
     * @param op_name The string of the operation name
     * @return the very operation object, or null if the operation doesn't exist
     */
    static Operation build(String op_name) {
        Operation op;
        try {
            Class<?> opClass = Class.forName(CLASSPREFIX + op_name);
            op = (Operation) opClass.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            return null;
        }
        return op;
    }

    /**
     * Test if there exists an operation with the name given
     *
     * @param op_name The name of operation
     * @return If the operation exists
     */
    static boolean isOperation(String op_name) {
        try {
            Class.forName(CLASSPREFIX + op_name);
        } catch (ClassNotFoundException e) {
            return false;
        }
        return true;
    }

    static boolean isOperator(String op_name) {
        Operation op;
        try {
            Class<?> opClass = Class.forName(CLASSPREFIX + op_name);
            op = (Operation) opClass.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            return false;
        }
        return op instanceof Operator;
    }

    /**
     * Starting recursive parsing.
     *
     * @param str The complete command string
     * @return The return value of the command string, or null if it is not valid
     */
    static Value parse(String str) {
        return parseValue(new Arguments(str));
    }

    /**
     * @param args The Arguments object containing command string
     * @return The return value of the command string, or null if it is not valid
     * @see #parse(String)
     */
    static Value parse(Arguments args) {
        return parseValue(args);
    }

    /**
     * Try to parse the first token of the command string as a value.
     *
     * <p>
     * This function will get the first token of the arguments(separated by space),
     * and try to parse it as an operation first. If it is not, then take it as a
     * value.
     * </p>
     *
     * @param args The Arguments object containing command string
     * @return The parsed value, or null if it is not a valid value
     */
    static Value parseValue(Arguments args) {
        String op_name = args.nextToken(true);

        // Test if it is a function
        Function fun = Namespace.getFunction(op_name);
        if (fun != null)
            return fun.execute(args);
        // Test if it is an operation
        Operation op = Operation.build(op_name);
        if (op != null)
            return op.execute(args);
        Value v = Value.build(op_name);
        if (v == null)
            throw new IllegalArgumentException(op_name + " is not a valid Value");
        return v;
    }

    /**
     * Try to parse the first token of the command string as a name.
     *
     * <p>
     * This function will get the first token of the arguments(separated by space),
     * and try to parse it as a function or operation first. If it is not, then take
     * it as a name. Name should be a Word.
     * </p>
     *
     * @param args The Arguments object containing command string
     * @return The parsed name, or null if it is not a valid name
     */
    static Name parseName(Arguments args) {
        String op_name = args.nextToken(true);

        // Try to build a function or operation first in case that building a variable
        // named the same as an function or operation
        Function fun = Namespace.getFunction(op_name);
        if (fun != null)
            return parseName(new Arguments(fun.execute(args).toRawString()));
        Operation op = Operation.build(op_name);
        if (op != null)
            return parseName(new Arguments(op.execute(args).toRawString()));
        Word word = Word.build(op_name);
        if (word == null)
            throw new IllegalArgumentException(op_name + " is not a valid Word");
        Name name = Name.build(word);
        if (name == null)
            throw new IllegalArgumentException(op_name + " is not a valid Name");
        return name;
    }

    /**
     * This is a special form of parseName for Operation {@code thing}. {@code Name}
     * in {@code thing} operation has no " mark.
     *
     * @param args The Arguments object containing command string
     * @return The parsed name, or null if it is not a valid name
     */
    static Name parseNameLabel(Arguments args) {
        String op_name = args.nextToken(true);

        // Function fun = Namespace.getFunction(op_name);
        // if (fun != null)
        // return parseNameLabel(new Arguments(fun.execute(args).toString()));
        Operation op = Operation.build(op_name);
        if (op != null)
            return parseNameLabel(new Arguments(op.execute(args).toString()));
        Name name = Name.build(op_name);
        if (name == null)
            throw new IllegalArgumentException(op_name + " is not a valid Word");
        return name;
    }

}
