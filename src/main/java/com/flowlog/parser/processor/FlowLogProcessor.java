package main.java.com.flowlog.parser.processor;


import java.util.HashMap;
import java.util.Map;

public class FlowLogProcessor {
    private final Map<String, String> lookupTable;
    private final Map<String, String> protocolLookupTable;
    private final Map<String, Integer> tagCounts = new HashMap<>();
    private final Map<String, Integer> portProtocolCounts = new HashMap<>();

    public FlowLogProcessor(Map<String, String> lookupTable, Map<String, String> protocolLookupTable) {
        this.lookupTable = lookupTable;
        this.protocolLookupTable = protocolLookupTable;
    }

    public void processLogEntry(String[] log) {
        String dstPort = log[5].trim();
        String protocolNum = log[7].trim();

        String protocol = protocolLookupTable.getOrDefault(protocolNum, "unknown").toLowerCase();
        String key = dstPort + "," + protocol;
        String tag = lookupTable.getOrDefault(key, "Untagged");

        tagCounts.put(tag, tagCounts.getOrDefault(tag, 0) + 1);
        
        String portProtocolKey = dstPort + "," + protocol;
        portProtocolCounts.put(portProtocolKey, portProtocolCounts.getOrDefault(portProtocolKey, 0) + 1);
    }

    public Map<String, Integer> getTagCounts() {
        return tagCounts;
    }

    public Map<String, Integer> getPortProtocolCounts() {
        return portProtocolCounts;
    }
}
