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
        return new LinkedList<>(customerQueue);
    }

    public Map<String, Parcel> getParcelMap() {
        return new HashMap<>(parcelMap);
    }

    public Worker getWorker() {
        return worker;
    }

    public Customer processNextCustomer() {
        if (!customerQueue.isEmpty()) {
            Customer customer = customerQueue.poll();
            Parcel parcel = parcelMap.get(customer.getId());
            if (parcel != null) {
                customer.setParcel(parcel);
                worker.processCustomer(customer);
                double fee = worker.calculateFee(parcel);
                Log.getInstance().addEvent(String.format("Fee for %s: $%.2f", customer.getName(), fee));
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
                "X009\t9.0\t1.0\t9.0\t7.0\t5", "X020\t1.0\t1.0\t6.0\t14.0\t3",
                "X025\t7.0\t1.0\t4.0\t9.0\t7", "X036\t8.0\t4.0\t6.0\t12.0\t2",
                "X064\t8.0\t4.0\t1.0\t15.0\t4", "X086\t7.0\t4.0\t1.0\t13.0\t6",
                "X121\t3.0\t7.0\t2.0\t6.0\t1", "X198\t9.0\t4.0\t8.0\t10.0\t8",
                "X213\t4.0\t8.0\t5.0\t15.0\t3", "X214\t1.0\t8.0\t1.0\t15.0\t5",
                "X278\t5.0\t3.0\t1.0\t11.0\t2", "X285\t1.0\t4.0\t3.0\t10.0\t7",
                "X309\t1.0\t2.0\t8.0\t11.0\t4", "X316\t9.0\t5.0\t4.0\t11.0\t6",
                "X386\t9.0\t1.0\t6.0\t9.0\t3", "X475\t4.0\t3.0\t8.0\t11.0\t5",
                "X507\t5.0\t3.0\t9.0\t13.0\t2", "X521\t6.0\t4.0\t4.0\t8.0\t8",
                "X540\t9.0\t2.0\t5.0\t5.0\t1", "X552\t4.0\t5.0\t7.0\t12.0\t3",
                "X606\t8.0\t8.0\t4.0\t13.0\t7", "X682\t3.0\t6.0\t4.0\t12.0\t4",
                "X720\t4.0\t2.0\t1.0\t8.0\t6", "X733\t6.0\t6.0\t5.0\t11.0\t2",
                "X746\t4.0\t4.0\t9.0\t7.0\t5", "X780\t4.0\t1.0\t2.0\t12.0\t3",
                "X782\t5.0\t3.0\t2.0\t12.0\t8", "X857\t2.0\t6.0\t6.0\t9.0\t1",
                "X904\t4.0\t1.0\t4.0\t15.0\t4", "X919\t5.0\t8.0\t7.0\t10.0\t6"
        };

        for (String data : parcelData) {
            String[] parts = data.split("\t");
            Parcel parcel = new Parcel(
                    parts[0],
                    Double.parseDouble(parts[1]),
                    Double.parseDouble(parts[2]),
                    Double.parseDouble(parts[3]),
                    Double.parseDouble(parts[4]),
                    Integer.parseInt(parts[5])
            );
            addParcel(parcel);
        }
    }

    public void writeLogToFile() {
        Log.getInstance().writeToFile("parcel_management_log.txt");
    }
}
