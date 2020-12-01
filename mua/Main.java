package mua;

import java.util.Scanner;

import mua.operation.*;
import mua.assets.Namespace;

public class Main {
    public static final Scanner stdin = new Scanner(System.in);

    public static void main(String[] args) {

        Namespace.initializeGlobalNamespace();

        while (stdin.hasNextLine()) {
            String cmd = getNextCommand();

            try {
                if (!cmd.equals(""))
                    Operation.parse(cmd);
            } catch (RuntimeException e) {
                System.out.println(e.getMessage());
                Namespace.setCurrentNamespaceGlobal();
            }
        }
        stdin.close();
    }

    public static String getNextCommand() {
        String cmd = stdin.nextLine();
        cmd = formatCommand(cmd).trim();
        return cmd;
    }

    static String formatCommand(String raw) {

        raw = raw.replace(":", "thing ");
        raw = raw.replaceAll("[\\s]+", " ");
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
