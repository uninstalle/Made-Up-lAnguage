package mua.value;

import mua.operation.Operation;
import mua.assets.Arguments;

/**
 * A special List object for Function. Value.build will not create object of
 * this class. CodeBlock contains a string consisted of various commands.
 */
public class CodeBlock implements Value {
    String value;

    CodeBlock(String value) {
        this.value = value;
    }

    /**
     * Test if a List is a CodeBlock
     */
    public static boolean isCodeBlock(String value) {
        // remove outer List mark
        Arguments el = new Arguments(value);
        String token = el.peekNextToken(false);
        if (token.isEmpty())
            return false;

        // If element contains commands, then this is a CodeBlock
        // We may endure an operator to support constant expressions like add 1 2
        if (Operation.isOperation(token) && !Operation.isOperator(token)) {
            return true;
        }
        return false;
    }

    public static CodeBlock build(String value) {
        value = value.trim();
        if (isCodeBlock(value))
            return new CodeBlock(value);
        else
            return null;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public String toRawString() {
        return value;
    }
}
