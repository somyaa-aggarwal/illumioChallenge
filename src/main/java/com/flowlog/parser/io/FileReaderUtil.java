package main.java.com.flowlog.parser.io;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import main.java.com.flowlog.parser.processor.FlowLogProcessor;

public class FileReaderUtil {
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
