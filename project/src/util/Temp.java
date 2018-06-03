package util;

import java.util.ArrayList;

public class Temp extends HashMapMerger {
    void main() {
        Integer x = 10;
        Integer y = 1000000;
        String test = "ee";

        ArrayList<Integer> ints = new ArrayList<Integer>() {{
            add(9);
            add(8);
            add(4);
        }};
        for (Integer i : ints) {
            System.out.println(i * i == 2 * i ? 10 : 2000);
        }

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

        if (true && true) {
            x = 500;
        } else if (true || false) {
            x = 3000;
        } else {
            x = 5000;
        }

        switch (x){
            case 10:
                x++;
                break;
            case 13:
                x--;
            default:
                x--;
        }
        /**
         * Exploring ANY behaviour:
         *  - with greedy finds the biggest match:
         *              y = 0;
         *             x = ++y;
         *             y = 0;
         *  Finds only the second one.
         *  - There's only the need for one any, even if several anys used in the same match. Example
         *      _any_test_.S(); ---  System.out.println("Testar cenas aleatorias");
         *      _var_y_ = 0; ---  y = 0;
         *      _any_test_.S(); ----  x = ++y;
         *  _any_test_ content: System(...), x = ++y;
         */
        for (int i = 0; i < z.size(); i++) {
            System.out.println("Testar cenas aleatorias");
            y = 0;
            x = ++y;
        }

        System.out.println("The final value of x is" + x);
    }
}
