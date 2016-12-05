package application;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

public class ClientCenter implements Runnable {
	private int state;
	private int Tid;
	private String function;
	private int source;
	private int dest;
	private int type;
	private double X;
	private double Y;
	private int direction;
	private String Stype;
	private Socket sock;
	private BufferedReader reader;
	private PrintStream writer;
	
	
	public ClientCenter(Socket socket) {
		try {
			sock = socket;
			// ���oSocket����J��Ƭy
			InputStreamReader isReader = new InputStreamReader(sock.getInputStream());
			reader = new BufferedReader(isReader);
			writer = new PrintStream(sock.getOutputStream());
			
		} catch (Exception ex) {
			System.out.println("�s������ in ClientCenter");
		}
	}

	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		String message;
		try {
			while ((message = reader.readLine()) != null) {
				System.out.println("����" + message);
				
				
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
