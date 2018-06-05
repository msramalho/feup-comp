package testclasses.patterns;

public class AnyTest3 {

    public int helper() {
        return 1;
    }

    public void any1() {
        int x = 0;

        while (x == 1) {
            if (x == 0) {
                int i = 0;
                while (x == 1) {
                    if (x == 0) {
                        System.out.println("secondTry");
                        System.out.println("secondTry");
                    } else {
                        int z = helper();
                    }
                    String a = "b";
                }
            } else {
                int y = helper();
            }
            String a = "b";
        }
    }
}
