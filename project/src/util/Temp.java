package util;

import java.util.ArrayList;

public class Temp {
    void main() {
        Integer x = 10;
        Integer y = 1000000;
        String test = "ee";

        ArrayList<Integer> z = new ArrayList<>();

        if (x == y) {
            System.out.println("lol");
        }

        if (test == null)
            return;

        if (true) {
            x = 500;
        } else {
            x = 1000;
        }

        for (int i = 0; i < z.size(); i++) {
            System.out.println("Testar cenas aleatorias");
            int j = 0;
            x = ++j;
        }

        System.out.println("The final value of x is" + x);
    }
}
