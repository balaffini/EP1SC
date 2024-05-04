import java.util.ArrayDeque;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Main {
    static final Queue<Integer> queue = new ArrayDeque<>();
    static final List<Guiche> availableGuiches = new LinkedList<>();
    static final List<Guiche> busyGuiches = new LinkedList<>();

    static int x = 0;
    static int y = 0;
    static int w() {
        return y/(x + y);
    }

    static int lambda = 3;
    static int t = 50;
    static double mi = 0.5;
    static int n = 5;

    public static void main(String[] args) {
        for(int i = 0; i < n; i++) {
            availableGuiches.add(new Guiche(i));
        }

        // recieves client
        for (int i = t; w() < 0.002; i += lambda) {
            Client client = new Client(i, mi);

            if (availableGuiches.isEmpty()) {
                addClientToQueue(client.getId());
            } else {
                Guiche guiche = availableGuiches.get(0);
                attendClient(guiche, client.getId());
            }

            //finish service
            for (Guiche guiche : busyGuiches) {
                if (client.getDepartureTime() >= i) {
                    guiche.setAvailable(true);
                    guiche.setClient(null);
                    availableGuiches.add(guiche);
                    busyGuiches.remove(guiche);
                }
            }
        }
    }

    private static void attendClient(Guiche guiche, int client) {
        guiche.setAvailable(false);
        guiche.setClient(client);
        busyGuiches.add(guiche);
        availableGuiches.remove(0);
        x++;
    }

    private static void addClientToQueue(int client) {
        if (isQueueFull()) {
            y++;
        } else {
            queue.add(client);
        }
    }

    private static boolean isQueueFull() {
        return queue.size()/(queue.size() + n) >= 1;
    }
}