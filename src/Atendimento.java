import java.util.ArrayDeque;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Atendimento {
    final List<Client> clients;
    final Queue<Integer> queue; //fila de clientes
    final List<Guiche> availableGuiches;
    final List<Guiche> busyGuiches;
    int x; //clientes atendidos
    int y; //clientes que foram embora
    double w() {  //taxa de abandono
        if(x + y == 0) return 0;
        return (double) y/(x + y);
    }

    int tmAtendimento = 0; //tempo maximo de atendimento
    void setTmAtendimento(int t) {
        if (t > tmAtendimento) {
            tmAtendimento = t;
        }
    }

    public Atendimento() {
        this.clients = new LinkedList<>();
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

        int nextClientTime = 0;

        for (int tk = 0; tk < Main.t; tk++) {
            selectClientsToFinishService(tk);

            if (tk >= nextClientTime) { //vai chegar um cliente?
                nextClientTime = tk + (int) Math.ceil(Main.generateStandardDeviation(Main.lambda) * 1/Main.lambda);
                clientArrival(tk);
            }
        }
        Main.setIterationResults(x, y, w(), tmAtendimento);
    }

    private void clientArrival(int tk) {
        Client client = new Client(clients.size(), tk);
        clients.add(client);

        if (availableGuiches.isEmpty()) {
            addClientToQueueOrAbandonCart(client.getId());
        } else {
            Guiche guiche = availableGuiches.get(0);
            attendClient(guiche, client, tk);
        }
    }

    private void selectClientsToFinishService(int tk) {
        final List<Guiche> attendedClients = new LinkedList<>();
        busyGuiches.forEach(guiche -> {
            if (canFinishForClient(guiche, tk)) {
                attendedClients.add(guiche);
            }
        });
        if (!attendedClients.isEmpty()) {
            finishServiceAndMoveQueue(attendedClients, tk);
        }
    }

    private void printRound(int tk, int nextClientTime) {
        System.out.println("\nTempo: " + tk);
        System.out.println("Próximo cliente: " + nextClientTime);
        System.out.println("Fila: " + queue.size());
        System.out.println("Guiches disponíveis: " + availableGuiches.size());
        System.out.println("Guiches ocupados: " + busyGuiches.size());
        System.out.println("Total clientes: " + clients.size());
        System.out.println("Clientes atendidos: " + x);
        System.out.println("Clientes que desistiram: " + y);
        if (x + y == 0) {
            System.out.println("Taxa de abandonoo: 0");
        } else {
            double taxaAbandono = (double) y/(x + y);
            System.out.println("Taxa de abandono: " + taxaAbandono);
        }
    }

    private boolean canFinishForClient(Guiche guiche, int tk) {
        return guiche.getClient().getDepartureTime() <= tk;
    }

    private void finishServiceAndMoveQueue(List<Guiche> guiches, int tk) {
        guiches.forEach(g -> {
            g.getClient().setAttended(true);
            setTmAtendimento(g.getClient().getDepartureTime() - g.getClient().getArrivalTime());
            g.setAvailable(true);
            g.setClient(null);
        });
        busyGuiches.removeAll(guiches);
        availableGuiches.addAll(guiches);
        x += guiches.size(); //foram atendidos

        //pegar um da fila
        if (!queue.isEmpty()) {
            Client client = clients.get(queue.poll());
            attendClient(availableGuiches.get(0), client, tk);
        }
    }

    private void attendClient(Guiche guiche, Client client, int tk) {
        guiche.setAvailable(false);
        guiche.setClient(client);
        client.setAttending(guiche.getId(), tk);
        availableGuiches.remove(guiche);
        busyGuiches.add(guiche);
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
