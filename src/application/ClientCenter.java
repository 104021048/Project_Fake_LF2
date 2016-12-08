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
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
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
		} catch (Exception ex) {
			System.out.println("�s������ in ClientCenter");
		}
	}

	private void EstablishConnection(String ip, int port) {
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
					handle();
					if (Integer.parseInt(ta[1]) == -1) {
						writer.println(("0#" + myTid + "#connect#-1#-1#-1#-1.0#-1.0#0#" + myName));
						writer.flush();
					}
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
		if (function.equals("connect"))
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
				// Ĳ�oTid
				// TODO: �̷�Tid�]�w�ж���������.�W�r�Ƨ�
				case 1:
					Platform.runLater(() -> {
						try {
							client.label_room_name1.setText(myName);
						} catch (Exception ex) {

						}
					});
					break;
				case 2:
					Platform.runLater(() -> {
						try {
							client.label_room_name2.setText(myName);
						} catch (Exception ex) {

						}
					});
					break;
				case 3:
					Platform.runLater(() -> {
						try {
							client.label_room_name3.setText(myName);
						} catch (Exception ex) {

						}
					});
					break;
				case 4:
					Platform.runLater(() -> {
						try {
							client.label_room_name4.setText(myName);
						} catch (Exception ex) {

						}
					});
					break;
				default:
					System.out.println(myTid);
				}
				break;
			case "connected":
				switch (ta[5]) {
				// Ĳ�oTid
				// TODO:
				case "1":
					Platform.runLater(() -> {
						try {
							System.out.println("ted");
							client.label_room_name1.setText(ta[9]);
						} catch (Exception ex) {

						}
					});
					break;
				case "2":
					Platform.runLater(() -> {
						try {
							client.label_room_name2.setText(ta[9]);
						} catch (Exception ex) {

						}
					});
					break;
				case "3":
					Platform.runLater(() -> {
						try {
							client.label_room_name3.setText(ta[9]);
						} catch (Exception ex) {

						}
					});
					break;
				case "4":
					Platform.runLater(() -> {
						try {
							client.label_room_name4.setText(ta[9]);
						} catch (Exception ex) {

						}
					});
					break;
				}
				break;
			case "disconnected":
				switch (ta[5]) {
				// Ĳ�oTid
				// TODO: �̷�Tid��ܽ��_�u
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
				SelectedRole(Integer.parseInt(ta[4]));
				break;
			case "locked":
				switch (ta[5]) {
				// Ĳ�oTid
				// TODO: �̷�Tid�]�w����w����
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
				// Ĳ�oTid
				// TODO: �̷�Tid�]�w����w����
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
				// Ĳ�oTid
				// TODO: �̷�Tid�]�w����w����
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
				// Ĳ�oTid
				// TODO: �̷�Tid�]�w����w����
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
				// Ĳ�oTid
				// TODO: �̷�Tid�]�w�ֵo�ʧ���
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
				// Ĳ�oTid
				// TODO: �̷�Tid�]�w�ֳQ����
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
				// Ĳ�oTid
				// TODO: �̷�Tid�]�w�֦��`
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
				// Ĳ�oTid
				// TODO: �̷�Tid�]�w�֩��W
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
				// Ĳ�oTid
				// TODO: �̷�Tid�]�w�֩��U
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
				// Ĳ�oTid
				// TODO: �̷�Tid�]�w�֩���
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
				// Ĳ�oTid
				// TODO: �̷�Tid�]�w�֩��k
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
				// Ĳ�oTid
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
				// Ĳ�oTid
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
				// Ĳ�oTid
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

	public void selecte_role_method(ImageView image, String png, Label label) {
		image = new ImageView(png);
		label.setGraphic(image);
	}

	public void SelecteRole(int role) {

		Platform.runLater(() -> {
			try {
				switch (myTid) {
				case 1:
					switch (role) {
					case 1:
						selecte_role_method(client.image_room_player_1,"role_1.png",client.label_room_headpicture1);
						break;
					case 2:
						selecte_role_method(client.image_room_player_1,"role_2.png",client.label_room_headpicture1);
						break;
					case 3:
						selecte_role_method(client.image_room_player_1,"role_3.png",client.label_room_headpicture1);
						break;
					case 4:
						selecte_role_method(client.image_room_player_1,"role_4.png",client.label_room_headpicture1);
						break;
					case 5:
						selecte_role_method(client.image_room_player_1,"role_5.png",client.label_room_headpicture1);
						break;
					}
					break;
				case 2:
					switch (role) {
					case 1:
						selecte_role_method(client.image_room_player_2,"role_1.png",client.label_room_headpicture2);
						break;
					case 2:
						selecte_role_method(client.image_room_player_2,"role_2.png",client.label_room_headpicture2);
						break;
					case 3:
						selecte_role_method(client.image_room_player_2,"role_3.png",client.label_room_headpicture2);
						break;
					case 4:
						selecte_role_method(client.image_room_player_2,"role_4.png",client.label_room_headpicture2);
						break;
					case 5:
						selecte_role_method(client.image_room_player_2,"role_5.png",client.label_room_headpicture2);
						break;
					}
					break;
				case 3:
					switch (role) {
					case 1:
						selecte_role_method(client.image_room_player_3,"role_1.png",client.label_room_headpicture3);
						break;
					case 2:
						selecte_role_method(client.image_room_player_3,"role_2.png",client.label_room_headpicture3);
						break;
					case 3:
						selecte_role_method(client.image_room_player_3,"role_3.png",client.label_room_headpicture3);
						break;
					case 4:
						selecte_role_method(client.image_room_player_3,"role_4.png",client.label_room_headpicture3);
						break;
					case 5:
						selecte_role_method(client.image_room_player_3,"role_5.png",client.label_room_headpicture3);
						break;
					}
					break;
				case 4:
					switch (role) {
					case 1:
						selecte_role_method(client.image_room_player_4,"role_1.png",client.label_room_headpicture4);
						break;
					case 2:
						selecte_role_method(client.image_room_player_4,"role_2.png",client.label_room_headpicture4);
						break;
					case 3:
						selecte_role_method(client.image_room_player_4,"role_3.png",client.label_room_headpicture4);
						break;
					case 4:
						selecte_role_method(client.image_room_player_4,"role_4.png",client.label_room_headpicture4);
						break;
					case 5:
						selecte_role_method(client.image_room_player_4,"role_5.png",client.label_room_headpicture4);
						break;
					}
					writer.println(("0#" + myTid + "#choose#-1#-1#"+role+"#-1.0#-1.0#0#" + myName));
					writer.flush();
					break;
				}
			} catch (Exception ex) {
				System.out.println("�e�X��ƥ���");
			}
		});
	}

	public void SelectedRole(int role) {
		String str = "";
		switch (role) {
		case 1:
			str = "role_1.png";
			break;
		case 2:
			str = "role_2.png";
			break;
		case 3:
			str = "role_3.png";
			break;
		case 4:
			str = "role_4.png";
			break;
		case 5:
			str = "role_5.png";
			break;
		}
		switch (ta[5]) {
		// Ĳ�oTid
		// TODO: �̷�Tid�]�w�ж����ֿ�ܨ���
		case "1":
			client.image_room_player_1 = new ImageView(str);
			break;
		case "2":
			client.image_room_player_2 = new ImageView(str);
			break;
		case "3":
			client.image_room_player_3 = new ImageView(str);
			break;
		case "4":
			client.image_room_player_4 = new ImageView(str);
			break;

		}
	}
}
