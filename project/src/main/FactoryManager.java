package main;

import spoon.reflect.declaration.CtElement;
import worker.WorkerFactory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class FactoryManager {
    private Map<String, WorkerFactory> workerFactories = new HashMap<>();

    FactoryManager() { }


    void addWorkerFactory(WorkerFactory workerFactory) {
        workerFactories.put(workerFactory.getType().getName(), workerFactory);
    }

    ArrayList<WorkerFactory> getWorkerFactory(CtElement elem) {
        ArrayList<WorkerFactory> factories = new ArrayList<>();
        for (Map.Entry<String, WorkerFactory> entry : workerFactories.entrySet()) {
            WorkerFactory factory = entry.getValue();
            if (factory.matches(elem))
                factories.add(factory);
        }
        return factories;
    }

    // public Worker makeWorker(CtElement elem) throws NullPointerException {
    //     return getWorkerFactory(elem).makeWorker(elem);
    // }

    Map<String, WorkerFactory> getWorkerFactories() { return workerFactories; }
}
