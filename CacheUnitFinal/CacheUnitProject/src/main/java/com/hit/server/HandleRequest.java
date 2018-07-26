package main.java.com.hit.server;

import java.io.*;
import java.lang.reflect.Type;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import main.java.com.hit.dm.DataModel;
import main.java.com.hit.services.CacheUnitController;

@SuppressWarnings("hiding")
public class HandleRequest<String> implements Runnable {

	private Socket socket = null;

	private CacheUnitController<String> cacheUnitController = null;


	private int totalReq;
	private int totalDMs;
	private int totalSwaps;

	
	private ObjectOutputStream outputStream = null;
	
	public ObjectOutputStream getOutputStream() {
		return outputStream;
	}

	public ObjectInputStream getInputStream() {
		return inputStream;
	}

	private ObjectInputStream inputStream = null;

	public HandleRequest(Socket s, CacheUnitController<String> controller) {
		socket = s;
		cacheUnitController = controller;
	}

	@SuppressWarnings("unchecked")
	public void run() {
		
	    try {
	    	inputStream = new ObjectInputStream(socket.getInputStream());
	    }
	    catch(IOException e)
	    {
	    	e.printStackTrace();
	    }
	    
	    String response;
	    
	    try {
			System.out.println("Connection established\n");
			Type ref = new TypeToken<Request<DataModel<String>[]>>() {}.getType(); // parse json into array
			response = (String) inputStream.readObject();
			
			Request<DataModel<String>[]> request = new Gson().fromJson((java.lang.String) response, ref);
			
			makeAction(request);

		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			try {
				inputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private void makeAction(Request<DataModel<String>[]> request) {
		Map<java.lang.String, java.lang.String> actions = request.getHeaders();
		Map<java.lang.String, java.lang.String> requestToSend;

		for (Entry<java.lang.String, java.lang.String> item : actions.entrySet()) {
			java.lang.String currentAction = item.getValue();

			switch (currentAction) {
			case "DELETE":
				loadStatisticsToProgramm();
				try {
					cacheUnitController.delete(request.getBody());
					requestToSend = createRequest("STATUS", "succeeded");
				} catch (Exception e) {
					requestToSend = createRequest("STATUS", "failed");
					System.out.println(e.getMessage());
				}

				totalReq++;
				totalDMs += request.getBody().length;
				updateStatisticsFile();
				sendRequest(requestToSend);
				break;
			case "UPDATE":
				loadStatisticsToProgramm();
				try {
					cacheUnitController.update(request.getBody());
					requestToSend = createRequest("STATUS", "Succeeded");
				} catch (Exception e) {
					requestToSend = createRequest("STATUS", "Failed");
					System.out.println(e.getMessage());
				}
				totalReq++;
				totalDMs += request.getBody().length;
				totalSwaps += cacheUnitController.getCacheUnitService().getSwaps();
				updateStatisticsFile();
				sendRequest(requestToSend);
				break;

			case "GET":
				loadStatisticsToProgramm();
				try {
					cacheUnitController.get(request.getBody());
					requestToSend = createRequest("STATUS", "succeeded");
				} catch (Exception e) {
					requestToSend = createRequest("STATUS", "failed");
					System.out.println(e.getMessage());
				}
				totalReq++;
				totalDMs += request.getBody().length;
				updateStatisticsFile();
				sendRequest(requestToSend);
				break;

			case "CAPACITY":
				requestToSend = createRequest("CAPACITY",
						Integer.toString(cacheUnitController.getCacheUnitService().getCapacity()));
				sendRequest(requestToSend);
				break;
			case "ALGO":
				requestToSend = createRequest("ALGO", cacheUnitController.getCacheUnitService().getAlgorithm());
				sendRequest(requestToSend);
				break;
			case "SWAPS":
				loadStatisticsToProgramm();
				requestToSend = createRequest("SWAPS", Integer.toString(totalSwaps));
				sendRequest(requestToSend);
				break;
			case "REQUESTS":
				loadStatisticsToProgramm();
				requestToSend = createRequest("REQUESTS", Integer.toString(totalReq));
				sendRequest(requestToSend);
				break;
			case "DMS":
				loadStatisticsToProgramm();
				requestToSend = createRequest("DMS", Integer.toString(totalDMs));
				sendRequest(requestToSend);
				break;

			default:
				System.out.println("Wrong json header");
				break;
			}
		}
	}

	private Map<java.lang.String, java.lang.String> createRequest(java.lang.String requestStr,
			java.lang.String requestUns) {
		Map<java.lang.String, java.lang.String> entry = new HashMap<java.lang.String, java.lang.String>();
		entry.put(requestStr, requestUns);

		return entry;
	}

	@SuppressWarnings("unchecked")
	private void sendRequest(Map<java.lang.String, java.lang.String> request) {
		String json = (String) "";
		try {
			outputStream = new ObjectOutputStream(socket.getOutputStream());
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
		Gson gson = new Gson();
		json = (String) gson.toJson(request);

		try {
			outputStream.writeObject(json);
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}

		try {
			outputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void updateStatisticsFile() {

		try {
			File file = new File("statistics.txt");
	        PrintStream out = new PrintStream(file);
	        out.println(Integer.toString(totalReq));
	        out.println(Integer.toString(totalDMs));
	        out.println(Integer.toString(totalSwaps));
	        out.close();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	private void loadStatisticsToProgramm() {
		try {

			File file = new File("statistics.txt");
			@SuppressWarnings("resource")
			Scanner scanner = new Scanner(file);
			totalReq = scanner.nextInt();
			totalDMs = scanner.nextInt();
			totalSwaps = scanner.nextInt();

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

}