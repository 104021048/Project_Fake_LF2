package application;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import javafx.stage.Stage;

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
	private Stage primaryStage;
	/*
	state#Tid#function#source#dest#type#X#Y#direction#SType
	0      1    2         3     4    5  6 7  8          9
	private String data[][] = new String[4][10];
	*/
	public ClientCenter(Stage stage,Socket socket ,String ip,String name) {
		try {
			EstablishConnection(ip,8888);
			primaryStage = stage;
			
		} catch (Exception ex) {
			System.out.println("�s������ in ClientCenter");
		}
	}
	private void EstablishConnection(String ip,int port) {
		try {
			// �ШD�إ߳s�u
			sock = new Socket(ip, port);
			// �إ�I/O��Ƭy
			InputStreamReader streamReader =
			// ���oSocket����J��Ƭy
					new InputStreamReader(sock.getInputStream());
			// ��J�Ȧs��
			reader = new BufferedReader(streamReader);
			// ���oSocket����X��Ƭy

			writer = new PrintStream(sock.getOutputStream());
			// �s�u���\
			System.out.println("�����إ�-�s�u���\");

		} catch (IOException ex) {
			System.out.println("�إ߳s�u����");
		}
	}
/*
	public class IncomingReader implements Runnable {
		public void run() {
			String message;
			try {
				while ((message = reader.readLine()) != null) {
					System.out.print(message + '\n');
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}
*/
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
		System.out.println("state: "+state);
		System.out.println("Tid: "+Tid);
		System.out.println("function: "+function);
		System.out.println("source: "+source);
		System.out.println("dest: "+dest);
		System.out.println("type: "+type);
		System.out.println("X: "+X);
		System.out.println("Y: "+Y);
		System.out.println("direction: "+direction);
		System.out.println("Stype: "+Stype);
	}

	public void handle() {

		if (state == 0) {
			// state 0
			switch(function){
			case "connect":
				switch(ta[5]){ 
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
				switch(ta[5]){ 
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
				switch(ta[5]){ 
				//Ĳ�oTid
				//TODO: �̷�Tid�]�w�ж����ֿ�ܨ���
				case "1":
					SelectedRole_1();
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
				switch(ta[5]){ 
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
				switch(ta[5]){ 
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
				switch(ta[5]){ 
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
				switch(ta[5]){ 
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
				switch(ta[5]){ 
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
				switch(ta[5]){ 
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
				switch(ta[5]){ 
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
				switch(ta[5]){ 
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
	
	public void SelectedRole_1(){
		try{
			function = "choose";
			writer.println((state+"#"+Tid+"#"+function+"#"+source+"#"+dest+"#"+type+"#"+X+"#"+Y+"#"+direction+"#"+Stype));
			writer.flush();
		}catch(Exception ex){
			System.out.println("�e�X��ƥ���");
		}
	}
	public void SelectedRole_2(){
		try{
			writer.println(("0#1#choose#-1#-1#2#-1.0#-1.0#0#@"));
			writer.flush();
		}catch(Exception ex){
			System.out.println("�e�X��ƥ���");
		}
	}
	public void SelectedRole_3(){
		try{
			writer.println(("0#1#choose#-1#-1#3#-1.0#-1.0#0#@"));
			writer.flush();
		}catch(Exception ex){
			System.out.println("�e�X��ƥ���");
		}
	}public void SelectedRole_4(){
		try{
			writer.println(("0#1#choose#-1#-1#4#-1.0#-1.0#0#@"));
			writer.flush();
		}catch(Exception ex){
			System.out.println("�e�X��ƥ���");
		}
	}
	public void SelectedRole_5(){
		try{
			writer.println(("0#1#choose#-1#-1#5#-1.0#-1.0#0#@"));
			writer.flush();
		}catch(Exception ex){
			System.out.println("�e�X��ƥ���");
		}
	}
}
