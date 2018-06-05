package main;

import report.WorkerReport;
import spoon.reflect.declaration.CtElement;
import spoon.reflect.visitor.CtScanner;
import util.Logger;
import worker.Worker;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

public class ClassScanner extends CtScanner implements Callable {

    private ExecutorService executorService;
    private FactoryManager factoryManager;

    private Node root;
    private Node current;

    ClassScanner(ExecutorService executorService, FactoryManager factoryManager, CtElement rootElement) {
        this.executorService = executorService;
        this.factoryManager = factoryManager;

        this.root = new Node(rootElement);
        this.current = root;
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void enter(CtElement e) {
        // Update current node
        current = current.createChild(e);

        // Spawn new tasks
        factoryManager.makeWorkers(e).forEach((Worker worker) -> {
            Logger.print(this, "Worker spawned from factory: " + worker.getPatternName() + " at " + e.getPosition().getLine());
            current.addFuture(worker.getPatternName(), executorService.submit(worker), worker.getOperations());
        });

    }

    @Override
    protected void exit(CtElement e) {
        current = current.getParent();
    }

    @Override
    public Node call() throws Exception {
        Logger.print(this, factoryManager.report());
        scan(this.root.getCtElement());
        return root;
    }
}
