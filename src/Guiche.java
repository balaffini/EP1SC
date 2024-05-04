public class Guiche {
    private Integer id;
    private Boolean available;
    private Integer client;

    public Guiche(Integer id) {
        this.id = id;
        this.available = true;
        this.client = null;
    }

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }

    public Integer getClient() {
        return client;
    }

    public void setClient(Integer client) {
        this.client = client;
    }

    public Integer getId() {
        return id;
    }
}