package testclasses.patterns;

public class AnyTest3 {

    public int helper() {
        return 1;
    }

    public void any1() {
        int x = 0;

        while (x == 1) {
            while(x == 1) {
                System.out.println("any1");
                System.out.println("any1");
                x = helper();
            }
            System.out.println("any1");
            x = helper();
        }
    }
}
