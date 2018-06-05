package testclasses;

public class ExtractorTest3 {

    public void test1() {
        System.out.println("test");
    }

    public void test2() {
        System.out.println("test");
    }

    public class Intruder {

        public void test5() {
            while(true) {

            }
        }

        public void test6() {
            for (int i = 0; true; i++) {

            }
        }
    }

    public void test3() {
        if (true)
            return ;
    }

    public void test4() {
        int i = 0;
    }
}
