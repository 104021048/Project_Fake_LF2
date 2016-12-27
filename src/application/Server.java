package application;

import java.io.*;
import java.net.*;
import java.util.*;

public class Server {
	static Vector<PrintStream> output;
	static int playerTid;
	static Map<Integer, PrintStream> tMap = new HashMap<>();
	static Map<PrintStream, Integer> pMap = new HashMap<>();
	static Set<Integer> Live = new HashSet<Integer>();
	static Set<Integer> Locked = new HashSet<Integer>();
	static Set<Integer> Death = new HashSet<Integer>();
	static Map<Integer, Integer> tchoose = new HashMap<>();
	static Map<Integer, String> tname = new HashMap<>();

	// region
	public static void main(String args[]) {
		System.out.println("Server is ON...");
		output = new Vector<PrintStream>();
		try {
			ServerSocket serverSock = new ServerSocket(8888);
			while (true) {
				Socket acceptSocket = serverSock.accept();
				PrintStream writer = new PrintStream(acceptSocket.getOutputStream());
				System.out.println(writer);
				output.add(writer);
				playerTid = chooseTid();
				if (playerTid == -1) {
					// 人數已滿
					System.out.println("Server已滿....");
					Thread full=new Thread(new FullHandler(acceptSocket, writer));
					full.start();
					full=null;
					output.remove(output.lastElement());
					System.gc();

				} else {
					tMap.put(playerTid, writer);
					pMap.put(writer, playerTid);
					Thread t = new Thread(new ServerCenter(acceptSocket, playerTid, output, tMap, pMap, Live, Locked,
							Death, tchoose, tname, false));
					t.start();
				}
				System.out.println(acceptSocket.getLocalSocketAddress() +
				// 執行緒的在線次數
						"有" + (Thread.activeCount() - 1) +
						// 顯示連線人次
						"個連接");

			}
		} catch (Exception ex) {
			System.out.println("連接失敗" + ex.toString());
		}

	}

	// endregion
	public static int chooseTid() {
		int tid = -1, i = 1;
		while (i <= 4) {
			if (!Live.contains(i)) {
				tid = i;
				break;
			}
			i++;
		}
		return tid;

	}
}