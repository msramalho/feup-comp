package testclasses.patterns;

import java.util.Collection;

public class VarsPatterns {

    Object _var_a_;
    Object _var_b_;
    int _var_i_;
    Collection<Object> _var_collection_;

    public void test1() {
        _var_a_ = _var_b_;
    }

    public void test2() {
        _var_i_ = 0;
    }

    public void test3() {
        if (true) {
            _var_i_ = 1;
        } else {
            _var_i_ = 2;
        }
    }

    public void test4() {
        if (_var_a_ != null) {
            String j = "j";
        }
    }

    public void test5() {
        for (_var_i_ = 0; _var_collection_.size() < 10; ++_var_i_) {
            System.out.println("test5");
        }
    }
}
