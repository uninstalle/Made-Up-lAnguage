package mua;

import java.util.Scanner;

import mua.operation.Operation;

public class Main {
    static public Scanner stdin = new Scanner(System.in);
    static public void main(String[] args) {
        while (stdin.hasNext()) {
            String cmd = stdin.nextLine();
            cmd = cmd.replaceAll(":", "thing ");
            Value retVal = Operation.parse(cmd);
        }
        stdin.close();
    }

}
