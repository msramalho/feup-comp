package util;

public class Logger {

    private static boolean isSilenced = false;

    public static void setSilence(boolean silence) {
        isSilenced = silence;
    }

    public static void print(Object caller, String message) {
        if (! isSilenced)
            System.out.println(format(caller, message));
    }

    private static String format(Object caller, String message) {
        // https://stackoverflow.com/a/421338/6196010
        StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();

        // the 3 means this is called from 3 -> 2 (print) -> 1 (format) ->  0 (getStackTrace)
        StackTraceElement relevant = stackTraceElements[3];
        return String.format("[%s->%s:%3d] - %s", caller.getClass().getName(), relevant.getMethodName(), relevant.getLineNumber(), message);
    }

}