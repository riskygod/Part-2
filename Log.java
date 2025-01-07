import java.io.*;
import java.util.*;

public class Log {
    private static Log instance;
    private List<String> events;

    // Private constructor for Singleton pattern
    private Log() {
        events = new ArrayList<>();
    }

    public static Log getInstance() {
        if (instance == null) {
            instance = new Log();
        }
        return instance;
    }

    public void addEvent(String event) {
        events.add(event);
    }

    public String getLogData() {
        StringBuilder logData = new StringBuilder();
        for (String event : events) {
            logData.append(event).append("\n");
        }
        return logData.toString();
    }

    public void writeToFile(String filename) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            writer.write(getLogData());
        } catch (IOException e) {
            System.err.println("Error writing log to file: " + e.getMessage());
        }
    }
}
