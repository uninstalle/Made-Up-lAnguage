package mua.value;

import mua.assets.Namespace;
import mua.assets.Arguments;
import mua.operation.Operation;

/**
 * The class of user-defined function of MUA.
 */
public class Function implements Operation,Value {

    public static void main(String[] args) {
        build(List.build("[[] [print 1]]")).execute(new Arguments("\"a123"));
    }

    String[] parameters;
    String operations;

    /**
     * Build a {@code Function} object from the given list.
     * 
     * <p>
     * This function will not check if the list is a valid function
     * 
     * @param functionList A list that contains a function
     * @return The {@code Function} object built
     */
    public static Function build(List functionList) {
        String functionString = functionList.toString().substring(1, functionList.toString().length() - 1);
        Arguments functionCode = new Arguments(functionString);
        List para = (List) Operation.parseValue(functionCode);
        List op = (List) Operation.parseValue(functionCode);

        Function function = new Function();
        function.parameters = para.toString().substring(1, para.toString().length() - 1).split(" ");
        function.operations = op.toString().substring(1, op.toString().length() - 1);
        return function;
    }

    /**
     * Test if the {@code Value} contains a valid function, which is a list that
     * contains two list elements.
     * 
     * <p>
     * This function will not check if the two list elements have valid parameter
     * declaration and code.
     * 
     * @param functionList A list that may contain a function
     * @return Whether the list is a function
     */
    public static boolean isFunction(Value functionList) {
        if (!functionList.isList())
            return false;
        String functionString = functionList.toString().substring(1, functionList.toString().length() - 1);
        Arguments functionCode = new Arguments(functionString);
        // with two and only two list objects as its element
        if (List.isList(functionCode.nextToken()) && List.isList(functionCode.nextToken()) && functionCode.isEmpty())
            return true;
        else
            return false;
    }

    String prepareParametersAssignments(Arguments args) {
        StringBuilder assignment = new StringBuilder();
        // test if no parameter
        if (!(parameters.length == 1 && parameters[0].equals("")))
            for (String para : parameters) {
                String arg = Operation.parseValue(args).toRawString();
                assignment.append("make \"").append(para).append(" ").append(arg).append(" ");
            }
        return assignment.toString();
    }

    @Override
    public Value execute(Arguments args) {
        Arguments assignment = new Arguments(prepareParametersAssignments(args));
        Namespace.addNestedNamespace();
        while (!assignment.isEmpty())
            Operation.parse(assignment);

        Arguments commandString = new Arguments(operations);
        Value retVal = Value.build("[]");
        while (!commandString.isEmpty())
            retVal = Operation.parse(commandString);

        Namespace.deleteNestedNamespace();

        return retVal;
    }

    @Override
    public String toRawString() {
        StringBuilder p = new StringBuilder();
        for(String para : parameters)
            p.append(para).append(" ");
        return p + operations;
    }
}
