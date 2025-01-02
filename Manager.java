public class Manager {
    private QueueOfCustomers customerQueue;
    private ParcelMap parcelMap;
    private Worker worker;

    public Manager() {
        customerQueue = new QueueOfCustomers();
        parcelMap = new ParcelMap();
        worker = new Worker();
        loadData();
    }

    private void loadData() {
        // Load customer data
        String[] customerData = {
                "Andrew Robertson\tX919", "Ann Jones\tX064", "Blair Foster\tX682",
                // ... (rest of the customer data)
        };

        for (String data : customerData) {
            String[] parts = data.split("\t");
            Customer customer = new Customer(parts[0], parts[1]);
            customerQueue.addCustomer(customer);
        }

        // Load parcel data
        String[] parcelData = {
                "X009\t9\t1\t9\t9\t7", "X020\t1\t1\t6\t4\t14", "X025\t7\t1\t4\t9\t9",
                // ... (rest of the parcel data)
        };

        for (String data : parcelData) {
            String[] parts = data.split("\t");
            int[] dimensions = new int[4];
            for (int i = 0; i < 4; i++) {
                dimensions[i] = Integer.parseInt(parts[i + 1]);
            }
            double weight = Double.parseDouble(parts[5]);
            Parcel parcel = new Parcel(parts[0], dimensions, weight);
            parcelMap.addParcel(parcel);
        }
    }

    public void processNextCustomer() {
        if (!customerQueue.isEmpty()) {
            Customer customer = customerQueue.removeCustomer();
            Parcel parcel = parcelMap.getParcel(customer.getId());
            if (parcel != null) {
                customer.setParcel(parcel);
                worker.processCustomer(customer);
                double fee = worker.calculateFee(parcel);
                Log.getInstance().addEvent("Fee for " + customer.getName() + ": $" + fee);
            } else {
                Log.getInstance().addEvent("No parcel found for " + customer.getName());
            }
        } else {
            Log.getInstance().addEvent("No more customers in queue");
        }
    }

    public static void main(String[] args) {
        Manager manager = new Manager();
        while (!manager.customerQueue.isEmpty()) {
            manager.processNextCustomer();
        }
        Log.getInstance().writeToFile("log.txt");
    }
}
