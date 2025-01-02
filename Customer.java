public class Customer {
    private String name;
    private String id;
    private Parcel parcel;

    public Customer(String name, String id) {
        this.name = name;
        this.id = id;
    }

    public String getName() { return name; }
    public String getId() { return id; }
    public Parcel getParcel() { return parcel; }
    public void setParcel(Parcel parcel) { this.parcel = parcel; }

    @Override
    public String toString() {
        return "Customer{name='" + name + "', id='" + id + "', parcel=" + parcel + "}";
    }
}
