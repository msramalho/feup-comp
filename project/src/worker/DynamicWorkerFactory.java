package worker;

import spoon.reflect.code.CtBlock;
import spoon.reflect.declaration.CtElement;
import spoon.reflect.declaration.CtMethod;

public class DynamicWorkerFactory extends WorkerFactory {

    private Class<?> type;
    private CtBlock patternElement;
    private DynamicWorker filterWorker;

    public DynamicWorkerFactory(Class<?> type, CtBlock patternElement) {
        super(getPatternName(patternElement));
        this.type = type;
        this.patternElement = patternElement;
        this.filterWorker = new DynamicWorker(patternElement, null, null);
    }

    private static String getPatternName(CtBlock block) {
        CtElement parent = block.getParent();

        if (parent instanceof CtMethod)
            return ((CtMethod) parent).getSimpleName();
        else
            return "Unknown Method";
    }

    @Override
    public Worker makeWorker(CtElement ctElement) { return new DynamicWorker(patternElement, ctElement, patternName); }

    @Override
    public boolean matches(CtElement ctElement) { return filterWorker.matches(ctElement); }

    @Override
    public Class<?> getType() { return type; }
}
