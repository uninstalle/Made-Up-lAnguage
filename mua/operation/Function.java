package mua.operation;

import mua.Namespace;
import mua.value.List;
import mua.value.Value;


public class Function extends Operation {

    public static void main(String[] args) {
        build(List.build("[[a] [print a]]")).execute(new Arguments("\"a123"));
    }

    String[] parameters;
    String operations;

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

    String replaceParameters(Arguments args) {
        String code = operations;
        for (String para : parameters) {
            String arg = Operation.parseValue(args).toRawString();
            code = code.replaceAll("(?<=[\\s])" + para + "(?=[\\s])", arg);
            code = code.replaceAll("(?<=[\\s])" + para + "$", arg);
            code = code.replaceAll("^" + para + "(?=[\\s])", arg);
        }
        code = code.replace("return", "_return");
        return code;
    }

    @Override
    Value execute(Arguments args) throws RuntimeException {
        String op = replaceParameters(args);

        Namespace.addNestedNamespace();

        Arguments commandString = new Arguments(op);
        Value retval = Value.build("[]");
        while (!commandString.isEmpty())
            retval = parse(commandString);

        Namespace.deleteNestedNamespace();

        return retval;
    }

}
