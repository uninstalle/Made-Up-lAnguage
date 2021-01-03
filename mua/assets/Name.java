package mua.assets;

import mua.value.Word;

/**
 * This class is a wrapper of String. It will check the rules of variable
 * naming, making sure all objects of this class is legal. The Name should
 * contain only alphabetic characters and numbers and _, and start with
 * alphabetic characters or _.
 */
public class Name {

    public static void main(String[] args) {
        Name n = Name.build("0aa0");
        if (n == null)
            System.out.println("null");
        else
            System.out.println();
        n = Name.build("_aa0");
        if (n == null)
            System.out.println("null");
        else
            System.out.println(n.toString());
        n = Name.build("aa0");
        if (n == null)
            System.out.println("null");
        else
            System.out.println(n);
    }

    String name;

    Name(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

    /**
     * build a {@code Name} from given {@code Word}.
     *
     * @param name Word containing the name string
     * @return {@code Name} object, or null if the Word is not a valid name
     */
    public static Name build(Word name) {
        // No need to check if the string is the same as some commands or user
        // functions, since the parser will try to take the string as a function first,
        // if no function found, then try to build it as a Name.
        if (name.toString().matches("^[A-Za-z_]([A-Za-z0-9_])*"))
            return new Name(name.toString());
        else
            return null;
    }

    /**
     * Build a {@code Name} from given string. The string is not a raw string of
     * Word, which means it contains no " at the beginning
     *
     * @param name name string
     * @return {@code Name} object, or null if the string is not a valid name
     */
    public static Name build(String name) {
        if (name.matches("^[A-Za-z_]([A-Za-z0-9_])*"))
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
