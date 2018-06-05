package worker;

import spoon.reflect.declaration.CtElement;
import spoon.reflect.declaration.CtMethod;

public class DynamicWorkerFactory extends WorkerFactory {

    private final Class<?> type;
    private final CtElement patternElement;
    private final DynamicWorker filterWorker;

    public DynamicWorkerFactory(Class<?> type, CtElement patternElement) {
        super(getPatternName(patternElement));
        this.type = type;
        this.patternElement = patternElement;
        this.filterWorker = new DynamicWorker(null, null, patternElement);
    }

    public static String getPatternName(CtElement block) {
        CtElement parent = block.getParent();

        if (parent instanceof CtMethod)
            return ((CtMethod) parent).getSimpleName();
        else
            return "Unknown Method";
    }

    @Override
    public Worker makeWorker(CtElement ctElement) {
        return new DynamicWorker(ctElement, getPatternName(), patternElement);
    }

    @Override
    public boolean matches(CtElement ctElement) { return filterWorker.matches(ctElement); }

    @Override
    public Class<?> getType() { return type; }
}
