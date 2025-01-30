import random
import csv
import os


protocols_ports = {
    "tcp": [20, 21, 22, 23, 25, 80, 110, 143, 443, 3389, 993, 8080],
    "udp": [53, 67, 68, 69, 123, 161, 162, 500, 520, 33434, 65000],
    "icmp": [0, 8, 31, 128, 255],
    "http": [80],
    "https": [443],
    "ftp": [20, 21],
    "dns": [53],
    "dhcp": [67, 68],
    "snmp": [161, 162],
    "ntp": [123],
    "smtp": [25],
    "pop3": [110],
    "imap": [143]
}


tags = [
    "sv_P1", "sv_P2", "sv_P3", "sv_P4", "sv_P5", "email", "database_query", 
    "dns", "voip_traffic", "vpn", "ftp", "remote", "snmp", "ntp", 
    "firewall", "load_balancer", "database", "container_runtime", 
    "security", "api_calls"
]


DEFAULT_MAPPINGS = 10000


output_dir = "test/load-testing/data_generation/"
os.makedirs(output_dir, exist_ok=True)


input_lookup_file = "resources/lookup_table.csv"  
output_lookup_file = os.path.join(output_dir, "generated_lookup_table.csv")


def generate_lookup_table(num_entries=DEFAULT_MAPPINGS):
    """
    Generate a lookup table with random mappings of port, protocol, and tag.

    :param num_entries: Number of entries to generate.
    :return: List of lookup table entries.
    """
    data = []
    for _ in range(num_entries):
        protocol = random.choice(list(protocols_ports.keys()))
        port = random.choice(protocols_ports[protocol])
        tag = random.choice(tags)
        data.append([port, protocol, tag])
    return data


def save_lookup_table(data, filename=output_lookup_file):
    """
    Save generated lookup table to a CSV file.

    :param data: List of lookup table entries.
    :param filename: Path to save the CSV file.
    """
    header = ['dstport', 'protocol', 'tag']
    with open(filename, 'w', newline='') as file:
        writer = csv.writer(file)
        writer.writerow(header)
        writer.writerows(data)
    print(f"Generated lookup table saved to {filename}")


if __name__ == "__main__":
    # Prompt user for custom number of mappings
    try:
        num_entries = int(input(f"Enter number of lookup table entries (default {DEFAULT_MAPPINGS}): ") or DEFAULT_MAPPINGS)
        print(f"Generating {num_entries} lookup table entries...")
        data = generate_lookup_table(num_entries)
        save_lookup_table(data)
    except ValueError:
        print("Invalid input. Using default value of 10,000 entries.")
        data = generate_lookup_table(DEFAULT_MAPPINGS)
        save_lookup_table(data)