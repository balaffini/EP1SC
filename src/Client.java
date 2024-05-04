import java.util.LinkedList;
import java.util.List;

public class Client {
    public static final List<Client> clients = new LinkedList<>();
    private Integer id;
    private Integer arrivalTime;
    private Double serviceTime;
    private Integer departureTime;
    private Integer guicheId;

    public Client(Integer arrivalTime, Double serviceTimeConstant) {
        this.id = clients.size();
        this.arrivalTime = arrivalTime;
        this.serviceTime = calculateServiceTime(serviceTimeConstant);
        this.departureTime = arrivalTime + (int) Math.ceil(serviceTime); //TODO
        this.guicheId = null;
        clients.add(this);
    }

    private Double calculateServiceTime(Double serviceTimeConstant) {
        return -serviceTimeConstant * Math.log(Math.random()); // TODO
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
}
