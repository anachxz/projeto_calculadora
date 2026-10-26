package calculadora;

/**
 * Núcleo de cálculos inteiros da FinançApp.
 * @author Ana Clara dos Reis Bauer Cesar
 * @version 1.0
 */
public class Calculadora {
    /** Último resultado; zero também é usado como retorno em caso de erro. */
    public int r = 0;

    /** Cria uma calculadora com resultado inicial zero. */
    public Calculadora() { }

    /**
     * Executa uma operação inteira. A divisão descarta a parte fracionária.
     * Em caso de erro, imprime uma mensagem e retorna zero, conforme a aula.
     * @param a primeiro operando
     * @param b segundo operando
     * @param op operador: +, -, * ou /
     * @return resultado inteiro, ou zero quando a operação não pode ser realizada
     */
    public int calc(int a, int b, String op) {
        r = 0;
        try {
            if ("+".equals(op)) {
                r = Math.addExact(a, b);
            } else if ("-".equals(op)) {
                r = Math.subtractExact(a, b);
            } else if ("*".equals(op)) {
                r = Math.multiplyExact(a, b);
            } else if ("/".equals(op)) {
                if (b == 0) {
                    System.out.println("Erro: divisão por zero.");
                } else if (a == Integer.MIN_VALUE && b == -1) {
                    throw new ArithmeticException("overflow");
                } else {
                    r = a / b;
                }
            } else {
                System.out.println("Erro: operação inválida. Use +, -, * ou /.");
            }
        } catch (ArithmeticException erro) {
            System.out.println("Erro: resultado fora do intervalo de int.");
        }
        System.out.println("Resultado = " + r);
        return r;
    }
}
