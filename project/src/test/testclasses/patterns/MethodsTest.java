package testclasses.patterns;

public class MethodsTest {

    public int helperA() {
        return 1;
    }

    public int helperB() {
        return 2;
    }

    public String helper2(String str) {
        return str;
    }

    public void methods1() {
        int x = helperA();
        String test = "test";
        String y = helper2(test);

        if (true) {
            x = helperA();
        } else {
            x = helperA();
        }

        int z;
        if (true) {
            x = helperA();
        } else {
            x = helperB();
        }
    }
}
