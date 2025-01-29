
package main.java.com.flowlog.parser.io;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

public class FileWriterUtil {

    private static final String OUTPUT_DIR = "output";

    private static void createOutputDirectory() {
        File dir = new File(OUTPUT_DIR);
        if (!dir.exists()) {
            if (dir.mkdirs()) {
                System.out.println("Created output directory: " + OUTPUT_DIR);
            } else {
                System.err.println("Failed to create output directory: " + OUTPUT_DIR);
            }
        }
    }

    public static void writeTagCounts(Map<String, Integer> tagCounts, String fileName) throws IOException {
        if (tagCounts == null) {
            throw new NullPointerException("Tag counts map cannot be null.");
        }
        createOutputDirectory(); //to ensure that the output directory exists
        String filePath = OUTPUT_DIR + File.separator + fileName;
        if (tagCounts == null || tagCounts.isEmpty()) {
            System.err.println("Warning: Tag counts are empty. No data to write.");
            return;
        }
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
            bw.write("Tag Counts:\n");
            bw.write("Tag,Count\n");
            for (Map.Entry<String, Integer> entry : tagCounts.entrySet()) {
                bw.write(entry.getKey() + "," + entry.getValue() + "\n");
                //System.out.println("Tag counts successfully written to: " + filePath);
            }
        } catch (IOException e) {
            System.err.println("Error writing tag counts to file: " + filePath);
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("Unexpected error while writing tag counts.");
            e.printStackTrace();
        }

    }

    public static void writePortProtocolCounts(Map<String, Integer> portProtocolCounts, String fileName) throws IOException {
        if (portProtocolCounts == null) {
            throw new NullPointerException("Port-protocol counts map cannot be null.");
        }
        createOutputDirectory(); //to ensure that the output directory exists
        String filePath = OUTPUT_DIR + File.separator + fileName;
        if (portProtocolCounts == null || portProtocolCounts.isEmpty()) {
            System.err.println("Warning: Port-protocol counts are empty. No data to write.");
            return;
        }
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
            bw.write("Port/Protocol Combination Counts:\n");
            bw.write("Port,Protocol,Count\n");
            for (Map.Entry<String, Integer> entry : portProtocolCounts.entrySet()) {
                String[] keyParts = entry.getKey().split(",");
                bw.write(keyParts[0] + "," + keyParts[1] + "," + entry.getValue() + "\n");
                //System.out.println("Port-protocol counts successfully written to: " + filePath);
            }
        }catch (IOException e) {
            System.err.println("Error writing port-protocol counts to file: " + filePath);
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("Unexpected error while writing port-protocol counts.");
            e.printStackTrace();
        }
    }
}
