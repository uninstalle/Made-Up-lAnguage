package mua;

import java.util.*;

import mua.value.*;
import mua.operation.Function;

public class Namespace {

    static final Namespace global = new Namespace(null);

    // initally global
    static Namespace curSpace = global;

    public static void initializeGlobalSpace() {
        assign(Name.build("pi"), Value.build("3.14159"));
    }

    public static void addNestedNamespace() {
        curSpace = new Namespace(curSpace);
    }

    public static void deleteNestedNamespace() {
        if (curSpace.upper != null)
            curSpace = curSpace.upper;
    }

    public static Namespace getCurrentNamespace() {
        return curSpace;
    }

    public static Namespace getGlobalNamespace() {
        return global;
    }

    Namespace upper;
    HashMap<String, Value> nameList = new HashMap<>();
    HashMap<String, Function> functionList = new HashMap<>();

    public Namespace(Namespace s) {
        upper = s;
    }

    void _assign(Name name, Value value) {
        nameList.put(name.toString(), value);
    }

    public static void assign(Name name, Value value) {
        curSpace._assign(name, value);
    }

    public static void assignGlobal(Name name, Value value) {
        global._assign(name, value);
    }

    void _assignFunction(String name, Function fun) {
        functionList.put(name, fun);
    }

    public static void assignFunction(String name, Function fun) {
        curSpace._assignFunction(name, fun);
    }

    public static void assignGlobalFunction(String name, Function fun) {
        global._assignFunction(name, fun);
    }

    Value _get(Name name) {
        return nameList.get(name.toString());
    }

    public static Value get(Name name) {
        Namespace ns = curSpace;
        Value v = ns._get(name);
        while (v == null) {
            if (ns.upper == null)
                break;
            ns = ns.upper;
            v = ns._get(name);
        }
        return v;
    }

    Value _get(String name) {
        return nameList.get(name);
    }

    public static Value get(String name) {
        Namespace ns = curSpace;
        Value v = ns._get(name);
        while (v == null) {
            if (ns.upper == null)
                break;
            ns = ns.upper;
            v = ns._get(name);
        }
        return v;
    }

    Function _getFunction(String name) {
        return functionList.get(name);
    }

    public static Function getFunction(String name) {
        Namespace ns = curSpace;
        Function f = ns._getFunction(name);
        while (f == null) {
            if (ns.upper == null)
                break;
            ns = ns.upper;
            f = ns._getFunction(name);
        }
        return f;
    }

    public static Value erase(Name name) {
        return curSpace._erase(name);
    }

    public Value _erase(Name name) {
        if (!nameList.containsKey(name.toString()))
            return null;
        else {
            Value v = get(name);
            nameList.remove(name.toString());
            return v;
        }
    }
}
