import javax.swing.*;
import java.awt.*;
import java.util.Vector;

public class ParcelManagementGUI extends JFrame {
    private Manager manager;
    private JList<Customer> customerList;
    private JList<Parcel> parcelList;
    private JTextArea currentParcelArea;
    private JButton processButton;
    private JButton addParcelButton;
    private JButton addCustomerButton;
    private JButton findParcelButton;
    private JButton saveLogButton;

    public ParcelManagementGUI(Manager manager) {
        this.manager = manager;
        setTitle("Parcel Management System");
        setSize(1000, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        initComponents();
        updateLists();
    }

    private void initComponents() {
        JPanel customerPanel = new JPanel(new BorderLayout());
        customerPanel.setBorder(BorderFactory.createTitledBorder("Customer Queue"));
        customerList = new JList<>(new Vector<>(manager.getCustomerQueue()));
        customerPanel.add(new JScrollPane(customerList), BorderLayout.CENTER);

        JPanel parcelPanel = new JPanel(new BorderLayout());
        parcelPanel.setBorder(BorderFactory.createTitledBorder("Parcel List"));
        parcelList = new JList<>(new Vector<>(manager.getParcelMap().values()));
        parcelPanel.add(new JScrollPane(parcelList), BorderLayout.CENTER);

        JPanel currentParcelPanel = new JPanel(new BorderLayout());
        currentParcelPanel.setBorder(BorderFactory.createTitledBorder("Current Parcel"));
        currentParcelArea = new JTextArea(10, 40);
        currentParcelArea.setEditable(false);
        currentParcelPanel.add(new JScrollPane(currentParcelArea), BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        processButton = new JButton("Process Next Customer");
        addParcelButton = new JButton("Add New Parcel");
        addCustomerButton = new JButton("Add Customer");
        findParcelButton = new JButton("Find Parcel");
        saveLogButton = new JButton("Save Log");

        buttonPanel.add(processButton);
        buttonPanel.add(addParcelButton);
        buttonPanel.add(addCustomerButton);
        buttonPanel.add(findParcelButton);
        buttonPanel.add(saveLogButton); // Add Save Log button to the panel

        add(customerPanel, BorderLayout.WEST);
        add(parcelPanel, BorderLayout.EAST);
        add(currentParcelPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        processButton.addActionListener(e -> processNextCustomer());
        addParcelButton.addActionListener(e -> addNewParcel());
        addCustomerButton.addActionListener(e -> addNewCustomer());
        findParcelButton.addActionListener(e -> findParcel());
        saveLogButton.addActionListener(e -> saveLog()); // Add action listener for the Save Log button
    }

    private void updateLists() {
        customerList.setListData(new Vector<>(manager.getCustomerQueue()));
        parcelList.setListData(new Vector<>(manager.getParcelMap().values()));
    }

    private void processNextCustomer() {
        Customer customer = manager.processNextCustomer();
        if (customer != null) {
            updateLists();
            Parcel parcel = customer.getParcel();
            String parcelInfo = String.format(
                    "Customer: %s\nParcel ID: %s\nDimensions: %.2f x %.2f x %.2f\nWeight: %.2f\nDays in Warehouse: %d\nFee: $%.2f",
                    customer.getName(),
                    parcel.getId(),
                    parcel.getLength(),
                    parcel.getWidth(),
                    parcel.getHeight(),
                    parcel.getWeight(),
                    parcel.getDaysInWarehouse(),
                    manager.getWorker().calculateFee(parcel)
            );
            currentParcelArea.setText(parcelInfo);
        } else {
            currentParcelArea.setText("No more customers in queue.");
        }
    }

    private void addNewParcel() {
        JTextField idField = new JTextField(10);
        JTextField lengthField = new JTextField(5);
        JTextField widthField = new JTextField(5);
        JTextField heightField = new JTextField(5);
        JTextField weightField = new JTextField(5);
        JTextField daysField = new JTextField(5);

        JPanel panel = new JPanel(new GridLayout(0, 2));
        panel.add(new JLabel("Parcel ID:"));
        panel.add(idField);
        panel.add(new JLabel("Length:"));
        panel.add(lengthField);
        panel.add(new JLabel("Width:"));
        panel.add(widthField);
        panel.add(new JLabel("Height:"));
        panel.add(heightField);
        panel.add(new JLabel("Weight:"));
        panel.add(weightField);
        panel.add(new JLabel("Days in Warehouse:"));
        panel.add(daysField);

        int result = JOptionPane.showConfirmDialog(null, panel, "Add New Parcel",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            try {
                String id = idField.getText();
                double length = Double.parseDouble(lengthField.getText());
                double width = Double.parseDouble(widthField.getText());
                double height = Double.parseDouble(heightField.getText());
                double weight = Double.parseDouble(weightField.getText());
                int days = Integer.parseInt(daysField.getText());

                Parcel newParcel = new Parcel(id, length, width, height, weight, days);
                manager.addParcel(newParcel);
                updateLists();
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Invalid input. Please enter numeric values for dimensions, weight, and days.",
                        "Input Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void addNewCustomer() {
        JTextField nameField = new JTextField(20);
        JTextField idField = new JTextField(10);

        JPanel panel = new JPanel(new GridLayout(0, 2));
        panel.add(new JLabel("Customer Name:"));
        panel.add(nameField);
        panel.add(new JLabel("Customer ID:"));
        panel.add(idField);

        int result = JOptionPane.showConfirmDialog(null, panel, "Add New Customer",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            String name = nameField.getText();
            String id = idField.getText();
            if (!name.isEmpty() && !id.isEmpty()) {
                Customer newCustomer = new Customer(name, id);
                manager.addCustomer(newCustomer);
                updateLists();
            } else {
                JOptionPane.showMessageDialog(this, "Name and ID cannot be empty.", "Input Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void findParcel() {
        String parcelId = JOptionPane.showInputDialog(this, "Enter Parcel ID:");
        if (parcelId != null && !parcelId.trim().isEmpty()) {
            Parcel parcel = manager.getParcel(parcelId.trim());
            if (parcel != null) {
                String parcelInfo = String.format(
                        "Parcel ID: %s\nDimensions: %.2f x %.2f x %.2f\nWeight: %.2f\nDays in Warehouse: %d",
                        parcel.getId(),
                        parcel.getLength(),
                        parcel.getWidth(),
                        parcel.getHeight(),
                        parcel.getWeight(),
                        parcel.getDaysInWarehouse()
                );
                currentParcelArea.setText(parcelInfo);
            } else {
                currentParcelArea.setText("No parcel found.");
            }
        }
    }

    private void saveLog() {
        manager.writeLogToFile();  // Save the log to a file
        JOptionPane.showMessageDialog(this, "Log saved to parcel_management_log.txt", "Log Saved", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Manager manager = new Manager();
            ParcelManagementGUI gui = new ParcelManagementGUI(manager);
            gui.setVisible(true);
        });
    }
}
