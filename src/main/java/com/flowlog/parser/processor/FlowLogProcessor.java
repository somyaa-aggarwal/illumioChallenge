package main.java.com.flowlog.parser.processor;


import java.util.HashMap;
import java.util.Map;


/**
 * Processes flow log entries by extracting port-protocol combinations
 * and mapping them to tags using lookup tables.
 * 
 * This class maintains counts of tags and port-protocol combinations
 * for further analysis.
 * 
 */


public class FlowLogProcessor {
    private final Map<String, String> lookupTable;
    private final Map<String, String> protocolLookupTable;
    private final Map<String, Integer> tagCounts = new HashMap<>();
    private final Map<String, Integer> portProtocolCounts = new HashMap<>();


   /**
     * Constructs a FlowLogProcessor with the given lookup tables.
     *
     * @param lookupTable         A mapping of port-protocol combinations to tags.
     * @param protocolLookupTable A mapping of protocol numbers to their names.
     */
    public FlowLogProcessor(Map<String, String> lookupTable, Map<String, String> protocolLookupTable) {
        this.lookupTable = lookupTable;
        this.protocolLookupTable = protocolLookupTable;
    }
    
    /**
     * Processes a single flow log entry by extracting the destination port and protocol,
     * determining its tag, and updating internal count mappings.
     *
     * @param log A string array representing a single flow log entry,
     *            where index 5 contains the destination port and index 7 contains the protocol number.
     */
    public void processLogEntry(String[] log) {
        String dstPort = log[5].trim();
        String protocolNum = log[7].trim();
        
        if (!protocolLookupTable.containsKey(protocolNum)) {
            System.err.println("Warning: Protocol number " + protocolNum + " does not have a mapping in the protocol lookup table. Rectfify protocol lookup source");
        }
        String protocol = protocolLookupTable.getOrDefault(protocolNum, "unknown").toLowerCase();

        // Creating a key for lookup (destination port + protocol)
        String key = dstPort + "," + protocol;

        String tag = lookupTable.getOrDefault(key, "Untagged");

        tagCounts.put(tag, tagCounts.getOrDefault(tag, 0) + 1);
        
        String portProtocolKey = dstPort + "," + protocol;
        portProtocolCounts.put(portProtocolKey, portProtocolCounts.getOrDefault(portProtocolKey, 0) + 1);
    }


    /**
     * Retrieves the accumulated counts of tags extracted from flow logs.
     *
     * @return A map where keys are tags and values are their respective counts.
     */

    public Map<String, Integer> getTagCounts() {
        return tagCounts;
    }

    
    /**
     * Retrieves the accumulated counts of port-protocol combinations found in flow logs.
     *
     * @return A map where keys are port-protocol combinations and values are their respective counts.
     */
    public Map<String, Integer> getPortProtocolCounts() {
        return portProtocolCounts;
    }
}
