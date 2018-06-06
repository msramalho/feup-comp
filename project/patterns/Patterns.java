import java.util.Collection;
import spoon.template.TemplateParameter;

public abstract class Patterns {

    Object _var_x_;
    Integer _var_y_;
    Boolean _var_bool_;
    Collection<Object> _var_z_;
    TemplateParameter<Void> _lazy_any_;
    TemplateParameter<Void> _greedy_any_;
    TemplateParameter<Void> _greedy_any_min1_;

    abstract Object _method_assign_();

    public void methodAssignment() {
        _var_x_ = _method_assign_();
    }

    public void possibleTernaryOperator() {
        if (true) {
            _var_y_ = 1;
        } else {
            _var_y_ = 2;
        }
    }

    public void nestedIfs() {
        if (_var_bool_) {
            _lazy_any_.S();
            if (_var_bool_) {
                _greedy_any_.S();
            }
            _greedy_any_.S();
        }
    }

    public void rangeBasedFor() {
        for (int i = 0; i < _var_z_.size(); i++) {
            _lazy_any_.S();
            _var_y_ = 0;
            _greedy_any_min1_.S();
        }
    }

}
