import java.util.Collection;
import spoon.template.TemplateParameter;

public class Patterns {

    Object _var_x_;
    Integer _var_y_;
    Collection<Object> _var_z_;
    TemplateParameter<Void> _lazy_any_;
    TemplateParameter<Void> _greedy_any_;

    public void possibleTernaryOperator() {
        if (true) {
            _var_y_ = 500;
        } else {
            _var_y_ = 1000;
        }
    }


    public void rangeBasedFor() {
        for (int i = 0; i < _var_z_.size(); i++) {
            _lazy_any_.S();
            _var_y_ = 0;
            _greedy_any_.S();
        }
    }

    // public void testEmptyError() { }

    /*public void test() {
        if (_var_x_ == null)
            return ;
    }*/

}
