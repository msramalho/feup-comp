
import spoon.template.TemplateParameter;
import spoon.template.ExtensionTemplate;
import spoon.template.Parameter;
import spoon.support.reflect.code.CtAssignmentImpl;

import spoon.reflect.code.CtExpression;
import spoon.reflect.code.CtVariableAccess;
import spoon.reflect.code.CtAssignment;
import java.util.Collection;

public class Patterns extends ExtensionTemplate {

    public TemplateParameter<Object> _col_;
    public TemplateParameter<CtExpression> _exp1_;
    public TemplateParameter<CtExpression> _exp2_;

    @Parameter
    int _var_x_;

    /*public void possibleTernaryOperator() {

        if (true) {
            _var_x_ = 500;
        } else {
            _var_x_ = 1000;
        }
    }*/

    //
    // public void rangeBasedFor() {
    //     for (int i = 0; i < _var_x.length; i++) {
    //         _any();
    //     }
    // }
    //
    // public void testEmptyError() { }

    public void test() {
        if (_col_.S() == null)
            return ;
    }

}
