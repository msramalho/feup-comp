package main;

import spoon.reflect.declaration.CtElement;
import util.Report;

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.concurrent.Future;

public class Node {
    private Collection<Node> children;
    private Node parent;

    private Collection<Future> nodeFutures;
    private CtElement ctElement;

    private Node() {
        this.children = new LinkedList<>();
        this.nodeFutures = new HashSet<>();
    }

    Node(CtElement elem, Node parent) {
        this();
        this.ctElement = elem;
        this.parent = parent;
    }

    CtElement getCtElement() {
        return ctElement;
    }

    void addFuture(Future future) {
        nodeFutures.add(future);
    }

    Collection<Future> getNodeFutures() {
        return nodeFutures;
    }

    Node createChild(CtElement e) {
        Node child = new Node(e, this);
        children.add(child);
        return child;
    }

    Collection<Node> getChildren() {
        return children;
    }

    Node getParent() {
        return parent;
    }

    Future<Report> getResult() {
        // TODO recursively call getResults - futures from children -- sum them, and then sum them to our futures
        // TODO return result as future ?
        return null;
    }
}
