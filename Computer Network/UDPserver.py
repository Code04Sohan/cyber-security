import socket 
def start_udp_server(): 
    server_socket = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)  # Create UDP socket 
    server_host = "127.0.0.1"  # Localhost 
    server_port = 12345  # Port number 
    # Bind the socket to the address and port 
    server_socket.bind((server_host, server_port)) 
    print(f"UDP Server is listening on {server_host}:{server_port}...") 
 
    while True: 
        # Receive message from client 
        data, client_address = server_socket.recvfrom(1024) 
        print(f"Received from {client_address}: {data.decode()}") 
 
        # Send response to client 
        response = "Hello Client, UDP Message Received!" 
        server_socket.sendto(response.encode(), client_address) 
 
# Run the server 
start_udp_server()