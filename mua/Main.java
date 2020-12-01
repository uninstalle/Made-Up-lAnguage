package mua;

import java.util.Scanner;

import mua.operation.*;

public class Main {
    public static final Scanner stdin = new Scanner(System.in);

    public static void main(String[] args) {
        while (stdin.hasNextLine()) {
            String cmd = getNextCommand();

            if (!cmd.equals(""))
                Operation.parse(cmd);
            try {
                ;
            } catch (RuntimeException e) {
                System.out.println(e.getMessage());
            }
        }
        stdin.close();
    }

    public static String getNextCommand() {
        String cmd = stdin.nextLine();
        cmd = replaceAllOperation(cmd);
        return cmd;
    }

    static String replaceAllOperation(String raw) {

        raw = raw.replace(":", "thing ");
        raw = raw.replace("\t", " ");
        raw = raw.replace("\n", " ");
        raw = replaceOperation(raw, "if", "_if");
        raw = replaceOperation(raw, "return", "_return");

        return raw;
    }

    static String replaceOperation(String raw, String op, String new_op) {
        raw = raw.replaceAll("(?<=[^A-Za-z0-9])" + op + "(?=[^A-Za-z0-9])", new_op);
        raw = raw.replaceAll("(?<=[^A-Za-z0-9])" + op + "$", new_op);
        raw = raw.replaceAll("^" + op + "(?=[^A-Za-z0-9])", new_op);
        return raw;
    }

}
