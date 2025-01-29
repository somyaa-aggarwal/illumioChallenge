package test.unit.java.com.flowlog.parser.io;


import org.junit.jupiter.api.Test;

import main.java.com.flowlog.parser.io.FileReaderUtil;

import static org.junit.jupiter.api.Assertions.*;
import java.io.IOException;
import java.util.Map;

public class FileReaderUtilTest {

    @Test
    void testLoadLookupTable() throws IOException {
        Map<String, String> lookupTable = FileReaderUtil.loadLookupTable("resources/lookup_table.csv");
        assertNotNull(lookupTable);
        assertTrue(lookupTable.containsKey("443,tcp"));
        assertEquals("sv_P2", lookupTable.get("443,tcp"));
    }

    @Test
    void testLoadProtocolLookupTable() throws IOException {
        Map<String, String> protocolLookupTable = FileReaderUtil.loadProtocolLookupTable("resources/protocol_lookup.csv");
        assertNotNull(protocolLookupTable);
        assertEquals("tcp", protocolLookupTable.get("6"));
        assertEquals("udp", protocolLookupTable.get("17"));
    }
}
