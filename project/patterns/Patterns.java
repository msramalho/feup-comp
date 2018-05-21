import java.util.Collection;

public class Patterns {

    Object _var_x_;
    Integer _var_y_;
    Collection<Object> _var_z_;

    public void possibleTernaryOperator() {
        if (true) {
            _var_y_ = 500;
        } else {
            _var_y_ = 1000;
        }
    }


    public void rangeBasedFor() {
        for (int i = 0; i < _var_z_.size(); i++) {
            //_any();
        }
    }

    // public void testEmptyError() { }

    public void test() {
        if (_var_x_ == null)
            return ;
    }

}
