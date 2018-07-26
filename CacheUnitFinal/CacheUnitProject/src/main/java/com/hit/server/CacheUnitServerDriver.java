package main.java.com.hit.server;

import main.java.com.hit.util.CLI;

public class CacheUnitServerDriver {

	public static void main(String[] args) {
		try {
			CLI cli = new CLI(System.in, System.out);
			Server server = new Server();
			cli.addObserver(server);
			new Thread(cli).start();
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage()); 
		}

	}

}
