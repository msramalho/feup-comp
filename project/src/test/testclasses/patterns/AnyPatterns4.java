package testclasses.patterns;

import spoon.template.TemplateParameter;

public abstract class AnyPatterns4 {

    int _var_x_;
    TemplateParameter<Void> _greedy_any_min4_max4_;
    TemplateParameter<Void> _greedy_any_min0_max4_;
    TemplateParameter<Void> _greedy_any_min1_max3_;
    TemplateParameter<Void> _greedy_any_min0_max2_;
    TemplateParameter<Void> _greedy_any_min2_max2_;
    TemplateParameter<Void> _greedy_any_min0max4_;


    public abstract int _method_test_();

    public void complex1() {
        while (_var_x_ == 1) {
            if (_var_x_ == 0) {
                _greedy_any_min2_max2_.S();
            } else {
                int _var_y_ = _method_test_();
            }
            String a = "b";
        }
    }

    /*public void complex1() {
        _var_x_ = _method_test_();
    }*/
}
