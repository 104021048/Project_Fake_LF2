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
	private String ta[];
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
				if (message.contains("#")) {
					// �o�O�]���Ȯɴ��եΪ���client�|��W�r:���b�e��
					decoder(message);
					System.out.println(message);
				}

			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void decoder(String message) {
		// ��message��#���}
		ta = message.split("#");
		// System.out.print("�Ѷ}�T��:");
		// for (int i = 0; i < ta.length; i++) {
		// System.out.print(ta[i] + "+");
		// }
		// System.out.println();
		// ��Ҧ���}���T���̧Ƕ�Jthread�����Ѽ�
		state = Integer.parseInt(ta[0]);
		Tid = Integer.parseInt(ta[1]);
		function = ta[2];
		source = Integer.parseInt(ta[3]);
		dest = Integer.parseInt(ta[4]);
		type = Integer.parseInt(ta[5]);
		X = Double.parseDouble(ta[6]);
		Y = Double.parseDouble(ta[7]);
		direction = Integer.parseInt(ta[8]);
		Stype = ta[9];
	}

	public void handle() {

		if (state == 0) {
			// state 0
			switch(function){
			case "connect":
				switch(ta[1]){ 
				//Ĳ�oTid
				//TODO: �̷�Tid�]�w�ж���������.�W�r�Ƨ�
				case "1":
					break;
				case "2":
					break;
				case "3":
					break;
				case "4":
					break;
				}
				break;
			case "disconnect":
				switch(ta[1]){ 
				//Ĳ�oTid
				//TODO: �̷�Tid��ܽ��_�u
				case "1":
					break;
				case "2":
					break;
				case "3":
					break;
				case "4":
					break;
				}
				break;
			case "choose":
				switch(ta[1]){ 
				//Ĳ�oTid
				//TODO: �̷�Tid�]�w�ж����ֿ�ܨ���
				case "1":
					break;
				case "2":
					break;
				case "3":
					break;
				case "4":
					break;
				}
				break;
			case "lock":
				switch(ta[1]){ 
				//Ĳ�oTid
				//TODO: �̷�Tid�]�w����w����
				case "1":
					break;
				case "2":
					break;
				case "3":
					break;
				case "4":
					break;
				}
				break;
			}
			
		} else if (state == 1) {
			switch(function){
			case "atk2":
				switch(ta[1]){ 
				//Ĳ�oTid
				//TODO: �̷�Tid�]�w�ֵo�ʧ���
				case "1":
					break;
				case "2":
					break;
				case "3":
					break;
				case "4":
					break;
				}
				break;
			case "atked":
				switch(ta[1]){ 
				//Ĳ�oTid
				//TODO: �̷�Tid�]�w�ֳQ����
				case "1":
					break;
				case "2":
					break;
				case "3":
					break;
				case "4":
					break;
				}
				break;
			case "death":
				switch(ta[1]){ 
				//Ĳ�oTid
				//TODO: �̷�Tid�]�w�֦��`
				case "1":
					break;
				case "2":
					break;
				case "3":
					break;
				case "4":
					break;
				}
				break;
			case "moveup":
				switch(ta[1]){ 
				//Ĳ�oTid
				//TODO: �̷�Tid�]�w�֩��W
				case "1":
					break;
				case "2":
					break;
				case "3":
					break;
				case "4":
					break;
				}
				break;
			case "movedown":
				switch(ta[1]){ 
				//Ĳ�oTid
				//TODO: �̷�Tid�]�w�֩��U
				case "1":
					break;
				case "2":
					break;
				case "3":
					break;
				case "4":
					break;
				}
				break;
			case "moveleft":
				switch(ta[1]){ 
				//Ĳ�oTid
				//TODO: �̷�Tid�]�w�֩���
				case "1":
					break;
				case "2":
					break;
				case "3":
					break;
				case "4":
					break;
				}
				break;
			case "moveright":
				switch(ta[1]){ 
				//Ĳ�oTid
				//TODO: �̷�Tid�]�w�֩��k
				case "1":
					break;
				case "2":
					break;
				case "3":
					break;
				case "4":
					break;
				}
				break;
			}
		}
	}
}
