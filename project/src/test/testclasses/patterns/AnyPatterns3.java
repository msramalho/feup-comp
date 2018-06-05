package testclasses.patterns;

import spoon.template.TemplateParameter;

public class AnyPatterns3 {

    int _var_x_;
    TemplateParameter<Void> _greedy_any_min4_max4_;
    TemplateParameter<Void> _greedy_any_min0_max4_;
    TemplateParameter<Void> _greedy_any_min1_max3_;
    TemplateParameter<Void> _greedy_any_min0_max2_;
    TemplateParameter<Void> _greedy_any_min2_max2_;
    TemplateParameter<Void> _greedy_any_min0max4_;

    public void minMax1() {
        while (_var_x_ == 1) {
            _greedy_any_min4_max4_.S();
        }
    }

    public void minMax2() {
        while (_var_x_ == 1) {
            _greedy_any_min0_max4_.S();
        }
    }

    public void minMax3() {
        while (_var_x_ == 1) {
            _greedy_any_min1_max3_.S();
        }
    }

    public void minMax4() {
        while (_var_x_ == 1) {
            _greedy_any_min0_max2_.S();
        }
    }

    public void minMax5() {
        while (_var_x_ == 1) {
            _greedy_any_min2_max2_.S();
        }
    }

    public void minMax6() {
        while (_var_x_ == 1) {
            _greedy_any_min0max4_.S();
        }
    }
}
