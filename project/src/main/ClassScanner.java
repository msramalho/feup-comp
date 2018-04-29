package main;

import spoon.reflect.declaration.CtElement;
import spoon.reflect.visitor.CtScanner;
import util.Logger;
import worker.WorkerFactory;

import java.util.List;
import java.util.concurrent.ExecutorService;

public class ClassScanner extends CtScanner implements Runnable {

    private ExecutorService executorService;
    private List<WorkerFactory> workerFactories;

    private Node root;
    private Node current;

    private Logger logger = new Logger(this);

    ClassScanner(ExecutorService executorService, List<WorkerFactory> workerFactories, CtElement rootElement) {
        this.executorService = executorService;
        this.workerFactories = workerFactories;

        this.root = new Node(rootElement, null);
        this.current = root;
    }

    @Override
    protected void enter(CtElement e) {
        super.enter(e);
        // Update current node
        current = current.createChild(e);

        // Spawn future tasks
        for (WorkerFactory factory : workerFactories) {
            if (factory.matches(e)) {
                current.addFuture(executorService.submit(factory.getWorker(e)));
            }
        }
    }

    @Override
    protected void exit(CtElement e) {
        super.exit(e);

        current = current.getParent();
    }

    @Override
    public void run() {
        scan(this.root.getCtElement());
    }
}
