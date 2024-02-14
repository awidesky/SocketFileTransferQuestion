package sec04_Filexchange;

import sec04_Filexchange.EX01.FileClient;
import sec04_Filexchange.EX02.FileServer;

public class Main {

	public static void main(String[] args) {
		
		new Thread(FileServer::run).start();
		new Thread(FileClient::run).start();

	}

}
