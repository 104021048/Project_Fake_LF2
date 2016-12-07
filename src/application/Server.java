package application;
import java.io.*;
import java.net.*;
import java.util.*;

public class Server {
	static Vector<PrintStream> output;
	static int playerTid = 0;
	static Map<Integer, PrintStream> tMap = new HashMap<>();
	static Map<PrintStream,Integer> pMap = new HashMap<>();
	static Set<Integer> Live= new HashSet<Integer>();
	static Set<Integer> Locked= new HashSet<Integer>();
	static Set<Integer> Death= new HashSet<Integer>();
	static Map<Integer, Integer> tchoose = new HashMap<>();
	static Map<Integer, String> tname = new HashMap<>();
	public static void main(String args[]) {
		output = new Vector<PrintStream>();
		try {
			ServerSocket serverSock = new ServerSocket(8888);
			while (true) {
				Socket acceptSocket = serverSock.accept();
				PrintStream writer = new PrintStream(acceptSocket.getOutputStream());
				System.out.println(writer);
				output.add(writer);
				playerTid++;
				tMap.put(playerTid, writer);
				pMap.put(writer,playerTid);
				Thread t = new Thread(new ServerCenter(acceptSocket, playerTid, output, tMap,pMap,Live,Locked,Death,tchoose,tname, false));
				t.start();
				
				System.out.println(acceptSocket.getLocalSocketAddress() +
				// ��������b�u����
						"��" + (Thread.activeCount() - 1) +
						// ��ܳs�u�H��
						"�ӳs��");

			}
			//countdown!!!
		} catch (Exception ex) {
			System.out.println("�s������" + ex.toString());
		}

	}
	
	
}
