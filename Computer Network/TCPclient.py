import socket 
 
def start_client(): 
    client_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)  # Create socket 
    server_host = "127.0.0.1"  # Localhost (same as server) 
    server_port = 12345  # Must match the server's port 
 
    # Connect to the server 
    client_socket.connect((server_host, server_port)) 
    print(f"Connected to server at {server_host}:{server_port}") 
 
    # Send message to server 
    message = "Hello Server!" 
    client_socket.send(message.encode()) 
 
    # Receive response from server 
    response = client_socket.recv(1024).decode() 
    print(f"Server Response: {response}") 
 
    # Close the connection 
    client_socket.close() 
 
# Run the client 
start_client()