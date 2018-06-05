package testclasses.patterns;

public abstract class MethodsPatterns {

    int _var_x_;
    String _var_string_;
    abstract int _method_test1_();
    abstract int _method_test1();
    abstract String _method_test2_(String param);

    public void test1() {
        _var_x_ = _method_test1();
    }

    public void test2() {
        _var_x_ = _method_test1_();
    }

    public void test3() {
        String y = _method_test2_(_var_string_);
    }

    public void test4() {
        if (true) {
            _var_x_ = _method_test1_();
        } else {
            _var_x_ = _method_test1_();
        }
    }
}
