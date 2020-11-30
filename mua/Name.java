package mua;

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

    public static Name build(String name) {
        if (name.matches("^\"?[A-Za-z]([A-Za-z0-9_])*"))
            return new Name(name);
        else
            return null;
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Name))
            return false;
        return name.equals(((Name) obj).name);
    }
}
