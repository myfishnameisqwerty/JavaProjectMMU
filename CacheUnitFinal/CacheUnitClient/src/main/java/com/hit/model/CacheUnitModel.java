package main.java.com.hit.model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Observable;


import com.google.gson.Gson;

public class CacheUnitModel extends Observable implements Model {

	private CacheUnitClient unitClient = null;


	private String response;

	private String totalReq;
	private String totalDMs;
	private String totalSwaps;

	private String capacity;
	private String algo;

	public CacheUnitModel() {
		unitClient = new CacheUnitClient();
	}

	public String getTotalReq() {
		return totalReq;
	}

	public void setTotalReq(String totalReq) {
		this.totalReq = totalReq;
	}

	public String getTotalDMs() {
		return totalDMs;
	}

	public void setTotalDMs(String totalDMs) {
		this.totalDMs = totalDMs;
	}

	public String getTotalSwaps() {
		return totalSwaps;
	}

	public void setTotalSwaps(String totalSwaps) {
		this.totalSwaps = totalSwaps;
	}
	
	public String getCapacity() {
		return capacity;
	}

	public void setCapacity(String capacity) {
		this.capacity = capacity;
	}

	public String getAlgo() {
		return algo;
	}

	public void setAlgo(String algo) {
		this.algo = algo;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> void updateModelData(T t) {
		try {
			response = unitClient.send((String)t);
			Map<String, String> answerServer = new Gson().fromJson(response, Map.class);
			
			for (Entry<String, String> item : answerServer.entrySet()) {
				switch (item.getKey()) 
				{
				case "CAPACITY":
					this.setCapacity(item.getValue());
					break;
				case "ALGO":
					this.setAlgo(item.getValue());
					break;
				case "SWAPS":
					this.setTotalSwaps(item.getValue());
					break;
				case "REQUESTS":
					this.setTotalReq(item.getValue());
					break;
				case "DMS":
					this.setTotalDMs(item.getValue());
					break;
				}
				setChanged();
				notifyObservers((String)item.getKey());
			}
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	public String sendJsonString(String jsonString)
	{
		return unitClient.sendJsonFile(jsonString);
	}
	
	@SuppressWarnings("resource")
	public String loadFile()
	{
		StringBuilder contentBuilder = new StringBuilder();
	    try
	    {
	    	BufferedReader br = new BufferedReader(new FileReader("Example.json"));
	        String sCurrentLine;
	        while ((sCurrentLine = br.readLine()) != null)
	        {
	            contentBuilder.append(sCurrentLine).append("\n");
	        }
	    }
	    catch(Exception e)
	    {
	    	System.out.println(e.getMessage());
	    }
	    
	    return contentBuilder.toString();
	}
}