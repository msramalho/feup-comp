package testclasses.patterns;

import java.util.ArrayList;
import java.util.HashSet;

public class VarsTest {

    void vars1() {
        int x; x = 0;
        int y; y = 0;

        if (true) {
            x = 1;
        } else {
            y = 2;
        }

        if (true) {
            x = 1;
        } else {
            x = 2;
        }

        if (true)
            y = 1;
        else
            y = 2;
    }

    void vars2() {
        Object a;
        Object b = null;
        a = b;
        Object c = a;

        if (a != null) {
            String j = "j";
        }
        if (b != null) {
            String j = "j";
        }
    }

    void vars3() {
        ArrayList<String> test5v0 = new ArrayList<>();
        int  i;
        for (i = 0; test5v0.size() < 10; ++i) {
            System.out.println("test5");
        }

        HashSet<String> test5v1 = new HashSet<>();
        int j;
        for (j = 0; test5v1.size() < 10; ++j) {
            System.out.println("test5");
        }
    }
}
