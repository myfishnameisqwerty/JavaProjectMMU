package main.java.com.hit.model;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JOptionPane;

import com.google.gson.Gson;


public class CacheUnitClient {
	private static final int PORT = 12345;


	private Socket socket;
	
	private ObjectOutputStream outputStream = null;
	private ObjectInputStream inputStream = null;

	public CacheUnitClient() {
		try {
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
	}

	public Socket getSocket() {
		if(socket == null)
		{
			try {
				socket = new Socket (InetAddress.getLocalHost().getHostAddress(), PORT);
			} catch (Exception e) {
			System.out.println(e.getMessage());
			}
		}
		return socket;
	}
	
	public String sendJsonFile(String jsonString) {
		try {
			socket = new Socket(InetAddress.getLocalHost().getHostAddress(), PORT);
			outputStream = new ObjectOutputStream(socket.getOutputStream());
		} catch (UnknownHostException e) {

			System.out.println(e.getMessage());
		} catch (IOException e) {

			System.out.println(e.getMessage());
		}

		try {
			outputStream.writeObject(jsonString);
			inputStream = new ObjectInputStream(socket.getInputStream());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		String response = "";

		try {
			response = (String) inputStream.readObject();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		}

		try {
			socket.close();
			outputStream.close();
			inputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return response;
	}

	public String send(String request) {
		Gson gson = new Gson();
		Request<Object> requestItem = createRequest(request);
		String json = gson.toJson(requestItem);
		
		try {
			socket = new Socket (InetAddress.getLocalHost().getHostAddress(), PORT);
			outputStream = new ObjectOutputStream(socket.getOutputStream ());
		} catch (IOException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
		
		try {
			outputStream.writeObject(json);
			inputStream = new ObjectInputStream (socket.getInputStream ());
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
		
		String response = "";
		
		try { 
			response = (String) inputStream.readObject();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		}
		
		try {
			socket.close();
            outputStream.close (); 
            inputStream.close();
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		return response;
	}

	
	private Request<Object> createRequest(String requestStr) {
		Map<java.lang.String, java.lang.String> entry = new HashMap<String, String>();
		entry.put("actions", requestStr);

		Request<Object> request = new Request<Object>(entry, null);

		return request;
	}

}