#!/bin/bash
echo "Compiling Java Files..."
rm -rf out/
mkdir -p out
#javac -d out $(find src/main/java -name "*.java")
javac -d out src/main/java/com/flowlog/parser/FlowLogParser.java src/main/java/com/flowlog/parser/io/FileReaderUtil.java src/main/java/com/flowlog/parser/io/FileWriterUtil.java src/main/java/com/flowlog/parser/processor/FlowLogProcessor.java
echo "Compilation Complete!"


