package main;

import report.PatternReport;
import report.Report;
import report.WorkerReport;
import spoon.reflect.declaration.CtElement;

import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class Node {
    private Collection<Node> children;
    private Node parent;

    private CtElement ctElement;
    private HashMap<String, Future<WorkerReport>> nodeFutures;

    private Node() {
        this.children = new LinkedList<>();
        this.nodeFutures = new HashMap<>();
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
     * @param patternName
     * @param future
     * @return Future so that this Future pointer can be used by other apps
     */
    Future addFuture(String patternName, Future<WorkerReport> future) {
        nodeFutures.put(patternName, future); // the same pattern cannot be triggered twice for the same node
        return future;
    }

    Node createChild(CtElement e) {
        Node child = new Node(e, this);
        children.add(child);
        return child;
    }


    private Report getOwnReport() throws ExecutionException, InterruptedException {
        HashSet<PatternReport> prs = new HashSet<>();
        for (Map.Entry<String, Future<WorkerReport>> entry : nodeFutures.entrySet()) {
            PatternReport pr = new PatternReport(entry.getKey());
            pr.addReport(entry.getValue().get());
            prs.add(pr);
        }
        return new Report(prs);
    }

    Report getReport() throws ExecutionException, InterruptedException {
        Report res = getOwnReport();
        for (Node child : children)
            res = res.merge(child.getReport());
        return res;
    }
}
