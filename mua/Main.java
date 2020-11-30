package mua;

import java.util.Scanner;

import mua.operation.*;
import mua.value.*;

public class Main {
    public static final Scanner stdin = new Scanner(System.in);

    public static void main(String[] args) {
        while (stdin.hasNextLine()) {
            String cmd = stdin.nextLine();
            cmd = cmd.replace(":", "thing ");
            cmd = cmd.replace("if", "_if");
            if (!cmd.equals(""))
                Operation.parse(cmd);
        }
        stdin.close();
    }

}
