public class Guiche {
    private final Integer id;
    private Boolean available;
    private Client client;

    public Guiche(Integer id) {
        this.id = id;
        this.available = true;
        this.client = null;
    }

    @Override
    public String toString() {
        String clientString = client == null ? "null" : client.getId().toString() + " leaves at " + client.getDepartureTime();
        return "\nGuiche{" +
                "id=" + id +
                ", available=" + available +
                ", client=" + clientString +
                '}';
    }

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Integer getId() {
        return id;
    }
}
