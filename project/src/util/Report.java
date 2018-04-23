package util;

public class Report {
    //TODO: create this in such a way that it is returnable by Callables as Future and is easilly combined for further stuff
    int demo;
    public Report(int d) {
        demo = d;
    }

    @Override
    public String toString() {
        return "Report{" +
                "demo=" + demo +
                '}';
    }
}
