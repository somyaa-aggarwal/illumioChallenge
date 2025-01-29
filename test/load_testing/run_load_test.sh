#!/bin/bash

# Exit on errors
set -e

# Define project root directory
PROJECT_ROOT=$(pwd)

# Define input files
LOOKUP_TABLE="$PROJECT_ROOT/test/load_testing/data_generation/generated_lookup_table.csv"
FLOW_LOGS="$PROJECT_ROOT/test/load_testing/data_generation/generated_flow_logs.txt"
PROTOCOL_LOOKUP="$PROJECT_ROOT/resources/protocol_lookup.csv"

# Define output directory and files
OUTPUT_DIR="$PROJECT_ROOT/output"
TAG_COUNTS="$OUTPUT_DIR/load_test_tag_counts_output.txt"
PORT_PROTOCOL_COUNTS="$OUTPUT_DIR/load_test_port_protocol_counts_output.txt"

# Ensure output directory exists
mkdir -p "$OUTPUT_DIR"

# Print debug information
echo "Starting Load Test..."
echo "Project Root: $PROJECT_ROOT"
echo "Lookup Table: $LOOKUP_TABLE"
echo "Flow Logs: $FLOW_LOGS"
echo "Protocol Lookup: $PROTOCOL_LOOKUP"
echo "Output Directory: $OUTPUT_DIR"

# Verify that input files exist
if [[ ! -f "$LOOKUP_TABLE" ]]; then
    echo "Error: Lookup table not found at $LOOKUP_TABLE"
    exit 1
fi

if [[ ! -f "$FLOW_LOGS" ]]; then
    echo "Error: Flow logs file not found at $FLOW_LOGS"
    exit 1
fi

if [[ ! -f "$PROTOCOL_LOOKUP" ]]; then
    echo "Error: Protocol lookup file not found at $PROTOCOL_LOOKUP"
    exit 1
fi

# Start timing execution
start_time=$(date +%s)

# Run the Flow Log Parser
java -cp out main.java.com.flowlog.parser.FlowLogParser  "$FLOW_LOGS" "$LOOKUP_TABLE" "$PROTOCOL_LOOKUP"

# End timing execution
end_time=$(date +%s)
execution_time=$((end_time - start_time))

# Display results
echo "Load Test Completed!"
echo "Execution Time: $execution_time seconds"
echo "Output Files:"
echo "  - $TAG_COUNTS"
echo "  - $PORT_PROTOCOL_COUNTS"
