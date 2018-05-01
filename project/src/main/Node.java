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

    private HashMap<String, Future> nodeFutures;
    private String depth = "  ";

    private Node() {
        this.children = new LinkedList<>();
        this.nodeFutures = new HashMap<>();
    }

    public Node(CtElement elem) {
        this();
        this.ctElement = elem;
    }

    Node(CtElement elem, Node parent) {
        this();
        this.ctElement = elem;
        this.parent = parent;
        depth = parent.depth + "  ";
    }

    CtElement getCtElement() {
        return ctElement;
    }

    /**
     * @param patternName
     * @param future
     * @return Future so that this Future pointer can be used by other apps
     */
    Future addFuture(String patternName, Future future) {
        nodeFutures.put(patternName, future); // the same pattern cannot be triggered twice for the same node
        return future;
    }

    Node createChild(CtElement e) {
        Node child = new Node(e, this);
        children.add(child);
        return child;
    }

    Collection<Node> getChildren() { return children; }

    Node getParent() { return parent; }

    private Report getOwnReport() throws ExecutionException, InterruptedException {
        HashSet<PatternReport> prs = new HashSet<>();
        for (Map.Entry<String, Future> entry : nodeFutures.entrySet()) {
            PatternReport pr = new PatternReport(entry.getKey());
            pr.addReport((WorkerReport) entry.getValue().get());
            prs.add(pr);
        }
        return new Report(prs);
    }

    Report getReport() throws ExecutionException, InterruptedException {
        Report res = getOwnReport();
        // System.out.println(depth + "Getting report (" + nodeFutures.size() + " futures) for [" + ctElement.getClass() + "] " + ctElement.toString() + "\n     " + res.toString());
        for (Node child : children) {
            Report temp = child.getReport();
            if (temp.getPatternReports().size() > 0) {
                System.out.println(depth + "child with: " + temp.toString());
            }

            res = res.merge(temp);
        }
        System.out.println(depth + "---");
        // System.out.println(depth + "Getting report (" + nodeFutures.size() + " futures) for [" + ctElement.getClass() + "] " + ctElement.toString() + "\n     " + res.toString());

        return res;
    }
}
