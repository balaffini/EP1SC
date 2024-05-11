import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main {
    static final List<Integer> clientesAtendidos = new ArrayList<>();
    static final List<Integer> clientesQueForamEmbora = new ArrayList<>();
    static final List<Double> taxasDeAbandono = new ArrayList<>();

    static Double wMedio() { //taxa de abandono média
        return taxasDeAbandono.stream().mapToDouble(Double::doubleValue).average().orElse(0.0);
    }

    static final double lambda = 3.0; //taxa de intervalo de chegada
    static final int t = 50; //tempo de simulação
    static final double mi = 0.5; //taxa de atendimento
    static final int nGuiches = 5; //número de guichês

    static int iteration = 500; // numero de testes rodada atual
    static int nextIteration() { //calcular a proxima rodada
        return iteration += 500;
    }

    public static void main(String[] args) {
        do {
            for (int n = 0; n < iteration; n++) {
//                System.out.println("Rodada: " + (n+1));

                Atendimento atendimento = new Atendimento();
                atendimento.simulate();
            }

            System.out.println("\nFinal da simulação " + iteration/500);
            System.out.println("Média clientes atendidos: " + clientesAtendidos.stream().mapToInt(Integer::intValue).average().orElse(0));
            System.out.println("Média clientes que foram embora: " + clientesQueForamEmbora.stream().mapToInt(Integer::intValue).average().orElse(0));
            System.out.println("Taxa de abandono média: " + (int) (wMedio()*100) + "%");

            nextIteration();
        } while (calculateIntervalAmplitude() >= 0.002 || wMedio() >= 0.2);
        System.out.println("Iterações necessárias: " + (iteration-500));
    }



    public static void setIterationResults(int x, int y, double w) {
//        System.out.println("\nTotal da última execução");
//        System.out.println("Clientes atendidos: " + x);
//        System.out.println("Clientes que foram embora: " + y);
//        System.out.println("Taxa de abandono: " + w);

        clientesAtendidos.add(x);
        clientesQueForamEmbora.add(y);
        taxasDeAbandono.add(w);
    }

    public static double generateStandardDeviation(double constant) {
        Random random = new Random();
        double std = Math.log(1 - random.nextDouble()) / (-constant);
        System.out.println("\nStandard Deviation for " + constant + ": " + std);
        return std;
    }

    private static double calculateIntervalAmplitude() {
        double z = 1.96; // intervalo de confiança de 95%
        double amplitude = 2 * z * calculateWStandardError();// * Math.exp(wMedio());

        System.out.println("A amplitude do intervalo de confiança é: " + amplitude);
        if (amplitude < 0.002) {
            System.out.println("A amplitude do intervalo de confiança é menor que 0.002");
        } else {
            System.out.println("A amplitude do intervalo de confiança é maior ou igual a 0.002");
        }
        return amplitude;
    }

    private static double calculateWStandardError() {
        double sumOfSquares = taxasDeAbandono.stream().mapToDouble(num -> Math.pow(num - wMedio(), 2)).sum();

        double standardDeviation = Math.sqrt(sumOfSquares / (taxasDeAbandono.size() - 1));
        double standardError = standardDeviation / Math.sqrt(taxasDeAbandono.size());

        System.out.println("O erro padrão da taxa de abandono é: " + standardError);

        return standardError;
    }
}
