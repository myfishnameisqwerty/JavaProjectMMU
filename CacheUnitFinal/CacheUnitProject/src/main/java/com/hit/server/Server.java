package main.java.com.hit.server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Observable;
import java.util.Observer;

import main.java.com.hit.services.CacheUnitController;

public class Server implements Observer {

	private static final int PORT = 12345;

	private ServerSocket server = null;

	public Server() {
	}

	@Override
	public void update(Observable o, Object arg) {
		String input = (String) arg;

		if (input.equals("START")) {
			start();
		} else {
			try {
				server.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public ServerSocket getServer() {
		return server;
	}

	public void setServer(ServerSocket server) {
		this.server = server;
	}

	public void start() {
		try {
			server = new ServerSocket(PORT);

			while (!server.isClosed()) {
				Socket client = server.accept();
				Thread t = new Thread(new HandleRequest<String>(client, new CacheUnitController<String>()));
				t.start();
			}

		} catch (Exception e) {
			System.out.println("Connectiont wasn't established. ~Server");
		}
	}

}
