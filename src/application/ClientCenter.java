package application;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import javafx.application.Platform;
import javafx.stage.Stage;

public class ClientCenter implements Runnable {
	private int state;
	private int Tid, myTid;
	private String function;
	private int source;
	private int dest;
	private int type;
	private double X;
	private double Y;
	private int direction;
	private String Stype, myName;
	private Socket sock;
	private BufferedReader reader;
	private PrintStream writer;
	private String ta[];
	private Stage primaryStage;
	private Client client;

	/*
	 * state#Tid#function#source#dest#type#X#Y#direction#SType 0 1 2 3 4 5 6 7 8
	 * 9 private String data[][] = new String[4][10];
	 */
	public ClientCenter(Client client, Socket socket, String ip, String name) {
		try {
			EstablishConnection(ip, 8888);
			this.client = client;
			myName = name;
			writer.println(("0#1#connect#-1#-1#-1#-1.0#-1.0#0#"+myName));
			writer.flush();
		} catch (Exception ex) {
			System.out.println("連接失敗 in ClientCenter");
		}
	}

	private void EstablishConnection(String ip, int port) {
		try {
			// 請求建立連線
			sock = new Socket(ip, port);
			// 建立I/O資料流
			InputStreamReader streamReader =
					// 取得Socket的輸入資料流
					new InputStreamReader(sock.getInputStream());
			// 放入暫存區
			reader = new BufferedReader(streamReader);
			// 取得Socket的輸出資料流

			writer = new PrintStream(sock.getOutputStream());
			// 連線成功
			System.out.println("網路建立-連線成功");

		} catch (IOException ex) {
			System.out.println("建立連線失敗");
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
					handle();
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
		myTid = Integer.parseInt(ta[5]);
		System.out.println("state: " + state);
		System.out.println("Tid: " + Tid);
		System.out.println("function: " + function);
		System.out.println("source: " + source);
		System.out.println("dest: " + dest);
		System.out.println("type: " + type);
		System.out.println("X: " + X);
		System.out.println("Y: " + Y);
		System.out.println("direction: " + direction);
		System.out.println("Stype: " + Stype);
		System.out.println("myTid: " + myTid);
	}

	public void handle() {

		if (state == 0) {
			// state 0
			switch (function) {
			case "connect":
				switch (myTid) {
				// 觸發Tid
				// TODO: 依照Tid設定房間內的角色.名字排序
				case 1:
					  Platform.runLater(() -> {
					        try {
								client.label_room_name1.setText(myName);
					        } catch (Exception  ex) {
					            
					        }
					    });
					break;
				case 2:
					  Platform.runLater(() -> {
					        try {
								client.label_room_name2.setText(myName);
					        } catch (Exception  ex) {
					            
					        }
					    });
					break;
				case 3:
					  Platform.runLater(() -> {
					        try {
								client.label_room_name3.setText(myName);
					        } catch (Exception  ex) {
					            
					        }
					    });
					break;
				case 4:
					  Platform.runLater(() -> {
					        try {
								client.label_room_name4.setText(myName);
					        } catch (Exception  ex) {
					            
					        }
					    });
					break;
				default:
						System.out.println(myTid);
				}
				break;
			case "connected":
				switch (ta[5]) {
				// 觸發Tid
				// TODO:
				case "1":
					  Platform.runLater(() -> {
					        try {
					        	System.out.println("ted");
								client.label_room_name1.setText(ta[9]);
					        } catch (Exception  ex) {
					            
					        }
					    });
					break;
				case "2":
					  Platform.runLater(() -> {
					        try {
								client.label_room_name2.setText(ta[9]);
					        } catch (Exception  ex) {
					            
					        }
					    });
					break;
				case "3":
					  Platform.runLater(() -> {
					        try {
								client.label_room_name3.setText(ta[9]);
					        } catch (Exception  ex) {
					            
					        }
					    });
					break;
				case "4":
					  Platform.runLater(() -> {
					        try {
								client.label_room_name4.setText(ta[9]);
					        } catch (Exception  ex) {
					            
					        }
					    });
					break;
				}
				break;
			case "disconnected":
				switch (ta[5]) {
				// 觸發Tid
				// TODO: 依照Tid顯示誰斷線
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
			case "choosed":
				switch (ta[5]) {
				// 觸發Tid
				// TODO: 依照Tid設定房間內誰選擇角色
				case "1":
					// SelectedRole();
					// Label.set
					break;
				case "2":
					break;
				case "3":
					break;
				case "4":
					break;
				}
				break;
			case "locked":
				switch (ta[5]) {
				// 觸發Tid
				// TODO: 依照Tid設定誰鎖定角色
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
			case "go1":
				switch (ta[5]) {
				// 觸發Tid
				// TODO: 依照Tid設定誰鎖定角色
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

			case "go2":
				switch (ta[5]) {
				// 觸發Tid
				// TODO: 依照Tid設定誰鎖定角色
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

			case "initial":
				switch (ta[5]) {
				// 觸發Tid
				// TODO: 依照Tid設定誰鎖定角色
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
			switch (function) {
			case "atk2":
				switch (ta[5]) {
				// 觸發Tid
				// TODO: 依照Tid設定誰發動攻擊
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
				switch (ta[5]) {
				// 觸發Tid
				// TODO: 依照Tid設定誰被攻擊
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
				switch (ta[5]) {
				// 觸發Tid
				// TODO: 依照Tid設定誰死亡
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
			case "movedup":
				switch (ta[5]) {
				// 觸發Tid
				// TODO: 依照Tid設定誰往上
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
			case "moveddown":
				switch (ta[5]) {
				// 觸發Tid
				// TODO: 依照Tid設定誰往下
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
			case "movedleft":
				switch (ta[5]) {
				// 觸發Tid
				// TODO: 依照Tid設定誰往左
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
			case "movedright":
				switch (ta[5]) {
				// 觸發Tid
				// TODO: 依照Tid設定誰往右
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
		} else if (state == 2) {
			switch (function) {
			case "win":
				switch (ta[5]) {
				// 觸發Tid
				// TODO:
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
			case "back1":
				switch (ta[5]) {
				// 觸發Tid
				// TODO:
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
			case "back2":
				switch (ta[5]) {
				// 觸發Tid
				// TODO:
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

	public void SelectedRole(int role) {
		try {
			function = "choose";
			writer.println((state + "#" + myTid + "#" + function + "#" + source + "#" + dest + "#" + role + "#" + X
					+ "#" + Y + "#" + direction + "#" + Stype));
			writer.flush();
		} catch (Exception ex) {
			System.out.println("送出資料失敗");
		}
	}
}
