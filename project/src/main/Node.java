package main;

import report.PatternReport;
import report.Report;
import report.WorkerReport;
import spoon.reflect.declaration.CtElement;

import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.function.Function;
import java.util.stream.Stream;

public class Node {
    private Collection<Node> children;
    private Node parent;

    private CtElement ctElement;
    private Map<String, Future<WorkerReport>> nodeFutures;

    /**
     * This node's report pattern operations.
     * Maps (String patternName) -> (String opName) -> (Op operation)
     */
    private Map<String, Map<String, Function<Stream<WorkerReport>, Number>>> patternOps;

    private Node() {
        this.children = new LinkedList<>();
        this.nodeFutures = new HashMap<>();
        this.patternOps = new HashMap<>();
    }

    Node(CtElement elem) {
        this();
        this.ctElement = elem;
    }

    private Node(CtElement elem, Node parent) {
        this();
        this.ctElement = elem;
        this.parent = parent;
    }

    CtElement getCtElement() { return ctElement; }

    Node getParent() { return parent; }

    Collection<Node> getChildren() { return children; }

    /**
     * Adds a given future WorkerReport to this node's Report.
     * @param patternName the pattern's name
     * @param future the future WorkerReport
     * @param operations this future's operations.
     * @return Future so that this Future pointer can be used by other apps
     */
    Future addFuture(String patternName, Future<WorkerReport> future,
                     Map<String, Function<Stream<WorkerReport>, Number>> operations) {
        // Note: the same pattern cannot be triggered twice for the same node
        nodeFutures.put(patternName, future);
        patternOps.put(patternName, operations);
        return future;
    }

    Node createChild(CtElement e) {
        Node child = new Node(e, this);
        children.add(child);
        return child;
    }

    private Report getOwnReport() throws ExecutionException, InterruptedException {
        HashSet<PatternReport> reportSet = new HashSet<>();

        for (Map.Entry<String, Future<WorkerReport>> entry : nodeFutures.entrySet()) {
            String patternName = entry.getKey();

            PatternReport patternReport = new PatternReport(patternName);
            patternReport.addOperations(patternOps.get(patternName));

            WorkerReport workerReport = entry.getValue().get();
            if (workerReport == null) {
                System.err.println("Node's self Worker returned a NULL Report.");
                continue;
            }

            patternReport.addWorkerReport(workerReport);
            reportSet.add(patternReport);
        }
        return Report.fromPatternReports(reportSet);
    }

    Report getReport() throws ExecutionException, InterruptedException {
        Report res = getOwnReport();
        for (Node child : children)
            res = res.merge(child.getReport());
        return res;
    }
}
