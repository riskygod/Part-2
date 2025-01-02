public class Worker {
    public void processCustomer(Customer customer) {
        Log.getInstance().addEvent("Processing customer: " + customer.getName());
    }

    public double calculateFee(Parcel parcel) {
        return parcel.getWeight() * 2.5;
    }
}
