package application;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server extends Thread {
	
	private ServerSocket serverSocket;
	private ServerState serverState;
	
	public Server(int port) throws IOException {
		serverState = ServerState.Stopped;
		this.serverSocket = new ServerSocket(port);		
	}
	
	@Override
	public void run() {		
		serverState = ServerState.Running;		
		try {
			System.out.println("Server on, waiting for connection");
			while (serverSocket.isBound() && !serverSocket.isClosed()) {
				
				Socket clientSocket = serverSocket.accept();
				System.out.println("Connection accepted to address" + clientSocket.getInetAddress());
				
				PrintWriter out = new PrintWriter(clientSocket.getOutputStream(),
						true);
				BufferedReader in = new BufferedReader(new InputStreamReader(
						clientSocket.getInputStream()));
				
				WebPage.getInstance().runHtml(in.readLine(), clientSocket.getOutputStream(), serverState);
				
				String inputLine;
				
				while ((inputLine = in.readLine()) != null) {
					System.out.println("Server: " + inputLine);

					if (inputLine.trim().equals(""))
						break;
				}

				in.close();
				clientSocket.close();	
			}

		} 
		catch (IOException e) {} 	
		finally {
			try {
				serverSocket.close();
			} catch (IOException e) {}			
		}
	}
	
}
