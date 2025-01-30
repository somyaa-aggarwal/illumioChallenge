package main.java.com.flowlog.parser;

import main.java.com.flowlog.parser.io.FileReaderUtil;
import main.java.com.flowlog.parser.io.FileWriterUtil;
import main.java.com.flowlog.parser.processor.FlowLogProcessor;

import java.io.IOException;
import java.util.Map;

/**
 * Main entry point for parsing flow logs. 
 * This class reads flow log data, processes it using lookup tables, 
 * and writes the extracted information to output files.
 */

public class FlowLogParser {
    public static void main(String[] args) {
        
        if (args.length < 3) {
            System.out.println("Usage: java FlowLogParser <flow_logs_file> <lookup_table_file> <protocol_lookup_file>");
            System.err.println("Please provide the correct number of arguments.");
            System.exit(1);
           
        }

        String flowLogFile = args[0];
        String lookupFile = args[1];
        String protocolLookupFile = args[2];

        try {
            Map<String, String> lookupTable = FileReaderUtil.loadLookupTable(lookupFile);
            Map<String, String> protocolLookupTable = FileReaderUtil.loadProtocolLookupTable(protocolLookupFile);
        
            FlowLogProcessor processor = new FlowLogProcessor(lookupTable, protocolLookupTable);
            FileReaderUtil.processFlowLogs(flowLogFile, processor);

            FileWriterUtil.writeTagCounts(processor.getTagCounts(), "tag_counts_output.txt");
            FileWriterUtil.writePortProtocolCounts(processor.getPortProtocolCounts(), "port_protocol_counts_output.txt");

            System.out.println("Processing completed successfully! Check the output files.");

        } catch (IOException e) {
            System.err.println("I/O Error: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("Unexpected Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
