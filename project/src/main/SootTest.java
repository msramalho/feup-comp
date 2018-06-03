package main;

import soot.*;
import soot.jimple.toolkits.callgraph.CHATransformer;
import soot.jimple.toolkits.callgraph.CallGraph;
import soot.jimple.toolkits.callgraph.Targets;

import java.util.*;

public class SootTest {
    public static void main(String[] args) {
        List<String> argsList = new ArrayList<String>(Arrays.asList(args));
        argsList.addAll(Arrays.asList(new String[]{
                "-cp",      // class path
                "./src",
                "-w",       // whole-program
                "-main-class",
                "main.Main",    // main-class
                "main.Main"     // arg-class
        }));


        PackManager.v().getPack("wjtp").add(new Transform("wjtp.myTrans", new SceneTransformer() {

            @Override
            protected void internalTransform(String phaseName, Map options) {
                CHATransformer.v().transform();
                SootClass a = Scene.v().getSootClass("main.Main");

                SootMethod src = Scene.v().getMainClass().getMethodByName("main");
                CallGraph cg = Scene.v().getCallGraph();

                Iterator<MethodOrMethodContext> targets = new Targets(cg.edgesOutOf(src));
                while (targets.hasNext()) {
                    SootMethod tgt = (SootMethod)targets.next();
                    System.out.println(src + " may call " + tgt);
                }
            }

        }));

        args = argsList.toArray(new String[0]);

        soot.Main.main(args);
    }
}
