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

    private Logger logger = new Logger(this);

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
            logger.print("there goes a Worker from factory: " + factory.getClass());
            current.addFuture(executorService.submit(factory.makeWorker(e)));
        }
    }

    @Override
    protected void exit(CtElement e) {
        current = current.getParent();
    }

    @Override
    public Object call() throws Exception {
        logger.print("Number of factories: " + factoryManager.getWorkerFactories().size());
        scan(this.root.getCtElement());
        return root;
    }
}
