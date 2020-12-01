package mua.assets;

import mua.value.Word;

public class Name {
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
     * @param name name Word
     * @return {@code Name} object, or null if the Word is not a valid name
     */
    public static Name build(Word name) {
        if (name.toString().matches("^[A-Za-z_]([A-Za-z0-9_])*"))
            return new Name(name.toString());
        else
            return null;
    }

    /**
     * Build a {@code Name} from given string. The string is not a raw string of a word, which means it contains no "
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
