import java.util.LinkedList;
import java.util.List;

public class Client {
    private Integer id;
    private Integer arrivalTime; //tk
    private Double serviceTime; //mi
    private Integer arrivalTimeAtGuiche; //tk de chegada no guiche
    private Integer departureTime; //tk final de atendimento
    private Integer guicheId;
    private boolean attended;

    public Client(int id, Integer arrivalTime) {
        this.id = id;
        this.arrivalTime = arrivalTime;
        this.serviceTime = calculateServiceTime();
        this.departureTime = Main.t+1; //TODO
        this.guicheId = null;
        this.attended = false;
    }

    private Double calculateServiceTime() {
        return Main.generateMiStandardDeviation();
    }

    public void setAttending(Integer guicheId, Integer arrivalTimeAtGuiche) {
        this.guicheId = guicheId;
        this.arrivalTimeAtGuiche = arrivalTimeAtGuiche;
        this.departureTime = arrivalTimeAtGuiche + (int) Math.ceil(serviceTime) + 8;
    }

    @Override
    public String toString() {
        return "\nClient{" +
                "id=" + id +
                ", arrivalTime=" + arrivalTime +
                ", serviceTime=" + serviceTime.intValue() +
                ", departureTime=" + departureTime +
                ", guicheId=" + guicheId +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public Integer getArrivalTime() {
        return arrivalTime;
    }

    public Double getServiceTime() {
        return serviceTime;
    }

    public Integer getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(Integer departureTime) {
        this.departureTime = departureTime;
    }

    public Integer getGuicheId() {
        return guicheId;
    }

    public void setGuicheId(Integer guicheId) {
        this.guicheId = guicheId;
    }

    public boolean isAttended() {
        return attended;
    }

    public void setAttended(boolean attended) {
        this.attended = attended;
    }
}
