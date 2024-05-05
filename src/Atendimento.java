import java.util.ArrayDeque;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Atendimento {
    final Queue<Integer> queue; //fila de clientes
    final List<Guiche> availableGuiches;
    final List<Guiche> busyGuiches;
    int x; //clientes atendidos
    int y; //clientes que foram embora
    double w() {  //taxa de abandono
        if(x + y == 0) return 0;
        return (double) y/(x + y);
    }

    public Atendimento() {
        this.queue = new ArrayDeque<>();
        this.availableGuiches = new LinkedList<>();
        this.busyGuiches = new LinkedList<>();
        this.x = 0;
        this.y = 0;
    }

    public void simulate() {
        for(int g = 0; g < Main.nGuiches; g++) {
            availableGuiches.add(new Guiche(g));
        }

        for (int tk = 0; tk < Main.t; tk++) {
            //            System.out.println("Tempo: " + tk);
            //            System.out.println("Fila: " + queue.size());
            //            System.out.println("Guiches disponíveis: " + availableGuiches.size());
            //            System.out.println("Guiches ocupados: " + busyGuiches.size());
            //            System.out.println("Clientes atendidos: " + x);
            //            System.out.println("Clientes que desistiram: " + y);
            //            if (x + y == 0) {
            //                System.out.println("Taxa de abandonoo: 0");
            //            } else {
            //                double taxaAbandono = (double) y/(x + y);
            //                System.out.println("Taxa de abandono: " + taxaAbandono);
            //            }

            final int instantTk = tk;
            busyGuiches.forEach(guiche -> {
                if (canFinishForClient(guiche, instantTk)) {
                    finishServiceAndMoveQueue(guiche);
                }
            });

            if (tk % 3 == 0) { //vai chegar um cliente?  TODO calculo em cima de lambda
                Client client = new Client(tk, Main.mi);

                if (availableGuiches.isEmpty()) {
                    addClientToQueueOrAbandonCart(client.getId());
                } else {
                    Guiche guiche = availableGuiches.get(0);
                    attendClient(guiche, client);
                }
            }
        }
        Main.setIterationResults(x, y, w());
    }

    private boolean canFinishForClient(Guiche guiche, int tk) {
        return guiche.getClient().getDepartureTime() >= tk;
    }

    private void finishServiceAndMoveQueue(Guiche guiche) {
        guiche.setAvailable(true);
        guiche.setClient(null);
        busyGuiches.remove(guiche);
        availableGuiches.add(guiche);

        //pegar um da fila
        if (!queue.isEmpty()) {
            Client client = Client.clients.get(queue.poll());
            attendClient(guiche, client);
        }
    }

    private void attendClient(Guiche guiche, Client client) {
        guiche.setAvailable(false);
        guiche.setClient(client);
        availableGuiches.remove(guiche);
        busyGuiches.add(guiche);
        x++; //foi atendido
    }

    private void addClientToQueueOrAbandonCart(int client) {
        if (isQueueFull()) {
            y++;  //foi embora
        } else {
            queue.add(client);
        }
    }

    private boolean isQueueFull() {
        return (double) queue.size()/(queue.size() + Main.nGuiches) >= Math.random();
    }
}
