#!/bin/bash

echo "🔹 Running Unit Tests..."
java -jar lib/junit-platform-console-standalone-1.10.0.jar --class-path out --select-package test.unit

if [ $? -eq 0 ]; then
    echo "Unit tests passed!"
else
    echo "Some unit tests failed!"
    exit 1
fi
