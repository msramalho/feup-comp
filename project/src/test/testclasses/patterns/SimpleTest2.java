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
        if (true) {
            String j = "j";
        }

        for (int k = 0; k < 10; ++k) {
            System.out.println("test3");

            if (true) {
                String c = "j";
            }

            if (true) {
                String j = "j";
                int i = 0;
            }
        }
    }

}
