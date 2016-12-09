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
	private int role;
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
					if (Tid == -1) {
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

	//Event Handler
	
	//region
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
				switch (type) {
				// Ĳ�oTid
				// TODO:
				case 1:
					Platform.runLater(() -> {
						try {
							System.out.println("Switch - connected");
							client.label_room_name1.setText(ta[9]);
						} catch (Exception ex) {

						}
					});
					break;
				case 2:
					Platform.runLater(() -> {
						try {
							client.label_room_name2.setText(ta[9]);
						} catch (Exception ex) {

						}
					});
					break;
				case 3:
					Platform.runLater(() -> {
						try {
							client.label_room_name3.setText(ta[9]);
						} catch (Exception ex) {

						}
					});
					break;
				case 4:
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
				switch (type) {
				// Ĳ�oTid
				// TODO: �̷�Tid��ܽ��_�u
				case 1:
					break;
				case 2:
					break;
				case 3:
					break;
				case 4:
					break;
				}
				break;
			case "choosed":
				switch (dest) {
				case 1:
					switch (type) {
					case 1:
						SelectedRole(client.image_room_player_1, "role_1.png", client.label_room_headpicture1);
						break;
					case 2:
						SelectedRole(client.image_room_player_1, "role_2.png", client.label_room_headpicture1);
						break;
					case 3:
						SelectedRole(client.image_room_player_1, "role_3.png", client.label_room_headpicture1);
						break;
					case 4:
						SelectedRole(client.image_room_player_1, "role_4.png", client.label_room_headpicture1);
						break;
					case 5:
						SelectedRole(client.image_room_player_1, "role_5.png", client.label_room_headpicture1);
						break;
					}
					break;
				case 2:
					switch (type) {
					case 1:
						SelectedRole(client.image_room_player_2, "role_1.png", client.label_room_headpicture2);
						break;
					case 2:
						SelectedRole(client.image_room_player_2, "role_2.png", client.label_room_headpicture2);
						break;
					case 3:
						SelectedRole(client.image_room_player_2, "role_3.png", client.label_room_headpicture2);
						break;
					case 4:
						SelectedRole(client.image_room_player_2, "role_4.png", client.label_room_headpicture2);
						break;
					case 5:
						SelectedRole(client.image_room_player_2, "role_5.png", client.label_room_headpicture2);
						break;
					}
					break;
				case 3:
					switch (type) {
					case 1:
						SelectedRole(client.image_room_player_3, "role_1.png", client.label_room_headpicture3);
						break;
					case 2:
						SelectedRole(client.image_room_player_3, "role_2.png", client.label_room_headpicture3);
						break;
					case 3:
						SelectedRole(client.image_room_player_3, "role_3.png", client.label_room_headpicture3);
						break;
					case 4:
						SelectedRole(client.image_room_player_3, "role_4.png", client.label_room_headpicture3);
						break;
					case 5:
						SelectedRole(client.image_room_player_3, "role_5.png", client.label_room_headpicture3);
						break;
					}
					break;
				case 4:
					switch (type) {
					case 1:
						SelectedRole(client.image_room_player_4, "role_1.png", client.label_room_headpicture4);
						break;
					case 2:
						SelectedRole(client.image_room_player_4, "role_2.png", client.label_room_headpicture4);
						break;
					case 3:
						SelectedRole(client.image_room_player_4, "role_3.png", client.label_room_headpicture4);
						break;
					case 4:
						SelectedRole(client.image_room_player_4, "role_4.png", client.label_room_headpicture4);
						break;
					case 5:
						SelectedRole(client.image_room_player_4, "role_5.png", client.label_room_headpicture4);
						break;
					}
					break;
				}
				break;
			case "locked":
				switch (type) {
				// Ĳ�oTid
				// TODO: �̷�Tid�]�w����w����
				case 1:
					break;
				case 2:
					break;
				case 3:
					break;
				case 4:
					break;
				}
				break;
			case "go1":
				// ��w�e��
				SelecteRole(myTid);
				break;

			case "go2":
				switch (type) {
				// Ĳ�oTid
				// TODO: �̷�Tid�]�w����w����
				case 1:
					break;
				case 2:
					break;
				case 3:
					break;
				case 4:
					break;
				}
				break;

			case "initial":
				switch (type) {
				// Ĳ�oTid
				// TODO: �̷�Tid�]�w����w����
				case 1:
					break;
				case 2:
					break;
				case 3:
					break;
				case 4:
					break;
				}
				break;
			}

		} else if (state == 1) {
			switch (function) {
			case "atk2":
				switch (type) {
				// Ĳ�oTid
				// TODO: �̷�Tid�]�w�ֵo�ʧ���
				case 1:
					break;
				case 2:
					break;
				case 3:
					break;
				case 4:
					break;
				}
				break;
			case "atked":
				switch (type) {
				// Ĳ�oTid
				// TODO: �̷�Tid�]�w�ֳQ����
				case 1:
					break;
				case 2:
					break;
				case 3:
					break;
				case 4:
					break;
				}
				break;
			case "death":
				switch (type) {
				// Ĳ�oTid
				// TODO: �̷�Tid�]�w�֦��`
				case 1:
					break;
				case 2:
					break;
				case 3:
					break;
				case 4:
					break;
				}
				break;
			case "movedup":
				switch (type) {
				// Ĳ�oTid
				// TODO: �̷�Tid�]�w�֩��W
				case 1:
					break;
				case 2:
					break;
				case 3:
					break;
				case 4:
					break;
				}
				break;
			case "moveddown":
				switch (type) {
				// Ĳ�oTid
				// TODO: �̷�Tid�]�w�֩��U
				case 1:
					break;
				case 2:
					break;
				case 3:
					break;
				case 4:
					break;
				}
				break;
			case "movedleft":
				switch (type) {
				// Ĳ�oTid
				// TODO: �̷�Tid�]�w�֩���
				case 1:
					break;
				case 2:
					break;
				case 3:
					break;
				case 4:
					break;
				}
				break;
			case "movedright":
				switch (type) {
				// Ĳ�oTid
				// TODO: �̷�Tid�]�w�֩��k
				case 1:
					break;
				case 2:
					break;
				case 3:
					break;
				case 4:
					break;
				}
				break;
			}
		} else if (state == 2) {
			switch (function) {
			case "win":
				switch (type) {
				// Ĳ�oTid
				// TODO:
				case 1:
					break;
				case 2:
					break;
				case 3:
					break;
				case 4:
					break;
				}
				break;
			case "back1":
				switch (type) {
				// Ĳ�oTid
				// TODO:
				case 1:
					break;
				case 2:
					break;
				case 3:
					break;
				case 4:
					break;
				}
				break;
			case "back2":
				switch (type) {
				// Ĳ�oTid
				// TODO:
				case 1:
					break;
				case 2:
					break;
				case 3:
					break;
				case 4:
					break;
				}
				break;
			}
		}
	}

	public void lockRole() {
		Platform.runLater(() -> {
			try {
				switch (myTid) {
				case 1:
					switch (role) {
					case 1:
						lock_role_method(client.image_room_player_1, "lock_role_1.png", client.label_room_headpicture1,
								role);
						break;
					}
					break;
				case 2:
				case 3:
				case 4:
				}
			} catch (Exception e) {

			}
		});
	}

	public void SelecteRole(int role) {
		Platform.runLater(() -> {
			try {
				switch (myTid) {
				case 1:
					switch (role) {
					case 1:
						select_role_method(client.image_room_player_1, "role_1.png", client.label_room_headpicture1,
								role);
						this.role = role;
						break;
					case 2:
						select_role_method(client.image_room_player_1, "role_2.png", client.label_room_headpicture1,
								role);
						this.role = role;
						break;
					case 3:
						select_role_method(client.image_room_player_1, "role_3.png", client.label_room_headpicture1,
								role);
						this.role = role;
						break;
					case 4:
						select_role_method(client.image_room_player_1, "role_4.png", client.label_room_headpicture1,
								role);
						this.role = role;
						break;
					case 5:
						select_role_method(client.image_room_player_1, "role_5.png", client.label_room_headpicture1,
								role);
						this.role = role;
						break;
					}
					break;
				case 2:
					switch (role) {
					case 1:
						select_role_method(client.image_room_player_2, "role_1.png", client.label_room_headpicture2,
								role);
						this.role = role;
						break;
					case 2:
						select_role_method(client.image_room_player_2, "role_2.png", client.label_room_headpicture2,
								role);
						this.role = role;
						break;
					case 3:
						select_role_method(client.image_room_player_2, "role_3.png", client.label_room_headpicture2,
								role);
						this.role = role;
						break;
					case 4:
						select_role_method(client.image_room_player_2, "role_4.png", client.label_room_headpicture2,
								role);
						this.role = role;
						break;
					case 5:
						select_role_method(client.image_room_player_2, "role_5.png", client.label_room_headpicture2,
								role);
						this.role = role;
						break;
					}
					break;
				case 3:
					switch (role) {
					case 1:
						select_role_method(client.image_room_player_3, "role_1.png", client.label_room_headpicture3,
								role);
						this.role = role;
						break;
					case 2:
						select_role_method(client.image_room_player_3, "role_2.png", client.label_room_headpicture3,
								role);
						this.role = role;
						break;
					case 3:
						select_role_method(client.image_room_player_3, "role_3.png", client.label_room_headpicture3,
								role);
						this.role = role;
						break;
					case 4:
						select_role_method(client.image_room_player_3, "role_4.png", client.label_room_headpicture3,
								role);
						this.role = role;
						break;
					case 5:
						select_role_method(client.image_room_player_3, "role_5.png", client.label_room_headpicture3,
								role);
						this.role = role;
						break;
					}
					break;
				case 4:
					switch (role) {
					case 1:
						select_role_method(client.image_room_player_4, "role_1.png", client.label_room_headpicture4,
								role);
						this.role = role;
						break;
					case 2:
						select_role_method(client.image_room_player_4, "role_2.png", client.label_room_headpicture4,
								role);
						this.role = role;
						break;
					case 3:
						select_role_method(client.image_room_player_4, "role_3.png", client.label_room_headpicture4,
								role);
						this.role = role;
						break;
					case 4:
						select_role_method(client.image_room_player_4, "role_4.png", client.label_room_headpicture4,
								role);
						this.role = role;
						break;
					case 5:
						select_role_method(client.image_room_player_4, "role_5.png", client.label_room_headpicture4,
								role);
						this.role = role;
						break;
					}
					break;
				}

			} catch (Exception ex) {
				System.out.println("�e�X��ƥ���");
			}
		});
	}

	public void SelectedRole(ImageView image2, String png, Label label) {

		ImageView image = new ImageView(png);

		Platform.runLater(() -> {
			label.setGraphic(image);
		});
		System.out.println("SelectedRole_finish");
	}
	//endregion
	
	//method
	
	//region
	public void select_role_method(ImageView image, String png, Label label, int role) {
		image = new ImageView(png);
		label.setGraphic(image);
		initSelect();
		writer.println(encoder());
		writer.flush();
		refreshInst();
		System.out.println("SelecteRole_writer_send");
	}

	public void lock_role_method(ImageView image, String png, Label label, int role) {
		image = new ImageView(png);
		label.setGraphic(image);
		initLock();
		writer.println(encoder());
		writer.flush();
		refreshInst();
		System.out.println("Lock_finish");
		
	}
	//endregion
	
	//coder
	
	//region
	public String encoder() {
		String ta[] = new String[10];
		ta[0] = Integer.toString(state);
		ta[1] = Integer.toString(myTid);
		ta[2] = function;
		ta[3] = Integer.toString(source);
		ta[4] = Integer.toString(dest);
		ta[5] = Integer.toString(type);
		ta[6] = Double.toString(X);
		ta[7] = Double.toString(Y);
		ta[8] = Integer.toString(direction);
		ta[9] = Stype;
		StringBuilder strBuilder = new StringBuilder();
		for (int i = 0; i < ta.length; i++) {
			strBuilder.append(ta[i]);
			if (i != ta.length - 1) {
				strBuilder.append("#");
			}
		}
		String message = strBuilder.toString();
		return message;
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
	//endregion
	
	//init
	
	//region
	public void initSelect() {
		state = 0;
		myTid = myTid;
		function = "choose";
		source = -1;
		dest = -1;
		type = role;
		X = -1.0;
		Y = -1.0;
		direction = 0;
		Stype = "@";
	}

	public void initLock() {
		state = 0;
		myTid = myTid;
		function = "lock";
		source = -1;
		dest = -1;
		type = role;
		X = -1.0;
		Y = -1.0;
		direction = 0;
		Stype = "@";
	}
	//endregion
	
	//refresh
	
	//region
	public void refreshInst() {

		state = -1;
		Tid = -1;
		function = "";
		source = -1;
		dest = -1;
		type = -1;
		X = -1;
		Y = -1;
		direction = 0;
		Stype = "@";
	}
	//endregion
}
