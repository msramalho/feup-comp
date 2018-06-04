package util;

import report.WorkerReport;

import java.util.function.Function;
import java.util.stream.Stream;

public abstract class Operable {
    public <T extends Number> T accept(Function<Stream<WorkerReport>, T> operation) {
        return operation.apply(getStream());
    }

    protected abstract Stream<WorkerReport> getStream();
}
