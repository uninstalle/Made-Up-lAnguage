package mua;

import java.util.Scanner;

import mua.operation.*;
import mua.value.*;

public class Main {
    static public Scanner stdin = new Scanner(System.in);
    static public void main(String[] args) {
        while (stdin.hasNext()) {
            String cmd = stdin.nextLine();
            cmd = cmd.replaceAll(":", "thing ");
            cmd = cmd.replaceAll("if", "_if");
            Value retVal = Operation.parse(cmd);
        }
        stdin.close();
    }

}
