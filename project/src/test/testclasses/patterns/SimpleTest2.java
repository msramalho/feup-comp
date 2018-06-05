package testclasses.patterns;

public class SimpleTest2 {

    void simple1() {
        System.out.println("test");

        int i = 0;
        System.out.println("test");

        while(true) {
            System.out.println("test");
        }
    }

    void simple2() {
        String j;
        if (true) {
            j = "j";
        }

        int a = 10;
        for (int k = 0; k < a; ++k) {
            System.out.println("test4");

            if (true) {
                j = "j";
            }

            if (true) {
                j = "j";
                int i = 0;
            }
        }
    }

}
