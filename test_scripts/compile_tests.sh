#!/bin/bash

SRC_DIR="src/main/java"
TEST_DIR="test"
OUT_DIR="out"
LIBS="lib/*"

echo "🔹 Cleaning previous output files..."
rm -rf $OUT_DIR
mkdir -p $OUT_DIR

echo "🔹 Compiling source files..."
javac -d $OUT_DIR -cp "$LIBS" $(find $SRC_DIR -name "*.java")
if [ $? -ne 0 ]; then
    echo "Compilation failed for source files."
    exit 1
fi

echo "🔹 Compiling unit test files..."
javac -d $OUT_DIR -cp "$LIBS:$OUT_DIR" $(find $TEST_DIR/unit -name "*.java")
if [ $? -ne 0 ]; then
    echo "Compilation failed for unit test files."
    exit 1
fi

echo "🔹 Compiling integration test files..."
javac -d $OUT_DIR -cp "$LIBS:$OUT_DIR" $(find $TEST_DIR/integration -name "*.java")
if [ $? -ne 0 ]; then
    echo "Compilation failed for integration test files."
    exit 1
fi

echo "Compilation successful!"
