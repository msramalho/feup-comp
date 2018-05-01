
public static class Patterns {

    public void possibleTernaryOperator() {
        if (_any()) {
            _var_x = any();
        } else {
            _var_x = any();
        }
    }

    public void rangeBasedFor() {
        for (int i = 0; i < _var_x.length; i++) {
            _any();
        }
    }

    public void testIterator(){
        if(_any());
    }
}
