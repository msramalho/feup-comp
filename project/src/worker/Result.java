package worker;

public class Result {
    //TODO: create this in such a way that it is returnable by Callables as Future and is easilly combined for further stuff
    int demo;
    public Result(int d) {
        demo = d;
    }

    @Override
    public String toString() {
        return "Result{" +
                "demo=" + demo +
                '}';
    }
}
