package util;

public class Logger {
    private Object source; // the object that will call Logger methods


    public Logger(Object source) {
        this.source = source;
    }

    public void print(String message) {
        System.out.println(format(message));
    }

    private String format(String message) {
        // https://stackoverflow.com/a/421338/6196010
        StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
        // the 3 means this is called from 3 -> 2 (print) -> 1 (format) ->  0 (getStackTrace)
        StackTraceElement relevant = stackTraceElements[3];
        return String.format("[%s->%s:%3d] - %s", source.getClass().getName(), relevant.getMethodName(), relevant.getLineNumber(), message);
    }

    /*
    public void log(String message){
        FileOutputStream logStream;
        try {
            logStream = new FileOutputStream(logFile, true);
        } catch (FileNotFoundException e1) {
            System.err.println("LOGGER: Could not log to file " + logFile.getName());
            return;
        }

        String timeStamp = new SimpleDateFormat("yyyy_MM_dd-HH_mm_ss").format(new Date(System.currentTimeMillis()));
        try {
            logStream.write((timeStamp + " - " + toLog + "\n").getBytes());
        } catch (IOException e) {
            System.err.println("[LOGGER] -  Could not write to log.");
        }
    }*/
}