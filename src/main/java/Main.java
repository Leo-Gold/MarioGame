import jade.Window;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Window window = Window.get();
        window.run();
    }

    public static Boolean valueOf(boolean b) {
        return b ? Boolean.TRUE : Boolean.FALSE;
    }
}