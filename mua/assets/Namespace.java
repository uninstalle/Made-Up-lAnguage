package mua.assets;

import java.util.*;

import mua.value.*;
import mua.value.Function;

public class Namespace {

    static final Namespace global = new Namespace(null);

    // initially global
    static Namespace curSpace = global;

    public static void initializeGlobalNamespace() {
        assign(Name.build("pi"), Value.build("3.14159"));
    }

    public static void setCurrentNamespaceGlobal() {
        curSpace = global;
    }

    public static void addNestedNamespace() {
        curSpace = new Namespace(curSpace);
    }

    public static void deleteNestedNamespace() {
        if (curSpace.upper != null)
            curSpace = curSpace.upper;
        else
            throw new IllegalStateException("Trying to delete global namespace");
    }

    Namespace upper;
    HashMap<String, Value> nameList = new HashMap<>();

    public Namespace(Namespace s) {
        upper = s;
    }

    void _assign(String name, Value value) {
        nameList.put(name, value);
    }

    public static void assign(Name name, Value value) {
        curSpace._assign(name.toString(), value);
    }

    public static void assign(String name, Value value) {
        curSpace._assign(name, value);
    }

    public static void assignGlobal(Name name, Value value) {
        global._assign(name.toString(), value);
    }

    public static void assignGlobal(String name, Value value) {
        global._assign(name, value);
    }

    Value _get(String name) {
        return nameList.get(name);
    }

    public static Value get(Name name) {
        return get(name.toString());
    }

    /**
     * Get the value of the variable with the given name. If current namespace doesn't contain the variable, this function will look up to upper namespace
     *
     * @param name variable name
     * @return value of the variable, or null if the variable doesn't exist
     */
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

    /**
     * Get function with the given name. If there exists a variable with the same name in nearer namespace, this function will stop look up and return null
     *
     * @param name function name
     * @return function, or null if no function retrieved
     */
    public static Function getFunction(String name) {
        Namespace ns = curSpace;
        Value v = ns._get(name);
        while (v == null) {
            if (ns.upper == null)
                break;
            ns = ns.upper;
            v = ns._get(name);
        }
        if (v == null || !v.isFunction())
            return null;
        return v.toFunction();
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

    public static Value erase(Name name) {
        return curSpace._erase(name);
    }
}
