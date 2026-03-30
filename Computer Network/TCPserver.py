import socket 
def start_server(): 
    server_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)  # Create socket 
    server_host = "127.0.0.1"  # Localhost 
    server_port = 12345  # Port number 
    # Bind the socket to the address and port 
    server_socket.bind((server_host, server_port)) 
    
    # Start listening for connections (max 5 clients in queue) 
    server_socket.listen(5) 
    print(f"Server is listening on {server_host}:{server_port}...") 
 
    # Accept connection from a client 
    client_socket, client_address = server_socket.accept() 
    print(f"Connection established with {client_address}") 
 
    # Receive data from client 
    data = client_socket.recv(1024).decode() 
    print(f"Received from client: {data}") 
 
    # Send response to client 
    response = "Hello Client, Message Received!" 
    client_socket.send(response.encode()) 
 
    # Close the connection 
    client_socket.close() 
    server_socket.close() 
 
# Run the server 
start_server() 