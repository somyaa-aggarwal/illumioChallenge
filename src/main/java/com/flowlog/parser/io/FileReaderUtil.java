package main.java.com.flowlog.parser.io;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import main.java.com.flowlog.parser.processor.FlowLogProcessor;


/**
 * Utility class for reading and processing flow log files and lookup tables.
 * 
 * This class provides methods to:
 * - Read and parse flow logs.
 * - Load lookup tables for mapping port-protocol combinations to tags.
 * - Load protocol lookup tables for mapping protocol numbers to names.
 * 
 */

public class FileReaderUtil {

    /**
     * Reads and processes flow logs from the given file path.
     * 
     * Each line in the file represents a flow log entry. The method extracts relevant
     * information, validates the format, and passes the entry to the {@link FlowLogProcessor}.
     * 
     *
     * @param filePath  Path to the flow logs file.
     * @param processor The {@link FlowLogProcessor} instance to process log entries.
     * @throws IOException If an error occurs while reading the file.
     */
    public static void processFlowLogs(String filePath, FlowLogProcessor processor) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] logParts = line.split("\\s+"); // Split by whitespace
                if (logParts.length < 12) {
                    System.err.println("Warning: Malformed log entry: " + line);
                    continue;
                }
                processor.processLogEntry(logParts);
            }
        }
        catch (IOException e) {
            System.err.println("Error processing flow log file: " + filePath);
            throw e;
        }
    }

    /**
     * Loads a lookup table from the given file path.
     * 
     * The lookup table maps port-protocol combinations to their corresponding tags.
     * The file should be in CSV format with the structure: {@code port,protocol,tag}.
     * 
     *
     * @param filePath Path to the lookup table file.
     * @return A map where keys are "port,protocol" and values are corresponding tags.
     * @throws IOException If an error occurs while reading the file.
     */
    public static Map<String, String> loadLookupTable(String filePath) throws IOException {
        Map<String, String> lookupTable = new HashMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            reader.readLine(); // Skipping header line
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    String key = parts[0].trim() + "," + parts[1].trim().toLowerCase();
                    lookupTable.put(key, parts[2].trim());
                }
                else{
                    System.err.println("Warning: Invalid entry in lookup table: " + line);
                    continue;

                }
            }
        } catch (IOException e) {
            System.err.println("Error reading lookup table file: " + filePath);
            throw e;
        }
        return lookupTable;
    }
    

    /**
     * Loads a protocol lookup table from the given file path.
     * 
     * The protocol lookup table maps protocol numbers to their corresponding names.
     * The file should be in CSV format with the structure: {@code protocol_number,protocol_name}.
     * 
     *
     * @param filePath Path to the protocol lookup table file.
     * @return A map where keys are protocol numbers and values are their corresponding protocol names.
     * @throws IOException If an error occurs while reading the file.
     */
    public static Map<String, String> loadProtocolLookupTable(String filePath) throws IOException {
        Map<String, String> protocolLookup = new HashMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            reader.readLine(); // Skipping header line
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
              
                if (parts.length < 2) {
                    System.err.println("Warning: Invalid protocol entry: " + line);
                    continue;
                }
                protocolLookup.put(parts[0].trim(), parts[1].trim().toLowerCase());
            }
        } catch (IOException e) {
            System.err.println("Error reading protocol lookup file: " + filePath);
            throw e;
        }
        return protocolLookup;
    }
}
