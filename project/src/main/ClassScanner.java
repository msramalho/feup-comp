package main;

import report.Report;
import spoon.reflect.declaration.CtElement;
import spoon.reflect.visitor.CtScanner;
import util.Logger;
import worker.StaticWorkerFactory;
import worker.Worker;
import worker.WorkerFactory;

import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;

public class ClassScanner extends CtScanner implements Callable {

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
        // Update current node
        current = current.createChild(e);

        // Spawn new tasks
        WorkerFactory factory = factoryManager.getWorkerFactory(e);
        if (factory != null) {
            // TODO: decide if Node is needed - this was designed to perpetuate node with report
            factory.addFuture(current.addFuture(executorService.submit(factory.makeWorker(e))));
        }

        System.out.println("entering " + e.getPosition());
    }

    @Override
    protected void exit(CtElement e) {
        current = current.getParent();
        System.out.println("exiting " + e.getPosition());
    }

    @Override
    public Object call() throws Exception {
        scan(this.root.getCtElement());
        return new Report(factoryManager.getWorkerFactories().values());
    }
}
