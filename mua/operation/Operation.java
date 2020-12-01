package mua.operation;

import mua.assets.Name;
import mua.assets.Namespace;
import mua.assets.Arguments;
import mua.value.*;

// force to compile all operation class


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
            Class<?> opClass = Class.forName("mua.operation." + op_name);
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
        return build(op_name) != null;
    }

    /**
     * Starting recursive parsing.
     *
     * @param str The complete command string
     * @return The return value of the command string, or null if it is not valid
     */
    public static Value parse(String str) {
        return parseValue(new Arguments(str));
    }

    /**
     * @see #parse(String)
     * @param args The Arguments object containing command string
     * @return The return value of the command string, or null if it is not valid
     */
    public static Value parse(Arguments args) {
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
    public static Value parseValue(Arguments args) {
        String opname = args.nextToken();

        // Test if it is a function
        Function fun = Namespace.getFunction(opname);
        if (fun != null)
            return fun.execute(args);
        // Test if it is an operation
        Operation op = Operation.build(opname);
        if (op != null)
            return op.execute(args);
        return Value.build(opname);
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
        String opname = args.nextToken();

        // Try to build a function or operation first in case that building a variable
        // named the same as an function or operation
        Function fun = Namespace.getFunction(opname);
        if (fun != null)
            return parseName(new Arguments(fun.execute(args).toRawString()));
        Operation op = Operation.build(opname);
        if (op != null)
            return parseName(new Arguments(op.execute(args).toRawString()));
        return Name.build(Word.build(opname));
    }

    /**
     * This is a special form of parseName for Operation {@code thing}. {@code Name}
     * in {@code thing} operation has no " mark.
     * 
     * @param args The Arguments object containing command string
     * @return The parsed name, or null if it is not a valid name
     */
    static Name parseNameLabel(Arguments args) {
        String opname = args.nextToken();

        Function fun = Namespace.getFunction(opname);
        if (fun != null)
            return parseNameLabel(new Arguments(fun.execute(args).toString()));
        Operation op = Operation.build(opname);
        if (op != null)
            return parseNameLabel(new Arguments(op.execute(args).toString()));
        return Name.build(opname);
    }

}
