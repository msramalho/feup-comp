package main;

import spoon.reflect.declaration.CtElement;
import worker.Worker;
import worker.WorkerFactory;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class FactoryManager {
    private Map<String, WorkerFactory> workerFactories;

    public FactoryManager() {
        this.workerFactories = new HashMap<>();
    }

    public void addWorkerFactories(Collection<WorkerFactory> factories) {
        for (WorkerFactory factory : factories) {
            this.addWorkerFactory(factory);
        }
    }

    public WorkerFactory addWorkerFactory(WorkerFactory workerFactory) {
        return workerFactories.put(workerFactory.getType().getName(), workerFactory);
    }

    // TODO use this in the future, need to test
    //    public WorkerFactory getWorkerFactory(CtElement elem) {
    //        return workerFactories.getOrDefault(
    //                elem.getClass().getName(),
    //                null
    //        );
    //    }

    public WorkerFactory getWorkerFactory(CtElement elem) {
        for (Map.Entry<String, WorkerFactory> entry : workerFactories.entrySet()) {
            WorkerFactory factory = entry.getValue();
            if (factory.matches(elem))
                return factory;
        }
        return null;
    }

    public Worker makeWorker(CtElement elem) throws NullPointerException {
        return getWorkerFactory(elem).makeWorker(elem);
    }

    public Map<String, WorkerFactory> getWorkerFactories() {
        return workerFactories;
    }
}
