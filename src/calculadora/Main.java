package calculadora;

import java.util.Scanner;

/**
 * Interface de console que permite continuar calculando após entradas inválidas.
 * @author Ana Clara dos Reis Bauer Cesar
 * @version 1.0
 */
public final class Main {
    /** Impede instanciação da classe de entrada. */
    private Main() { }

    /**
     * Solicita dois inteiros e um operador até o comando sair ou fim da entrada.
     * @param args argumentos não utilizados
     */
    public static void main(String[] args) {
        Calculadora calculadora = new Calculadora();
        try (Scanner entrada = new Scanner(System.in)) {
            System.out.println("FinançApp | Calculadora de inteiros");
            while (true) {
                System.out.println("Digite: primeiro operador segundo (ex.: 5 + 3), ou sair");
                if (!entrada.hasNextLine()) {
                    break;
                }
                String linha = entrada.nextLine().trim();
                if (linha.equalsIgnoreCase("sair")) {
                    break;
                }
                String[] partes = linha.split("\\s+");
                if (partes.length != 3) {
                    System.out.println("Erro: use o formato 5 + 3, com espaços.");
                    continue;
                }
                try {
                    int primeiro = Integer.parseInt(partes[0]);
                    int segundo = Integer.parseInt(partes[2]);
                    calculadora.calc(primeiro, segundo, partes[1]);
                } catch (NumberFormatException erro) {
                    System.out.println("Erro: informe inteiros entre -2147483648 e 2147483647.");
                }
            }
        }
        System.out.println("Programa encerrado.");
    }
}
