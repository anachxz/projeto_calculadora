package calculadora;

/**
 * Núcleo de cálculos inteiros da FinançApp, conforme o contrato da aula 09.
 * <p>Erros esperados são tratados internamente: imprime-se a mensagem e retorna-se
 * zero. Portanto, o retorno zero sozinho não distingue um erro de um cálculo válido.
 * Esta classe mantém estado e não deve ser compartilhada entre threads.</p>
 * @author Ana Clara dos Reis Bauer Cesar
 * @version 2.0
 */
public class Calculadora {
    /** Último resultado; reiniciado a zero a cada chamada, inclusive em erros. */
    public int r = 0;

    /** Cria uma calculadora com resultado inicial zero. */
    public Calculadora() { }

    /**
     * Calcula uma operação e apresenta o resultado no console.
     * <p>A divisão é inteira, com truncamento em direção a zero. Operadores nulos,
     * vazios ou diferentes dos quatro símbolos são inválidos. Exceções de validação
     * e de aritmética são capturadas aqui, sem interromper as próximas chamadas.</p>
     * @param a primeiro operando
     * @param b segundo operando (divisor na divisão)
     * @param op operador: +, -, * ou /
     * @return resultado inteiro, ou zero para divisão por zero, operador inválido ou overflow
     */
    public int calc(int a, int b, String op) {
        r = 0;
        try {
            r = calcularOperacao(a, b, op);
        } catch (IllegalArgumentException erro) {
            System.out.println(erro.getMessage());
        } catch (ArithmeticException erro) {
            System.out.println("Erro: resultado fora do intervalo de int.");
        }
        System.out.println("Resultado = " + r);
        return r;
    }

    /**
     * Seleciona a operação sem misturar cálculo e apresentação.
     * @param primeiro primeiro operando
     * @param segundo segundo operando
     * @param operador símbolo da operação
     * @return resultado da operação
     * @throws IllegalArgumentException se o operador for inválido ou o divisor for zero
     * @throws ArithmeticException se o resultado não couber em int
     */
    private int calcularOperacao(int primeiro, int segundo, String operador) {
        if (operador == null) {
            throw new IllegalArgumentException("Erro: operação inválida. Use +, -, * ou /.");
        }
        return switch (operador) {
            case "+" -> Math.addExact(primeiro, segundo);
            case "-" -> Math.subtractExact(primeiro, segundo);
            case "*" -> Math.multiplyExact(primeiro, segundo);
            case "/" -> dividir(primeiro, segundo);
            default -> throw new IllegalArgumentException(
                    "Erro: operação inválida. Use +, -, * ou /.");
        };
    }

    /**
     * Divide inteiros após validar o divisor e o único overflow possível da divisão.
     * @param dividendo valor a dividir
     * @param divisor valor pelo qual dividir
     * @return quociente inteiro truncado em direção a zero
     * @throws IllegalArgumentException se o divisor for zero
     * @throws ArithmeticException se MIN_VALUE for dividido por -1
     */
    private int dividir(int dividendo, int divisor) {
        if (divisor == 0) {
            throw new IllegalArgumentException("Erro: divisão por zero.");
        }
        // Esse caso transborda silenciosamente no operador / de Java.
        if (dividendo == Integer.MIN_VALUE && divisor == -1) {
            throw new ArithmeticException("overflow");
        }
        return dividendo / divisor;
    }
}
