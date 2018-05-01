package worker;

import spoon.reflect.declaration.CtElement;

public class DynamicWorkerFactory extends WorkerFactory {

    private Class<?> type;
    private CtElement patternElement;
    private DynamicWorker filterWorker;

    public DynamicWorkerFactory(Class<?> type, CtElement patternElement) {
        super("TODO "); // TODO: get pattern name from the method
        this.type = type;
        this.patternElement = patternElement;
        this.filterWorker = new DynamicWorker(patternElement, null, null);
    }

    @Override
    public Worker makeWorker(CtElement ctElement) {
        return new DynamicWorker(patternElement, ctElement, patternName);
    }

    @Override
    public boolean matches(CtElement ctElement) {
        return filterWorker.matches(ctElement);
    }

    @Override
    public Class<?> getType() {
        return type;
    }
}
