package worker;

import spoon.reflect.declaration.CtElement;
import spoon.reflect.visitor.Filter;
import spoon.reflect.visitor.filter.AbstractFilter;
import spoon.reflect.visitor.filter.TypeFilter;
import spoon.support.reflect.code.CtIfImpl;

public class W_testIterator extends AutoWorker {

    public W_testIterator(CtElement element) {
        super(element);
    }

    @Override
    protected AbstractFilter setFilter() {
        return new TypeFilter<>(CtIfImpl.class);
    }
}
