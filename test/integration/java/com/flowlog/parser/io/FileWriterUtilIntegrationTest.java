package test.integration.java.com.flowlog.parser.io;

import main.java.com.flowlog.parser.io.FileWriterUtil;
import org.junit.jupiter.api.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class FileWriterUtilIntegrationTest {
    private static final String OUTPUT_DIR = "output";  // Base directory for output files
    private static final String TAG_FILE_NAME = "integration_test_tag_counts.txt";
    private static final String PORT_PROTOCOL_FILE_NAME = "integration_test_port_protocol_counts.txt";
    private static final String TAG_FILE_PATH = OUTPUT_DIR + File.separator + TAG_FILE_NAME;
    private static final String PORT_PROTOCOL_FILE_PATH = OUTPUT_DIR + File.separator + PORT_PROTOCOL_FILE_NAME;

    @BeforeAll
    static void setup() {
        File outputDir = new File(OUTPUT_DIR);
        if (!outputDir.exists()) {
            assertTrue(outputDir.mkdirs(), "Failed to create output directory: " + OUTPUT_DIR);
        }
    }

    @AfterEach
    void cleanup() {
        // Cleanup test files after each test
        File tagFile = new File(TAG_FILE_PATH);
        File portFile = new File(PORT_PROTOCOL_FILE_PATH);

        if (tagFile.exists()) {
            assertTrue(tagFile.delete(), "Failed to delete test file: " + TAG_FILE_PATH);
        }
        if (portFile.exists()) {
            assertTrue(portFile.delete(), "Failed to delete test file: " + PORT_PROTOCOL_FILE_PATH);
        }

        // Clean up output directory if empty
        File outputDir = new File(OUTPUT_DIR);
        if (outputDir.isDirectory() && outputDir.list().length == 0) {
            assertTrue(outputDir.delete(), "Failed to delete empty output directory: " + OUTPUT_DIR);
        }
    }

    @Test
    @Order(1)
    void testWriteTagCounts_CreatesFileAndWritesCorrectData() {
        Map<String, Integer> tagCounts = new HashMap<>();
        tagCounts.put("sv_P1", 5);
        tagCounts.put("sv_P2", 3);
        tagCounts.put("sv_P3", 1);

        assertDoesNotThrow(() -> FileWriterUtil.writeTagCounts(tagCounts, TAG_FILE_NAME));

        File file = new File(TAG_FILE_PATH);
        assertTrue(file.exists(), "Output file should be created: " + TAG_FILE_PATH);

        try {
            String content = Files.readString(file.toPath());
            assertTrue(content.contains("Tag,Count"));
            assertTrue(content.contains("sv_P1,5"));
            assertTrue(content.contains("sv_P2,3"));
            assertTrue(content.contains("sv_P3,1"));
        } catch (IOException e) {
            fail("IOException while reading file: " + e.getMessage());
        }
    }

    @Test
    @Order(2)
    void testWritePortProtocolCounts_CreatesFileAndWritesCorrectData() {
        Map<String, Integer> portProtocolCounts = new HashMap<>();
        portProtocolCounts.put("8080,TCP", 10);
        portProtocolCounts.put("443,UDP", 7);

        assertDoesNotThrow(() -> FileWriterUtil.writePortProtocolCounts(portProtocolCounts, PORT_PROTOCOL_FILE_NAME));

        File file = new File(PORT_PROTOCOL_FILE_PATH);
        assertTrue(file.exists(), "Output file should be created: " + PORT_PROTOCOL_FILE_PATH);

        try {
            String content = Files.readString(file.toPath());
            assertTrue(content.contains("Port,Protocol,Count"));
            assertTrue(content.contains("8080,TCP,10"));
            assertTrue(content.contains("443,UDP,7"));
        } catch (IOException e) {
            fail("IOException while reading file: " + e.getMessage());
        }
    }

    @Test
    @Order(3)
    void testWriteTagCounts_HandlesEmptyMap() {
        Map<String, Integer> tagCounts = new HashMap<>();

        assertDoesNotThrow(() -> FileWriterUtil.writeTagCounts(tagCounts, TAG_FILE_NAME));

        File file = new File(TAG_FILE_PATH);
        assertFalse(file.exists(), "File should not be created for empty tag counts.");
    }

    @Test
    @Order(4)
    void testWritePortProtocolCounts_HandlesEmptyMap() {
        Map<String, Integer> portProtocolCounts = new HashMap<>();

        assertDoesNotThrow(() -> FileWriterUtil.writePortProtocolCounts(portProtocolCounts, PORT_PROTOCOL_FILE_NAME));

        File file = new File(PORT_PROTOCOL_FILE_PATH);
        assertFalse(file.exists(), "File should not be created for empty port-protocol counts.");
    }

    @Test
    @Order(5)
    void testWriteTagCounts_HandlesNullMap() {
        NullPointerException exception = assertThrows(NullPointerException.class, 
                () -> FileWriterUtil.writeTagCounts(null, TAG_FILE_NAME),
                "Expected NullPointerException for null tagCounts.");
        
        assertEquals("Tag counts map cannot be null.", exception.getMessage());
    }
    
    @Test
    @Order(6)
    void testWritePortProtocolCounts_HandlesNullMap() {
        NullPointerException exception = assertThrows(NullPointerException.class, 
                () -> FileWriterUtil.writePortProtocolCounts(null, PORT_PROTOCOL_FILE_NAME),
                "Expected NullPointerException for null portProtocolCounts.");
        
        assertEquals("Port-protocol counts map cannot be null.", exception.getMessage());
    }
}

