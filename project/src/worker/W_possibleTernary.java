package worker;

import com.google.gson.Gson;
import main.Configuration;
import spoon.reflect.code.CtExpression;
import spoon.reflect.declaration.CtElement;
import spoon.reflect.declaration.CtVariable;
import spoon.reflect.visitor.chain.CtConsumer;
import spoon.reflect.visitor.filter.SiblingsFunction;
import spoon.reflect.visitor.filter.TypeFilter;
import spoon.support.reflect.code.*;

import java.util.List;

public class W_possibleTernary extends Worker {

    public W_possibleTernary(Configuration configuration, CtElement element) {
        super(configuration, element);
    }

    @Override
    protected void setFilter() {
        filter = new TypeFilter(CtLocalVariableImpl.class);
    }

    @Override
    public Object call() throws Exception {
        SiblingsFunction siblings = new SiblingsFunction();
        Verifier myConsumer = new Verifier((CtLocalVariableImpl) element);

        siblings.mode(SiblingsFunction.Mode.NEXT);
        siblings.apply(element, myConsumer);

        //return new Result(myConsumer.getGsonResult());
        return new Result(1);
    }


    private class Verifier implements CtConsumer {

        CtVariable declaredVar;

        public Verifier(CtLocalVariableImpl declaredVar) {
            this.declaredVar = declaredVar;
        }

        @Override
        public void accept(Object obj) {
            if (! (obj instanceof CtIfImpl))
                return;

            CtBlockImpl ifTrue = ((CtIfImpl) obj).getThenStatement();
            CtBlockImpl ifFalse = ((CtIfImpl) obj).getElseStatement();
            List<CtElement> teste = ifTrue.getElements(null);

            // In case there isn't one of the statements
            if (ifTrue == null || ifFalse == null)
                return;

            if (isDeclareStmt(ifTrue) && isDeclareStmt(ifFalse))
                System.out.println("Got a match sooon");

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
            if (! (stmt instanceof CtVariableWriteImpl))
                return false;

            CtVariable variable = ((CtVariableWriteImpl) stmt).getVariable().getDeclaration();

            return variable.equals(element);
        }

        public Gson getGsonResult() {
            return new Gson();
        }

    }

}
