package main;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import spoon.reflect.declaration.CtElement;
import worker.Worker;
import worker.WorkerFactory;

import java.util.*;

public class FactoryManager {
    private Multimap<String, WorkerFactory> workerFactories;

    public FactoryManager() {
        workerFactories = HashMultimap.create();
    }

    public void addWorkerFactory(WorkerFactory workerFactory) {
        workerFactories.put(workerFactory.getType().getName(), workerFactory);
    }

    // TODO test. Please do not delete. Performance improvement.
//    Collection<WorkerFactory> getWorkerFactories(CtElement elem) {
//        return workerFactories.get(elem.getClass().getName());
//    }

    Collection<WorkerFactory> getWorkerFactories(CtElement elem) {
        List<WorkerFactory> matchedFactories = new ArrayList<>();
        for (Map.Entry<String, WorkerFactory> entry : workerFactories.entries()) {
            WorkerFactory factory = entry.getValue();
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
