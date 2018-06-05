package main;

import spoon.reflect.declaration.CtElement;
import worker.Worker;
import worker.WorkerFactory;

import java.util.*;

class FactoryManager {
    private Set<WorkerFactory> workerFactories;

    FactoryManager() {
        workerFactories = new HashSet<>();
    }

    void addWorkerFactory(WorkerFactory workerFactory) {
        workerFactories.add(workerFactory);
    }

    Collection<WorkerFactory> getWorkerFactories(CtElement elem) {
        List<WorkerFactory> matchedFactories = new ArrayList<>();
        for (WorkerFactory factory : workerFactories) {
            if (factory.matches(elem))
                matchedFactories.add(factory);
        }
        return matchedFactories;
    }

    Collection<Worker> makeWorkers(CtElement elem) {
        List<Worker> workers = new LinkedList<>();
        for (WorkerFactory factory : getWorkerFactories(elem)) {
            workers.add(factory.makeWorker(elem));
        }
        return workers;
    }

    String report() {
        return "Total number of factories: " + workerFactories.size();
    }
}
