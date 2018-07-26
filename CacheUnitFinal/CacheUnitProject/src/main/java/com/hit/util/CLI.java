package main.java.com.hit.util;

import java.io.*;
import java.util.*;

public class CLI extends Observable implements java.lang.Runnable {

	private InputStream inputStream = null;

	private OutputStream outputStream = null;

	public CLI(InputStream in, OutputStream out) {
		inputStream = in;
		outputStream = out;
	}

	public void run() {
		boolean runProgram = true;
		DataInputStream dataInputStream = null;
		String input = null;
		Scanner reader = new Scanner(inputStream);
		while (runProgram) {
			try {
				input = handleInput(reader);
				
				if (input.equals("START")) {
					System.out.println("Starting server...");
					dataInputStream = new DataInputStream(inputStream);
				}
				else if (input.equals("SHUTDOWN")) {
					System.out.println("shutdown server");
					runProgram = false;
					reader.close();
					if (dataInputStream != null)
						dataInputStream.close();
					if (inputStream != null)
						inputStream.close();
				}
				setChanged();
				notifyObservers(input);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public InputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	public OutputStream getOutputStream() {
		return outputStream;
	}

	public void setOutputStream(OutputStream outputStream) {
		this.outputStream = outputStream;
	}

	private String handleInput(Scanner reader)  {
		String input;
		
		System.out.println("Please enter your command");
		input = reader.nextLine();
		
		while(!input.equals("SHUTDOWN") && !input.equals("START")) 
		{
				System.out.println("Not a valid command");
				System.out.println("Please enter your command");
				input = reader.nextLine();
			}
		return input;
	}


}
