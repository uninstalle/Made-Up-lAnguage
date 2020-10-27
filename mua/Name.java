package mua;

import java.util.HashMap;
import mua.value.*;

public class Name {
    String name;

    Name(String name) {
        if (name.startsWith("\""))
            name = name.substring(1);
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

    static HashMap<String, Value> NameList = new HashMap<String, Value>();

    public static void assign(Name name, Value value) throws RuntimeException {
        if (!NameList.containsKey(name.toString()))
            NameList.put(name.toString(), value);
        else
            throw new RuntimeException("Variable name " + name.toString() + " has been used");
    }

    public static Name build(String name) throws RuntimeException {
        if (name.matches("^\"?[A-Za-z]([A-Za-z0-9_])*"))
            return new Name(name);
        else
            return null;
    }

    public static Value get(Name name) {
        return NameList.get(name.toString());
    }

    public static Value get(String name) {
        return NameList.get(name);
    }

    public static Value erase(Name name) {
        if (!NameList.containsKey(name.toString()))
            return null;
        else {
            Value v = get(name);
            NameList.remove(name, get(name));
            return v;
        }
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
