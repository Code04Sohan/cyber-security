import socket 
 
def start_udp_client(): 
    client_socket = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)  # Create UDP socket 
    server_host = "127.0.0.1"  # Localhost (same as server) 
    server_port = 12345  # Must match the server's port 
 
    # Send message to server 
    message = "Hello UDP Server!" 
    client_socket.sendto(message.encode(), (server_host, server_port)) 
 
    # Receive response from server 
    response, server_address = client_socket.recvfrom(1024) 
    print(f"Server Response: {response.decode()}") 
 
    # Close the socket 
    client_socket.close() 
 
# Run the client 
start_udp_client() 