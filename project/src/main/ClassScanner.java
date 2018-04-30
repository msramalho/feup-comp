package main;

import spoon.reflect.declaration.CtElement;
import spoon.reflect.visitor.CtScanner;
import util.Logger;
import worker.StaticWorkerFactory;
import worker.Worker;
import worker.WorkerFactory;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;

public class ClassScanner extends CtScanner implements Runnable {

    private ExecutorService executorService;
    private FactoryManager factoryManager;

    private Node root;
    private Node current;

    ClassScanner(ExecutorService executorService, FactoryManager factoryManager, CtElement rootElement) {
        this.executorService = executorService;
        this.factoryManager = factoryManager;

        this.root = new Node(rootElement, null);
        this.current = root;

        scan(rootElement);
    }

    @Override
    protected void enter(CtElement e) {
        super.enter(e);
        // Update current node
        current = current.createChild(e);

        // Spawn new tasks
        WorkerFactory factory = factoryManager.getWorkerFactory(e);
        if (factory != null) {
            current.addFuture(executorService.submit(factory.makeWorker(e)));
        }

        System.out.println("entering " + e.getPosition());
    }

    @Override
    protected void exit(CtElement e) {
        super.exit(e);

        current = current.getParent();
        System.out.println("exiting " + e.getPosition());
    }

    @Override
    public void run() {
        scan(this.root.getCtElement());
    }
}
