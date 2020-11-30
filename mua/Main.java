package mua;

import java.util.Scanner;

import mua.operation.*;

public class Main {
    public static final Scanner stdin = new Scanner(System.in);

    public static void main(String[] args) {
        while (stdin.hasNextLine()) {
            String cmd = stdin.nextLine();
            cmd = replaceAllOperation(cmd);
            try {
                if (!cmd.equals(""))
                    Operation.parse(cmd);
            } catch (RuntimeException e) {
                System.out.println(e.getMessage());
            }
        }
        stdin.close();
    }

    static String replaceAllOperation(String raw) {

        raw = raw.replace(":", "thing ");
        raw = replaceOperation(raw, "if", "_if");
        raw = replaceOperation(raw, "return", "_return");

        return raw;
    }

    static String replaceOperation(String raw, String op, String new_op) {
        raw = raw.replaceAll("(?<=[\\s])" + op + "(?=[\\s])", new_op);
        raw = raw.replaceAll("(?<=[\\s])" + op + "$", new_op);
        raw = raw.replaceAll("^" + op + "(?=[\\s])", new_op);
        return raw;
    }
}
