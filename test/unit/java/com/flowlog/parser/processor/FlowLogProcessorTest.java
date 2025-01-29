
package test.unit.java.com.flowlog.parser.processor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import main.java.com.flowlog.parser.processor.FlowLogProcessor;
import static org.junit.jupiter.api.Assertions.*;
import java.util.HashMap;
import java.util.Map;

public class FlowLogProcessorTest {

    private FlowLogProcessor processor;

    @BeforeEach
    void setUp() {
        Map<String, String> lookupTable = new HashMap<>();
        lookupTable.put("443,tcp", "sv_P2");
        lookupTable.put("25,tcp", "sv_P1");
        lookupTable.put("110,tcp", "email");

        Map<String, String> protocolLookupTable = new HashMap<>();
        protocolLookupTable.put("6", "tcp");
        protocolLookupTable.put("17", "udp");

        processor = new FlowLogProcessor(lookupTable, protocolLookupTable);
    }

    @Test
    void testProcessLogEntry_validMapping() {
        String[] log = {"2", "123456789012", "eni-0a1b2c3d", "10.0.1.201", "198.51.100.2", "443", "49153", "6", "25", "20000", "1620140761", "1620140821"};
        processor.processLogEntry(log);

        assertEquals(1, processor.getTagCounts().get("sv_P2"));
        assertEquals(1, processor.getPortProtocolCounts().get("443,tcp"));
    }

    @Test
    void testProcessLogEntry_unknownMapping() {
        String[] log = {"2", "123456789012", "eni-xyz", "192.168.1.1", "198.51.100.3", "9999", "49154", "6", "20", "20000", "1620140761", "1620140821"};
        processor.processLogEntry(log);

        assertEquals(1, processor.getTagCounts().get("Untagged"));
        assertEquals(1, processor.getPortProtocolCounts().get("9999,tcp"));
    }
}
