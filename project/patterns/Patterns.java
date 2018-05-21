public class Patterns {

    Object _var_x_;
    Object _var_y_;

    public void possibleTernaryOperator() {
        if (true) {
            _var_y_ = 500;
        } else {
            _var_y_ = 1000;
        }
    }

    //
    // public void rangeBasedFor() {
    //     for (int i = 0; i < _var_x.length; i++) {
    //         _any();
    //     }
    // }
    //
    // public void testEmptyError() { }

    public void test() {
        if (_var_x_ == null)
            return ;
    }

}
