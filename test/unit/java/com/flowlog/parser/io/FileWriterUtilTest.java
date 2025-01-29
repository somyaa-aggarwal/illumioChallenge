package test.unit.java.com.flowlog.parser.io;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import main.java.com.flowlog.parser.io.FileWriterUtil;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class FileWriterUtilTest {
    private static final String OUTPUT_DIR = "output"; // Single output directory
    private static final String FILE_NAME = "test_tag_counts.txt"; // Use only file name
    private static final String FILE_PATH = OUTPUT_DIR + File.separator + FILE_NAME;

    @BeforeEach
    void setup() {
        File outputDir = new File(OUTPUT_DIR);
        if (!outputDir.exists()) {
            assertTrue(outputDir.mkdirs(), "Failed to create output directory: " + OUTPUT_DIR);
        }
    }

    @Test
    void testWriteTagCounts() {
        Map<String, Integer> tagCounts = new HashMap<>();
        tagCounts.put("sv_P1", 2);
        tagCounts.put("sv_P2", 1);

        try {
            FileWriterUtil.writeTagCounts(tagCounts, FILE_NAME);
        } catch (Exception e) {
            fail("Exception while writing file: " + e.getMessage());
        }

        File file = new File(FILE_PATH);
        if (!file.exists()) {
            fail("Output file was not created: " + FILE_PATH);
        }

        try {
            String content = Files.readString(file.toPath());
            assertTrue(content.contains("sv_P1,2"), "Output file does not contain expected data: sv_P1,2");
            assertTrue(content.contains("sv_P2,1"), "Output file does not contain expected data: sv_P2,1");
        } catch (IOException e) {
            fail("Exception while reading file: " + e.getMessage());
        }
    }
}
