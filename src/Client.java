public class Client {
    private final Integer id;
    private final Integer arrivalTime; //tk
    private final Double serviceTime; //mi
    private Integer arrivalTimeAtGuiche; //tk de chegada no guiche
    private Integer departureTime; //tk final de atendimento
    private Integer guicheId;
    private boolean attended;

    public Client(int id, Integer arrivalTime) {
        this.id = id;
        this.arrivalTime = arrivalTime;
        this.serviceTime = calculateServiceTime();
        this.departureTime = Main.t+1;
        this.guicheId = null;
        this.attended = false;
    }

    private Double calculateServiceTime() {
        return Main.generateStandardDeviation(Main.mi)*(1/Main.mi);
    }

    public void setAttending(Integer guicheId, Integer arrivalTimeAtGuiche) {
        this.guicheId = guicheId;
        this.arrivalTimeAtGuiche = arrivalTimeAtGuiche;
        this.departureTime = arrivalTimeAtGuiche + (int) Math.ceil(serviceTime) + 1;
    }

    @Override
    public String toString() {
        return "\nClient{" +
                "id=" + id +
                ", arrivalTime=" + arrivalTime +
                ", arrivalTimeAtGuiche=" + arrivalTimeAtGuiche +
                ", serviceTime=" + serviceTime.intValue() +
                ", departureTime=" + departureTime +
                ", guicheId=" + guicheId +
                ", attended=" + attended +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public Integer getDepartureTime() {
        return departureTime;
    }

    public void setAttended(boolean attended) {
        this.attended = attended;
    }

    public Integer getArrivalTime() {
        return arrivalTime;
    }
}
