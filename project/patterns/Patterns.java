
import spoon.template.TemplateParameter;

import java.util.Collection;

public class Patterns {

    public TemplateParameter<Object> _col_;

    /*public void possibleTernaryOperator() {
        if (_any_()) {
            _var_x = _any_();
        } else {
            _var_x = _any_();
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
