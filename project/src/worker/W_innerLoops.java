package worker;

import main.Configuration;
import spoon.reflect.declaration.CtElement;
import spoon.reflect.visitor.filter.TypeFilter;
import spoon.support.reflect.code.CtForEachImpl;

public class W_innerLoops extends Worker {

    public W_innerLoops(Configuration configuration, CtElement element) {
        super(configuration, element);
        logger.print("proof of concept constructor");
    }

    @Override
    protected void setFilter() {
        filter = new TypeFilter(CtForEachImpl.class);
    }

    @Override
    public Object call() throws Exception {
        logger.print("proof of concept run for the ForEachLoops innerLoops Count");
        return new Result(9000);
    }
}
