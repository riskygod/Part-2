public class Customer {
    private String name;
    private String id;
    private Parcel parcel;

    public Customer(String name, String id) {
        this.name = name;
        this.id = id;
    }

    // Getters and setters
    public String getName() { return name; }
    public String getId() { return id; }
    public Parcel getParcel() { return parcel; }
    public void setParcel(Parcel parcel) { this.parcel = parcel; }
    public String getParcelId() { return parcel != null ? parcel.getId() : null; }

    @Override
    public String toString() {
        return String.format("Customer{name='%s', id='%s', parcel=%s}", name, id, parcel);
    }
}
