public class Worker {
    public void processCustomer(Customer customer) {
        Log.getInstance().addEvent("Processing customer: " + customer.getName());
    }

    public double calculateFee(Parcel parcel) {
        // Base fee
        double fee = 5.0;

        // Add fee based on weight
        fee += parcel.getWeight() * 0.5;

        // Add fee based on volume
        double volume = parcel.getLength() * parcel.getWidth() * parcel.getHeight();
        fee += volume * 0.01;

        // Add fee for days in warehousez
        fee += parcel.getDaysInWarehouse() * 1.5;

        return fee;
    }
}
