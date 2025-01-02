import java.util.Arrays;

public class Parcel {
    private String id;
    private int[] dimensions;
    private double weight;

    public Parcel(String id, int[] dimensions, double weight) {
        this.id = id;
        this.dimensions = dimensions;
        this.weight = weight;
    }

    public String getId() { return id; }
    public int[] getDimensions() { return dimensions; }
    public double getWeight() { return weight; }

    @Override
    public String toString() {
        return "Parcel{id='" + id + "', dimensions=" + Arrays.toString(dimensions) + ", weight=" + weight + "}";
    }
}
