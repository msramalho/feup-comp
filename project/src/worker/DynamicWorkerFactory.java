package worker;

import spoon.reflect.declaration.CtElement;

public class DynamicWorkerFactory implements WorkerFactory {

    @Override
    public Worker makeWorker(CtElement ctElement) {
        return null;
    }

    @Override
    public boolean matches(CtElement ctElement) {
        return false;
    }

    @Override
    public Class<? extends CtElement> getType() {
        return null;
    }
}
