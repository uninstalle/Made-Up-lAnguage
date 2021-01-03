package mua.value;

import mua.assets.Namespace;

import mua.assets.Arguments;
import mua.operation.Operation;

/**
 * The class of user-defined function of MUA. Function is a special type of
 * List.
 */
public class Function extends List implements Operation {

    public static void main(String[] args) {
        build("[[] [print 1]]").execute(new Arguments("\"a123"));
    }

    String[] parameters;
    String operations;

    Function(String value) {
        super(value);
        // directly convert List to string, thus discard the marker []
        // Code block may starts from blank, thus trim is needed
        parameters = elements.get(0).toString().substring(1, elements.get(0).toString().length() - 1).trim().split(" ");
        operations = elements.get(1).toString().substring(1, elements.get(1).toString().length() - 1).trim();
    }

    /**
     * Build a {@code Function} object from the given string.
     *
     * @param value A string that contains a function
     * @return The {@code Function} object built
     */
    public static Function build(String value) {
        value = value.trim();
        if (isFunction(value))
            return new Function(value);
        else
            return null;
    }

    /**
     * Test if the string contains a valid function, which is a List that contains a
     * parameter list and a function body.
     *
     * <p>
     * This function will not check if parameter declaration and code are valid.
     *
     * @param value A string that may contain a function
     * @return Whether the list is a function
     */
    public static boolean isFunction(String value) {
        List l = List.build(value);
        if (l == null)
            return false;
        if (l.elements.size() != 2)
            return false;
        try {
            // empty List is considered as CodeBlock too.
            boolean hasPara = l.elements.get(0).isList();
            boolean hasBody = l.elements.get(1).toList().isEmpty()
                    || l.elements.get(1).toList().elements.get(0).isCodeBlock();
            return hasPara && hasBody;
        } catch (ClassCastException e) {
            return false;
        }
    }

    /**
     * Generate assignment commands of the arguments to the code block.
     * 
     * @param args Arguments' value
     * @return Replaced code block
     */
    String prepareParametersAssignments(Arguments args) {
        StringBuilder assignment = new StringBuilder();
        // test if it has parameters
        if (!(parameters.length == 1 && parameters[0].isEmpty()))
            for (String para : parameters) {
                String arg = Operation.parseValue(args).toRawString();
                // generate make command
                assignment.append("make \"").append(para).append(" ").append(arg).append(" ");
            }
        return assignment.toString();
    }

    @Override
    public Value execute(Arguments args) {
        // Generate given arguments' assignment command
        Arguments assignment = new Arguments(prepareParametersAssignments(args));
        // create function namespace
        Namespace.addNestedNamespace();
        // assign the arguments
        while (!assignment.isEmpty())
            Operation.parse(assignment);

        // execute function body
        Arguments commandString = new Arguments(operations);
        Value retVal = null;
        while (!commandString.isEmpty())
            retVal = Operation.parse(commandString);

        // remove function namespace
        Namespace.deleteNestedNamespace();

        return retVal;
    }
}
