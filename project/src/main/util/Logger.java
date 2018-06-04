package util;

public class Logger {
    private Object source; // the object that will call Logger methods


    public Logger(Object source) { this.source = source; }

    public void print(String message) { System.out.println(format(message)); }

    private String format(String message) {
        // https://stackoverflow.com/a/421338/6196010
        StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();

        // the 3 means this is called from 3 -> 2 (print) -> 1 (format) ->  0 (getStackTrace)
        StackTraceElement relevant = stackTraceElements[3];
        return String.format("[%s->%s:%3d] - %s", source.getClass().getName(), relevant.getMethodName(), relevant.getLineNumber(), message);
    }

}