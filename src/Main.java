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

    static final double lambda = 4.0; //taxa de intervalo de chegada
    static final int t = 50; //tempo de simulação
    static final double mi = 0.5; //taxa de atendimento
    static final int nGuiches = 5; //número de guichês

    static int iteration = 500; // numero de testes rodada atual
    static int nextIteration() { //calcular a proxima rodada
        return iteration += 500;
    }

    public static void main(String[] args) {
//        do {
                for (int n = 0; n < 1; n++) {
                    System.out.println("\nRodada: " + (n+1));

                    Atendimento atendimento = new Atendimento();
                    atendimento.simulate();
                }

                System.out.println("\nFinal da simulação " + iteration/500);
                System.out.println("Média clientes atendidos: " + clientesAtendidos.stream().mapToInt(Integer::intValue).average().orElse(0));
                System.out.println("Média clientes que foram embora: " + clientesQueForamEmbora.stream().mapToInt(Integer::intValue).average().orElse(0));
                System.out.println("Taxa de abandono média: " + wMedio());

                nextIteration();
//        } while (wMedio() < 0.002);
    }

    public static void setIterationResults(int x, int y, double w) {
        System.out.println("\nTotal da última execução");
        System.out.println("Clientes atendidos: " + x);
        System.out.println("Clientes que foram embora: " + y);
        System.out.println("Taxa de abandono: " + w);

        clientesAtendidos.add(x);
        clientesQueForamEmbora.add(y);
        taxasDeAbandono.add(w);
    }

    public static double generateLambdaStandardDeviation() {
        Random random = new Random();
        double z = -Math.log(1 - random.nextDouble()) / lambda;
        return z;
    }

    public static double generateMiStandardDeviation() {
        Random random = new Random();
        double z = -Math.log(1 - random.nextDouble()) / mi;
        return z;
    }
}
