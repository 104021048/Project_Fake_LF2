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
			// 取得Socket的輸入資料流
			InputStreamReader isReader = new InputStreamReader(sock.getInputStream());
			reader = new BufferedReader(isReader);
			writer = new PrintStream(sock.getOutputStream());

		} catch (Exception ex) {
			System.out.println("連接失敗 in ClientCenter");
		}
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		String message;
		try {
			while ((message = reader.readLine()) != null) {
				System.out.println("收到" + message);
				if (message.contains("#")) {
					// 這是因為暫時測試用的的client會把名字:打在前面
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
		// 把message用#分開
		ta = message.split("#");
		// System.out.print("解開訊息:");
		// for (int i = 0; i < ta.length; i++) {
		// System.out.print(ta[i] + "+");
		// }
		// System.out.println();
		// 把所有拆開的訊息依序填入thread中的參數
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
				//觸發Tid
				//TODO: 依照Tid設定房間內的角色.名字排序
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
				//觸發Tid
				//TODO: 依照Tid顯示誰斷線
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
				//觸發Tid
				//TODO: 依照Tid設定房間內誰選擇角色
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
				//觸發Tid
				//TODO: 依照Tid設定誰鎖定角色
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
				//觸發Tid
				//TODO: 依照Tid設定誰發動攻擊
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
				//觸發Tid
				//TODO: 依照Tid設定誰被攻擊
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
				//觸發Tid
				//TODO: 依照Tid設定誰死亡
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
				//觸發Tid
				//TODO: 依照Tid設定誰往上
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
				//觸發Tid
				//TODO: 依照Tid設定誰往下
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
				//觸發Tid
				//TODO: 依照Tid設定誰往左
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
				//觸發Tid
				//TODO: 依照Tid設定誰往右
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
