package testclasses.patterns;

import spoon.template.TemplateParameter;

public class AnyPatterns2 {

    int _var_x_;
    TemplateParameter<Void> _lazy_any_max1_;
    TemplateParameter<Void> _lazy_any_max0_;
    TemplateParameter<Void> _greedy_any_max4_;
    TemplateParameter<Void> _greedy_any_max0;

    public void max1() {
        while (_var_x_ == 1) {
            _greedy_any_max4_.S();
        }
    }

    public void max2() {
        while (_var_x_ == 1) {
            _lazy_any_max1_.S();
        }
    }

    public void max3() {
        while (_var_x_ == 1) {
            _lazy_any_max0_.S();
        }
    }

    public void max4() {
        while (_var_x_ == 1) {
            _greedy_any_max0.S();
        }
    }
}
