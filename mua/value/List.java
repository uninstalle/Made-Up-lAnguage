package mua.value;

import java.util.ArrayList;
import mua.assets.Arguments;

/**
 * This class contains a list value. It stores the raw string of the list.
 */
public class List implements Value {
    int level;
    ArrayList<Value> elements;

    List(String value) {
        parseList(value);
        calculateLevel(value);
    }

    /**
     * Test if the given string is an List. The string may contain blanks in its
     * front and back, but should contains only one List.
     * 
     * @param value String that may contain a List
     * @return if the given string is a List
     */
    public static boolean isList(String value) {
        // does not start with [ or end with ], apparently not List
        if (!value.startsWith("[") || !value.endsWith("]"))
            return false;

        int level = 0;
        for (int i = 0; i < value.length(); ++i) {
            if (value.charAt(i) == '[')
                level++;
            else if (value.charAt(i) == ']')
                level--;
            // Always check the balance of [ and ], in case that the string contains two or
            // more Lists
            if (level == 0) {
                // Found a complete List, if the balanced ] is not the end of this string,
                // then it is not a valid List
                if (i != value.length() - 1)
                    return false;
                return true;
            }
        }
        // imbalanced level, so not a List
        return false;
    }

    /**
     * Calculate the level of the List, and assign it to the member level.
     */
    private void calculateLevel(String elementString) {
        int level = 0, maxLevel = 0;
        for (int i = 0; i < elementString.length(); ++i) {
            if (elementString.charAt(i) == '[')
                level++;
            else if (elementString.charAt(i) == ']')
                level--;
            if (maxLevel < level)
                maxLevel = level;
        }
        this.level = maxLevel;
    }

    /**
     * Parse the string as multiple Values, and assign it to the member elements.
     */
    private void parseList(String elementString) {
        elements = new ArrayList<>();
        // remove outer List mark
        elementString = elementString.substring(1, elementString.length() - 1);

        // if it is a CodeBlock List, then the whole List contains only one CodeBlock
        // element
        if (CodeBlock.isCodeBlock(elementString)) {
            elements.clear();
            elements.add(new CodeBlock(elementString));
            return;
        }

        Arguments el = new Arguments(elementString);
        while (!el.isEmpty()) {
            String token = el.nextToken(true);
            Value v = Value.build(token);
            if (v == null) {
                v = Word.build("\"" + token);
            }
            elements.add(v);
        }
    }

    public java.util.List<Value> getElements() {
        return elements;
    }

    public void append(Value v) {
        elements.add(v);
    }

    /**
     * Build a {@code List} from given string.
     *
     * @return {@code List} object, or null if it has no list object
     */
    public static List build(String value) {
        value = value.trim();
        if (List.isList(value))
            return new List(value);
        else
            return null;
    }

    public boolean isEmpty() {
        return elements.isEmpty();
    }

    @Override
    public String toString() {
        if (elements.isEmpty()) {
            return "[]";
        }
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < elements.size(); ++i) {
            if (i == 0)
                str.append('[');
            else
                str.append(' ');
            str.append(elements.get(i).toString());
            if (i == (elements.size() - 1))
                str.append(']');
        }
        return str.toString();
    }

    @Override
    public String toRawString() {
        if (elements.isEmpty()) {
            return "[]";
        }
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < elements.size(); ++i) {
            if (i == 0)
                str.append('[');
            else
                str.append(' ');
            str.append(elements.get(i).toRawString());
            if (i == (elements.size() - 1))
                str.append(']');
        }
        return str.toString();
    }
}
