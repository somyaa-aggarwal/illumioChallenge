import random
import time
import csv
import os


PROJECT_ROOT = os.path.abspath(os.path.join(os.path.dirname(__file__), "../../../"))  
RESOURCE_DIR = os.path.join(PROJECT_ROOT, "resources")  
PROTOCOL_LOOKUP_FILE = os.path.join(RESOURCE_DIR, "protocol_lookup.csv")
OUTPUT_FILE = os.path.join(PROJECT_ROOT, "test/load_testing/data_generation", "generated_flow_logs30M.txt")

# Default number of logs to generate
DEFAULT_NUM_LOGS = 100000


def load_protocol_lookup():
    protocol_lookup = {}
    if not os.path.exists(PROTOCOL_LOOKUP_FILE):
        print(f"Error: {PROTOCOL_LOOKUP_FILE} not found.")
        return protocol_lookup  # Return empty dict instead of crashing

    try:
        with open(PROTOCOL_LOOKUP_FILE, "r") as file:
            reader = csv.reader(file)
            next(reader)  # Skip header
            for row in reader:
                if len(row) >= 2:
                    protocol_lookup[row[0].strip()] = row[1].strip().lower()
    except Exception as e:
        print(f"Error reading {PROTOCOL_LOOKUP_FILE}: {e}")

    return protocol_lookup


def generate_flow_log(protocol_lookup):
    version = 2
    packet_id = random.randint(100000000000, 999999999999)
    eni_id = f"eni-{random.randint(1000000000, 9999999999)}"
    src_ip = f"10.0.{random.randint(0, 255)}.{random.randint(1, 254)}"
    dest_ip = f"198.51.100.{random.randint(1, 254)}"
    src_port = random.randint(1024, 65535)  # Random high port
    dst_port = random.choice([22, 23, 25, 53, 80, 110, 143, 443, 993, 3389])  # Common service ports
    protocol_num = random.choice(list(protocol_lookup.keys()))
    protocol = protocol_lookup.get(protocol_num, "unknown")
    packet_size = random.randint(20, 1500)
    total_bytes = random.randint(20000, 50000)
    start_time = int(time.time())
    end_time = start_time + random.randint(1, 10)
    action = random.choice(['ACCEPT', 'DROP'])
    status = random.choice(['OK', 'FAIL'])

    return f"{version} {packet_id} {eni_id} {src_ip} {dest_ip} {src_port} {dst_port} {protocol_num} {packet_size} {total_bytes} {start_time} {end_time} {action} {status}"


def generate_logs(num_logs=DEFAULT_NUM_LOGS):
    protocol_lookup = load_protocol_lookup()
    if not protocol_lookup:
        print("Error: Protocol lookup data missing. Cannot generate logs.")
        return

    with open(OUTPUT_FILE, "w") as file:
        for _ in range(num_logs):
            file.write(generate_flow_log(protocol_lookup) + "\n")

    print(f"Successfully generated {num_logs} flow logs in {OUTPUT_FILE}")


if __name__ == "__main__":
    num_logs = input(f"Enter number of logs to generate (default {DEFAULT_NUM_LOGS}): ") or DEFAULT_NUM_LOGS
    try:
        num_logs = int(num_logs)
        generate_logs(num_logs)
    except ValueError:
        print("Invalid input! Generating default number of logs...")
        generate_logs()
