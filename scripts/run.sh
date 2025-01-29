#!/bin/bash
echo "Running Flow Log Parser..."
java -cp out main.java.com.flowlog.parser.FlowLogParser resources/flow_logs.txt resources/lookup_table.csv resources/protocol_lookup.csv
echo "Execution Complete! Check output files."
