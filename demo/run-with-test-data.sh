#!/bin/bash
# Script to run Spring Boot application with test data loaded

set -e

echo "================================"
echo "Spring Boot App with Test Data"
echo "================================"
echo ""

# Navigate to the demo directory
SCRIPT_DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"
cd "$SCRIPT_DIR"

echo "[1/3] Building the project..."
./mvnw.cmd clean package -DskipTests > /dev/null 2>&1 || mvn clean package -DskipTests > /dev/null 2>&1
echo "✓ Build completed"
echo ""

echo "[2/3] Loading test data..."
echo "Starting Spring Boot application with dev profile (test data enabled)"
echo ""

# Run with dev profile to enable DataLoader
./mvnw.cmd spring-boot:run -Dspring-boot.run.arguments="--spring.profiles.active=dev" || \
  mvn spring-boot:run -Dspring-boot.run.arguments="--spring.profiles.active=dev"
