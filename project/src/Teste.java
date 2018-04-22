public class Teste {
    private Object source;

    public Teste(Object source) {
        this.source = source;
    }

    private String format(String message) {
        Integer teste;

        if (!this.source.toString().equals(""))
            teste = 1;
        else
            teste = 2;

        return teste.toString();
    }
}