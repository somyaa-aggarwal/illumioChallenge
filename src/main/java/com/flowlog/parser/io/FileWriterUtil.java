
package main.java.com.flowlog.parser.io;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

public class FileWriterUtil {

    public static void writeTagCounts(Map<String, Integer> tagCounts, String fileName) throws IOException {
        if (tagCounts == null || tagCounts.isEmpty()) {
            System.err.println("Warning: Tag counts are empty. No data to write.");
            return;
        }
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName))) {
            bw.write("Tag Counts:\n");
            bw.write("Tag,Count\n");
            for (Map.Entry<String, Integer> entry : tagCounts.entrySet()) {
                bw.write(entry.getKey() + "," + entry.getValue() + "\n");
                System.out.println("Tag counts successfully written to: " + fileName);
            }
        } catch (IOException e) {
            System.err.println("Error writing tag counts to file: " + fileName);
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("Unexpected error while writing tag counts.");
            e.printStackTrace();
        }

    }

    public static void writePortProtocolCounts(Map<String, Integer> portProtocolCounts, String fileName) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName))) {
            bw.write("Port/Protocol Combination Counts:\n");
            bw.write("Port,Protocol,Count\n");
            for (Map.Entry<String, Integer> entry : portProtocolCounts.entrySet()) {
                String[] keyParts = entry.getKey().split(",");
                bw.write(keyParts[0] + "," + keyParts[1] + "," + entry.getValue() + "\n");
            }
        }
    }
}
