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
            cenas();
            y = 0;
            x = ++y;
            y = 0;
            System.out.println("Middle test");
            y = 0;
            x = ++y;
            System.out.println("Last test");
        }

        if (true)
            System.out.println("teste de pattern com mais do que um nó");

        System.out.println("The final value of x is" + x);
    }

    public int cenas() {
        for (int i = 0; i < 10; i++) {
            if (true) return 0;
            while (true) {
                Integer[] ss = {1, 2, 3};
                for (Integer s : ss) {
                    for (int j = 0; j < s; j++) {
                        return j * j;
                    }
                }
            }
        }

        for (int i = 0; i < 10; i++) {
            return i * i * i;
        }
        return 10;
    }
}
