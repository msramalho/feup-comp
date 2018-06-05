package util;

import report.WorkerReport;

import java.util.function.Function;
import java.util.stream.Stream;

/**
 * Represents an operable object, which accepts a given function over a stream of WorkerReports and returns a Number.
 */
public abstract class Operable {
    public <T extends Number> T accept(Function<Stream<WorkerReport>, T> operation) {
        return operation.apply(getStream());
    }

    protected abstract Stream<WorkerReport> getStream();
}
