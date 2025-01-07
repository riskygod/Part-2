public class Parcel {
    private String id;
    private double length;
    private double width;
    private double height;
    private double weight;
    private int daysInWarehouse;

    public Parcel(String id, double length, double width, double height, double weight, int daysInWarehouse) {
        this.id = id;
        this.length = length;
        this.width = width;
        this.height = height;
        this.weight = weight;
        this.daysInWarehouse = daysInWarehouse;
    }

    // Getters
    public String getId() { return id; }
    public double getLength() { return length; }
    public double getWidth() { return width; }
    public double getHeight() { return height; }
    public double getWeight() { return weight; }
    public int getDaysInWarehouse() { return daysInWarehouse; }

    @Override
    public String toString() {
        return String.format("Parcel{id='%s', dimensions=%.2fx%.2fx%.2f, weight=%.2f, days=%d}",
                id, length, width, height, weight, daysInWarehouse);
    }
}
