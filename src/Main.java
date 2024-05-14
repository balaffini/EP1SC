import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main {
    static final List<Integer> clientesAtendidos = new ArrayList<>();
    static final List<Integer> clientesQueForamEmbora = new ArrayList<>();

    static final List<Integer> tempoAtendimento = new ArrayList<>();
    static final List<Double> taxasDeAbandono = new ArrayList<>();

    static Double wMedio() { //taxa de abandono média
        return taxasDeAbandono.stream().mapToDouble(Double::doubleValue).average().orElse(0.0);
    }

    static final double lambda = 4.0; //taxa de intervalo de chegada
    static final int t = 60; //tempo de simulação
    static final double mi = 0.5; //taxa de atendimento
    static int nGuiches = 5; //número de guichês

    static int iteration = 500; // numero de testes rodada atual
    static void nextIteration() { //calcular a proxima rodada
        iteration += 500;
        nGuiches++;
    }

    public static void main(String[] args) {
        do {
            for (int n = 0; n < iteration; n++) {
                Atendimento atendimento = new Atendimento();
                atendimento.simulate();
            }
            nextIteration();
        } while (calculateIntervalAmplitude() >= 0.002 || wMedio() > 0.2);
        System.out.println("N: " + (iteration-500));
        System.out.println("x: " + clientesAtendidos.stream().mapToInt(Integer::intValue).average().orElse(0));
        System.out.println("Y: " + clientesQueForamEmbora.stream().mapToInt(Integer::intValue).average().orElse(0));
        System.out.println("W: " + (wMedio()*100) + "%");
        System.out.println("n: " + nGuiches);
    }



    public static void setIterationResults(int x, int y, double w, int tm) {
        clientesAtendidos.add(x);
        clientesQueForamEmbora.add(y);
        taxasDeAbandono.add(w);
        tempoAtendimento.add(tm);
    }

    public static double generateStandardDeviation(double constant) {
        Random random = new Random();
        return Math.log(1 - random.nextDouble()) / (-constant);
    }

    private static double calculateIntervalAmplitude() {
        double z = 1.96; // intervalo de confiança de 95%
        double amplitude = 2 * z * calculateWStandardError();// * Math.exp(wMedio());

        return amplitude;
    }

    private static double calculateWStandardError() {
        double sumOfSquares = taxasDeAbandono.stream().mapToDouble(num -> Math.pow(num - wMedio(), 2)).sum();

        double standardDeviation = Math.sqrt(sumOfSquares / (taxasDeAbandono.size() - 1));
        double standardError = standardDeviation / Math.sqrt(taxasDeAbandono.size());

        return standardError;
    }
}
