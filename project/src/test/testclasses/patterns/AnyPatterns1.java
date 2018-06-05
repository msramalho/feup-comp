package testclasses.patterns;

import spoon.template.TemplateParameter;

public class AnyPatterns1 {

    int _var_x_;
    TemplateParameter<Void> _lazy_any_;
    TemplateParameter<Void> _greedy_any_;
    TemplateParameter<Void> _any_;
    TemplateParameter<Void> _lazy_any_min1_;
    TemplateParameter<Void> _lazy_any_min0_;
    TemplateParameter<Void> _greedy_any_min4_;
    TemplateParameter<Void> _greedy_any_min0;

    public void test1() {
        if (true) {
            _lazy_any_.S();
            _var_x_ = 0;
        }
    }

    public void test2() {
        if (true) {
            _greedy_any_.S();
            _var_x_ = 0;
        }
    }

    public void test3() {
        if (true) {
            _any_.S();
            _var_x_ = 0;
        }
    }

    public void min1() {
        while (_var_x_ == 1) {
            _greedy_any_min4_.S();
        }
    }

    public void min2() {
        while (_var_x_ == 1) {
            _lazy_any_min1_.S();
        }
    }

    public void min3() {
        while (_var_x_ == 1) {
            _lazy_any_min0_.S();
        }
    }

    public void min4() {
        while (_var_x_ == 1) {
            _greedy_any_min0.S();
        }
    }

}
