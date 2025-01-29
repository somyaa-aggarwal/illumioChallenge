#!/bin/bash

echo "🔹 Running Integration Tests..."
java -jar lib/junit-platform-console-standalone-1.10.0.jar --class-path out --select-package test.integration

if [ $? -eq 0 ]; then
    echo "Integration tests passed!"
else
    echo "Some integration tests failed!"
    exit 1
fi
