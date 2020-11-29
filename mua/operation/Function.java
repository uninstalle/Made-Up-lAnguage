package mua.operation;

import mua.Name;
import mua.value.List;
import mua.value.Value;

import java.util.HashMap;

public class Function extends Operation{

    public static void main(String[] args) {
        build("test",new List("[[a] [print a]]"));
        FunctionList.get("test").execute(new Arguments("\"a123"));
    }

    String[] parameters;
    String operations;

    static HashMap<String, Function> FunctionList = new HashMap<String, Function>();

    Function getFunction(String funname){
        return FunctionList.get(funname);
    }

    static void build(String name, List functionList){
        String functionString = functionList.toString().substring(1,functionList.toString().length()-1);
        Arguments functionCode = new Arguments(functionString);
        List para = (List)Operation.parseValue(functionCode);
        List op = (List)Operation.parseValue(functionCode);
        Function function = new Function();
        function.parameters = para.toString().substring(1,para.toString().length()-1).split(" ");
        function.operations = op.toString().substring(1,op.toString().length()-1);
        FunctionList.put(name,function);
    }

    String replaceParameters(Arguments args){
        String code = operations;
        for(String para:parameters){
            String arg = Operation.parseValue(args).toRawString();
            code = code.replaceAll(para,arg);
        }
        return code;
    }

    @Override
    Value execute(Arguments args) throws RuntimeException {
        String op = replaceParameters(args);
        Arguments commandString = new Arguments(op);
        Value retval = Value.build("[]");
        while (!commandString.isEmpty())
            retval = parse(commandString);
        return retval;
    }

}
