import java.util.*;

public class Manager {
    private Queue<Customer> customerQueue;
    private Map<String, Parcel> parcelMap;
    private Worker worker;

    public Manager() {
        customerQueue = new LinkedList<>();
        parcelMap = new HashMap<>();
        worker = new Worker();
        loadData();
    }

    public Queue<Customer> getCustomerQueue() {
        return new LinkedList<>(customerQueue); // Return a copy to preserve encapsulation
    }

    public Map<String, Parcel> getParcelMap() {
        return new HashMap<>(parcelMap); // Return a copy to preserve encapsulation
    }

    public Customer processNextCustomer() {
        if (!customerQueue.isEmpty()) {
            Customer customer = customerQueue.poll();
            Parcel parcel = parcelMap.get(customer.getId());
            if (parcel != null) {
                customer.setParcel(parcel);
                worker.processCustomer(customer);
                double fee = worker.calculateFee(parcel);
                Log.getInstance().addEvent("Fee for " + customer.getName() + ": $" + fee);
                return customer;
            } else {
                Log.getInstance().addEvent("No parcel found for " + customer.getName());
            }
        } else {
            Log.getInstance().addEvent("No more customers in queue");
        }
        return null;
    }

    public void addParcel(Parcel parcel) {
        parcelMap.put(parcel.getId(), parcel);
        Log.getInstance().addEvent("New parcel added: " + parcel.getId());
    }

    public void addCustomer(Customer customer) {
        customerQueue.offer(customer);
        Log.getInstance().addEvent("New customer added: " + customer.getName());
    }

    private void loadData() {
        // Load customer data
        String[] customerData = {
                "Andrew Robertson\tX919", "Ann Jones\tX064", "Blair Foster\tX682",
                "Bob Dawson\tX780", "Chris Smith\tX782", "Dave Jackson\tX316",
                "David Hunter\tX278", "Donald Murray\tX720", "Fiona Thoms\tX475",
                "Gillian Hamilton\tX386", "Harry Johnston\tX857", "Helen Webster\tX309",
                "Jack Houston\tX733", "Jo Hill\tX285", "Joe Woods\tX213",
                "John Brown\tX009", "Judy Hilman\tX904", "Keith Burns\tX552",
                "Lily Watson\tX121", "Lucy Grey\tX025", "Mary Brown\tX198",
                "Pamela Field\tX521", "Peter White\tX036", "Robert Murray\tX606",
                "Susan Turner\tX214", "Thomas Young\tX507", "Tim Smith\tX020",
                "Tony Lawson\tX086", "Ursula Milton\tX746", "Viola Nicholson\tX540"
        };

        for (String data : customerData) {
            String[] parts = data.split("\t");
            Customer customer = new Customer(parts[0], parts[1]);
            addCustomer(customer);
        }

        // Load parcel data
        String[] parcelData = {
                "X009\t9\t1\t9\t9\t7", "X020\t1\t1\t6\t4\t14", "X025\t7\t1\t4\t9\t9",
                "X036\t8\t4\t6\t9\t12", "X064\t8\t4\t1\t8\t15", "X086\t7\t4\t1\t7\t13",
                "X121\t3\t7\t2\t3\t6", "X198\t9\t4\t8\t0\t10", "X213\t4\t8\t5\t2\t15",
                "X214\t1\t8\t1\t1\t15", "X278\t5\t3\t1\t0\t11", "X285\t1\t4\t3\t1\t10",
                "X309\t1\t2\t8\t5\t11", "X316\t9\t5\t4\t0\t11", "X386\t9\t1\t6\t5\t9",
                "X475\t4\t3\t8\t1\t11", "X507\t5\t3\t9\t8\t13", "X521\t6\t4\t4\t4\t8",
                "X540\t9\t2\t5\t4\t5", "X552\t4\t5\t7\t8\t12", "X606\t8\t8\t4\t2\t13",
                "X682\t3\t6\t4\t4\t12", "X720\t4\t2\t1\t3\t8", "X733\t6\t6\t5\t7\t11",
                "X746\t4\t4\t9\t5\t7", "X780\t4\t1\t2\t5\t12", "X782\t5\t3\t2\t7\t12",
                "X857\t2\t6\t6\t3\t9", "X904\t4\t1\t4\t9\t15", "X919\t5\t8\t7\t4\t10"
        };

        for (String data : parcelData) {
            String[] parts = data.split("\t");
            int[] dimensions = new int[4];
            for (int i = 0; i < 4; i++) {
                dimensions[i] = Integer.parseInt(parts[i + 1]);
            }
            double weight = Double.parseDouble(parts[5]);
            Parcel parcel = new Parcel(parts[0], dimensions, weight);
            addParcel(parcel);
        }
    }

    public void writeLogToFile() {
        Log.getInstance().writeToFile("parcel_management_log.txt");
    }
}
