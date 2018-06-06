package worker.workers;

import com.google.gson.Gson;
import org.apache.commons.lang3.RandomUtils;
import report.WorkerReport;
import spoon.reflect.code.CtExpression;
import spoon.reflect.declaration.CtElement;
import spoon.reflect.declaration.CtVariable;
import spoon.reflect.visitor.chain.CtConsumer;
import spoon.reflect.visitor.filter.AbstractFilter;
import spoon.reflect.visitor.filter.SiblingsFunction;
import spoon.reflect.visitor.filter.TypeFilter;
import spoon.support.reflect.code.*;
import worker.Worker;

import java.util.List;

public class W_possibleTernary extends Worker {

    public W_possibleTernary(CtElement element, String patternName) {
        super(element, patternName);
    }

    @Override
    protected AbstractFilter setFilter() {
        return new TypeFilter<>(CtLocalVariableImpl.class);
    }

    @Override
    public WorkerReport call() {
        CtElement element = getCtElement();

        SiblingsFunction siblings = new SiblingsFunction();
        PatternDetector myConsumer = new PatternDetector((CtLocalVariableImpl) element);

        siblings.mode(SiblingsFunction.Mode.NEXT);
        siblings.apply(element, myConsumer);

        return new WorkerReport(myConsumer.getMatchValue());
    }

    /**
     * Class used to consume the CtElement siblings of the declaration rootNode
     *
     * @see CtConsumer for more information
     */
    private class PatternDetector implements CtConsumer {

        /**
         * Class used for returning the resultant json object
         */
        class ReturnGson {
            boolean success;
            String declaration_pos;
            String conditional_pos;

            ReturnGson(boolean success, String declaration_pos, String conditional_pos) {
                this.success = success;
                this.declaration_pos = declaration_pos;
                this.conditional_pos = conditional_pos;
            }
        }

        ReturnGson returnGson = new ReturnGson(false, null, null);

        private CtVariable declaredVar;

        PatternDetector(CtLocalVariableImpl declaredVar) {
            this.declaredVar = declaredVar;
        }

        @Override
        public void accept(Object obj) {
            if (!(obj instanceof CtIfImpl))
                return;

            CtBlockImpl ifTrue = ((CtIfImpl) obj).getThenStatement();
            CtBlockImpl ifFalse = ((CtIfImpl) obj).getElseStatement();

            // In case there isn't one of the statements
            if (ifTrue == null || ifFalse == null)
                return;

            if (isDeclareStmt(ifTrue) && isDeclareStmt(ifFalse))
                returnGson = new ReturnGson(
                        true,
                        declaredVar.getPosition().toString(),
                        ((CtIfImpl) obj).getPosition().toString()
                );
        }

        /**
         * Checks if inside the given statement there is only an assignment to the variable stored on field declaredVar
         *
         * @param block The block inside one of the if statements
         * @return True if inside there is only one statement, being an assignment to the stored variable
         */
        private boolean isDeclareStmt(CtBlockImpl block) {

            List<CtElement> statements = block.getStatements();
            if (statements.size() != 1 || !(statements.get(0) instanceof CtAssignmentImpl))
                return false;

            CtExpression stmt = ((CtAssignmentImpl) statements.get(0)).getAssigned();
            if (!(stmt instanceof CtVariableWriteImpl))
                return false;

            CtVariable variable = ((CtVariableWriteImpl) stmt).getVariable().getDeclaration();

            return variable.equals(getCtElement());
        }

        public String getGsonResult() {
            return new Gson().toJson(returnGson);
        }

        public int getMatchValue() {
            return returnGson.success ? 1 : 0;
        }

    }

}
