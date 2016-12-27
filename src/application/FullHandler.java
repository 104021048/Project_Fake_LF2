package application;

import java.io.*;
import java.net.*;
import java.util.*;

public class FullHandler implements Runnable {
	private PrintStream writer;
	private Socket acceptSocket;

	public FullHandler(Socket acceptSocket, PrintStream writer) {
		try {
			this.acceptSocket = acceptSocket;
			this.writer = writer;		
		} catch (Exception ex) {
			ex.printStackTrace();
			System.out.println("FullHandler有錯");
		}
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		writer.println("下次請早.....");
		writer.flush();
		try {
			acceptSocket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
