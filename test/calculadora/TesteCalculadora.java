package calculadora;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

/**
 * Testes unitários automatizados, sem dependências externas, no formato da aula.
 * Cada caso verifica retorno, atributo e mensagem; falhas encerram com código 1.
 * @author Ana Clara dos Reis Bauer Cesar
 * @version 1.0
 */
public final class TesteCalculadora {
    /** Instância compartilhada para verificar também chamadas consecutivas. */
    private final Calculadora calculadora = new Calculadora();
    /** Quantidade de casos executados. */
    private int total;
    /** Quantidade de casos com divergências. */
    private int falhas;

    /** Cria o executor de testes. */
    private TesteCalculadora() { }

    /**
     * Executa os testes e informa as entradas, saídas esperadas e obtidas.
     * @param args argumentos não utilizados
     * @throws AssertionError se um ou mais casos falharem
     */
    public static void main(String[] args) {
        TesteCalculadora teste = new TesteCalculadora();
        teste.executar();
        System.out.printf("TOTAL: %d | APROVADOS: %d | FALHAS: %d%n",
                teste.total, teste.total - teste.falhas, teste.falhas);
        if (teste.falhas > 0) {
            throw new AssertionError("Há testes com divergências.");
        }
    }

    /** Executa cenários funcionais, limites e recuperação após erros. */
    private void executar() {
        verificar(5, 3, "+", 8, "");
        verificar(10, 4, "-", 6, "");
        verificar(6, 7, "*", 42, "");
        verificar(20, 4, "/", 5, "");
        verificar(10, 0, "/", 0, "Erro: divisão por zero.");
        verificar(2, 3, "%", 0, "Erro: operação inválida. Use +, -, * ou /.");
        verificar(7, 2, "/", 3, "");
        verificar(-7, 2, "/", -3, "");
        verificar(-5, 3, "+", -2, "");
        verificar(3, 5, "-", -2, "");
        verificar(-6, 7, "*", -42, "");
        verificar(0, 5, "/", 0, "");
        verificar(0, 0, "/", 0, "Erro: divisão por zero.");
        verificar(2, 3, null, 0, "Erro: operação inválida. Use +, -, * ou /.");
        verificar(2, 3, "", 0, "Erro: operação inválida. Use +, -, * ou /.");
        verificar(Integer.MAX_VALUE, 1, "+", 0, "Erro: resultado fora do intervalo de int.");
        verificar(Integer.MIN_VALUE, 1, "-", 0, "Erro: resultado fora do intervalo de int.");
        verificar(Integer.MAX_VALUE, 2, "*", 0, "Erro: resultado fora do intervalo de int.");
        verificar(Integer.MIN_VALUE, -1, "/", 0, "Erro: resultado fora do intervalo de int.");
        verificar(Integer.MAX_VALUE, 0, "+", Integer.MAX_VALUE, "");
        verificar(Integer.MIN_VALUE, 1, "/", Integer.MIN_VALUE, "");
        verificar(0, 7, "*", 0, "");
        verificar(9, 3, "/", 3, "");
    }

    /**
     * Compara um caso com valores esperados independentes da implementação.
     * @param a primeiro operando
     * @param b segundo operando
     * @param op operador testado (inclusive nulo)
     * @param esperado retorno esperado
     * @param mensagem mensagem de erro esperada ou texto vazio para sucesso
     */
    private void verificar(int a, int b, String op, int esperado, String mensagem) {
        total++;
        PrintStream original = System.out;
        ByteArrayOutputStream captura = new ByteArrayOutputStream();
        int obtido = 0;
        RuntimeException excecao = null;
        // Restaura o console mesmo quando a implementação lança uma exceção.
        try (PrintStream temporario = new PrintStream(captura, true, StandardCharsets.UTF_8)) {
            System.setOut(temporario);
            obtido = calculadora.calc(a, b, op);
        } catch (RuntimeException erro) {
            excecao = erro;
        } finally {
            System.setOut(original);
        }
        String saida = captura.toString(StandardCharsets.UTF_8).replace("\r\n", "\n");
        String saidaEsperada = (mensagem.isEmpty() ? "" : mensagem + "\n")
                + "Resultado = " + esperado + "\n";
        boolean passou = excecao == null && obtido == esperado
                && calculadora.r == esperado && saida.equals(saidaEsperada);
        if (!passou) {
            falhas++;
        }
        System.out.printf("CT%02d | %d %s %d | esperado=%d | obtido=%d | %s%n",
                total, a, String.valueOf(op), b, esperado, obtido, passou ? "OK" : "FALHOU");
        if (!mensagem.isEmpty()) {
            System.out.println("  Mensagem esperada: " + mensagem);
            System.out.println("  Mensagem obtida:   " + saida.strip().replace("\n", " | "));
        }
        if (!passou) {
            System.out.println("  Diagnóstico: atributo=" + calculadora.r + "; exceção=" + excecao
                    + "; saída=" + saida.replace("\n", " | "));
        }
    }
}
