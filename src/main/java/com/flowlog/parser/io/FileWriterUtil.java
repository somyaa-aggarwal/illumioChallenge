
package main.java.com.flowlog.parser.io;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;


/**
 * Utility class for writing parsed flow log data to output files.
 * 
 * This class provides methods to:
 * - Write tag counts to a file.
 * - Write port-protocol counts to a file.
 * - Ensure the existence of the output directory before writing.
 * 
 */
public class FileWriterUtil {
    //The directory where output files will be stored. 
    private static final String OUTPUT_DIR = "output";

    /**
     * Ensures that the output directory exists before writing any files.
     * If the directory does not exist, it attempts to create it.
     */
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


    /**
     * Writes the tag counts to an output file in CSV format.
     * <p>
     * The file will contain tag names and their corresponding counts.
     * If the input map is null, a {@link NullPointerException} is thrown.
     * If the map is empty, a warning is displayed, and no file is created.
     * </p>
     *
     * @param tagCounts A map containing tag names as keys and their counts as values.
     * @param fileName  The name of the output file to be created.
     * @throws IOException If an error occurs while writing the file.
     */
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

    /**
     * Writes port-protocol combination counts to an output file in CSV format.
     * <p>
     * The file will contain port numbers, corresponding protocol types, and their counts.
     * If the input map is null, a {@link NullPointerException} is thrown.
     * If the map is empty, a warning is displayed, and no file is created.
     * </p>
     *
     * @param portProtocolCounts A map where keys represent "port,protocol" combinations and values are their counts.
     * @param fileName           The name of the output file to be created.
     * @throws IOException If an error occurs while writing the file.
     */
    public static void writePortProtocolCounts(Map<String, Integer> portProtocolCounts, String fileName) throws IOException {
        if (portProtocolCounts == null) {
            throw new NullPointerException("Port-protocol counts map cannot be null.");
        }
        createOutputDirectory(); //to ensure that the output directory exists
        String filePath = OUTPUT_DIR + File.separator + fileName;

        // Handling empty port-protocol counts scenario
        if (portProtocolCounts == null || portProtocolCounts.isEmpty()) {
            System.err.println("Warning: Port-protocol counts are empty. No data to write.");
            return;
        }

        // Writing port-protocol counts to file
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
