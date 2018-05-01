package worker;

import spoon.reflect.declaration.CtElement;
import spoon.reflect.visitor.filter.AbstractFilter;
import spoon.reflect.visitor.filter.TypeFilter;
import spoon.support.reflect.code.CtIfImpl;

public class W_testIterator extends DynamicWorker {

    public W_testIterator(CtElement element, String patternName) {
        super(element, element, patternName); // just for testing purposes!!
    }

    @Override
    protected AbstractFilter setFilter() {
        return new TypeFilter<>(CtIfImpl.class);
    }
}
