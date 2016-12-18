package application;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class ClientCenter implements Runnable {
	private static Map<Integer, Attack2> bulletlist = new HashMap<Integer, Attack2>();
	private int bulletcounter;
	public int state;
	public int Tid, myTid;
	public String function;
	public int source;
	public int dest;
	public int type;
	public int role, role_data[] = new int[4];
	public double X, Y, position[][] = new double[4][2];
	public int direction;
	public String Stype, myName;
	public Socket sock;
	public BufferedReader reader;
	public PrintStream writer;
	public String ta[], name[] = new String[4];
	public Stage primaryStage;
	public Client client;
	public ClientCenter clientCenter;
	public Stage stage;
	public double c1_speed, c2_speed, c3_speed, c4_speed, c5_speed;
	public double c1_hp, c2_hp, c3_hp, c4_hp, c5_hp;
	public double Start_X = 0, Start_Y = 0; // 會跟著腳色移動即時更新X，Y
	public int direction_atk = 1;

	public ClientCenter(Client client, Socket socket, String ip, String name) {
		try {
			EstablishConnection(ip, 8888);
			this.clientCenter = this;
			this.client = client;
			myName = name;
			bulletcounter = 0;
		} catch (Exception ex) {
			System.out.println("連接失敗 in ClientCenter");
		}
	}

	public void EstablishConnection(String ip, int port) {
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

	// Event Handler

	// region

	// selectRole -> select_role_method -> Write to Server
	// selectedRole -> SetImage
	// lockRole -> lock_role_method -> Write to Server

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
							name[0] = myName;
							selecteRole(1);
						} catch (Exception ex) {

						}
					});
					break;
				case 2:
					Platform.runLater(() -> {
						try {
							client.label_room_name2.setText(myName);
							name[1] = myName;
							selecteRole(1);
						} catch (Exception ex) {

						}
					});
					break;
				case 3:
					Platform.runLater(() -> {
						try {
							client.label_room_name3.setText(myName);
							name[2] = myName;
							selecteRole(1);
						} catch (Exception ex) {

						}
					});
					break;
				case 4:
					Platform.runLater(() -> {
						try {
							client.label_room_name4.setText(myName);
							name[3] = myName;
							selecteRole(1);
						} catch (Exception ex) {

						}
					});
					break;
				}
				break;
			case "connected":
				switch (type) {
				// 觸發Tid
				// TODO:
				case 1:
					Platform.runLater(() -> {
						try {
							client.label_room_name1.setText(Stype);
							name[0] = Stype;
							System.out.println("name[0]: " + name[0]);
						} catch (Exception ex) {

						}
					});
					break;
				case 2:
					Platform.runLater(() -> {
						try {
							client.label_room_name2.setText(Stype);
							name[1] = Stype;
							System.out.println("name[1]: " + name[1]);
						} catch (Exception ex) {

						}
					});
					break;
				case 3:
					Platform.runLater(() -> {
						try {
							client.label_room_name3.setText(Stype);
							name[2] = Stype;
							System.out.println("name[2]: " + name[2]);
						} catch (Exception ex) {

						}
					});
					break;
				case 4:
					Platform.runLater(() -> {
						try {

							client.label_room_name4.setText(Stype);
							name[3] = Stype;
							System.out.println("name[3]: " + name[3]);
						} catch (Exception ex) {

						}
					});
					break;
				}
				break;
			case "disconnected":
				switch (type) {
				// 觸發Tid
				// TODO: 依照Tid顯示誰斷線
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
						Platform.runLater(() -> {
							selectedRole("role_1.png", client.label_room_headpicture1);
						});
						break;
					case 2:
						Platform.runLater(() -> {
							selectedRole("role_2.png", client.label_room_headpicture1);
						});
						break;
					case 3:
						Platform.runLater(() -> {
							selectedRole("role_3.png", client.label_room_headpicture1);
						});
						break;
					case 4:
						Platform.runLater(() -> {
							selectedRole("role_4.png", client.label_room_headpicture1);
						});
						break;
					case 5:
						Platform.runLater(() -> {
							selectedRole("role_5.png", client.label_room_headpicture1);
						});
						break;
					}
					break;
				case 2:
					switch (type) {
					case 1:
						Platform.runLater(() -> {
							selectedRole("role_1.png", client.label_room_headpicture2);
						});
						break;
					case 2:
						Platform.runLater(() -> {
							selectedRole("role_2.png", client.label_room_headpicture2);
						});
						break;
					case 3:
						Platform.runLater(() -> {
							selectedRole("role_3.png", client.label_room_headpicture2);
						});
						break;
					case 4:
						Platform.runLater(() -> {
							selectedRole("role_4.png", client.label_room_headpicture2);
						});
						break;
					case 5:
						Platform.runLater(() -> {
							selectedRole("role_5.png", client.label_room_headpicture2);
						});
						break;
					}
					break;
				case 3:
					switch (type) {
					case 1:
						Platform.runLater(() -> {
							selectedRole("role_1.png", client.label_room_headpicture3);
						});
						break;
					case 2:
						Platform.runLater(() -> {
							selectedRole("role_2.png", client.label_room_headpicture3);
						});
						break;
					case 3:
						Platform.runLater(() -> {
							selectedRole("role_3.png", client.label_room_headpicture3);
						});
						break;
					case 4:
						Platform.runLater(() -> {
							selectedRole("role_4.png", client.label_room_headpicture3);
						});
						break;
					case 5:
						Platform.runLater(() -> {
							selectedRole("role_5.png", client.label_room_headpicture3);
						});
						break;
					}
					break;
				case 4:
					switch (type) {
					case 1:
						Platform.runLater(() -> {
							selectedRole("role_1.png", client.label_room_headpicture4);
						});
						break;
					case 2:
						Platform.runLater(() -> {
							selectedRole("role_2.png", client.label_room_headpicture4);
						});
						break;
					case 3:
						Platform.runLater(() -> {
							selectedRole("role_3.png", client.label_room_headpicture4);
						});
						break;
					case 4:
						Platform.runLater(() -> {
							selectedRole("role_4.png", client.label_room_headpicture4);
						});
						break;
					case 5:
						Platform.runLater(() -> {
							selectedRole("role_5.png", client.label_room_headpicture4);
						});
						break;
					}
					break;
				}
				break;
			case "locked":
				role_data[dest - 1] = type;
				switch (dest) {
				case 1:
					switch (type) {
					// 觸發Tid
					// TODO: 依照Tid設定誰鎖定角色
					case 1:
						lockedRole("lock_role_1.png", client.label_room_headpicture1);
						break;
					case 2:
						lockedRole("lock_role_2.png", client.label_room_headpicture1);
						break;
					case 3:
						lockedRole("lock_role_3.png", client.label_room_headpicture1);
						break;
					case 4:
						lockedRole("lock_role_4.png", client.label_room_headpicture1);
						break;
					case 5:
						lockedRole("lock_role_5.png", client.label_room_headpicture1);
						break;
					}
					break;
				case 2:
					switch (type) {
					// 觸發Tid
					// TODO: 依照Tid設定誰鎖定角色
					case 1:
						lockedRole("lock_role_1.png", client.label_room_headpicture2);
						break;
					case 2:
						lockedRole("lock_role_2.png", client.label_room_headpicture2);
						break;
					case 3:
						lockedRole("lock_role_3.png", client.label_room_headpicture2);
						break;
					case 4:
						lockedRole("lock_role_4.png", client.label_room_headpicture2);
						break;
					case 5:
						lockedRole("lock_role_5.png", client.label_room_headpicture2);
						break;
					}
					break;
				case 3:
					switch (type) {
					// 觸發Tid
					// TODO: 依照Tid設定誰鎖定角色
					case 1:
						lockedRole("lock_role_1.png", client.label_room_headpicture3);
						break;
					case 2:
						lockedRole("lock_role_2.png", client.label_room_headpicture3);
						break;
					case 3:
						lockedRole("lock_role_3.png", client.label_room_headpicture3);
						break;
					case 4:
						lockedRole("lock_role_4.png", client.label_room_headpicture3);
						break;
					case 5:
						lockedRole("lock_role_5.png", client.label_room_headpicture3);
						break;
					}
					break;
				case 4:
					switch (type) {
					// 觸發Tid
					// TODO: 依照Tid設定誰鎖定角色
					case 1:
						lockedRole("lock_role_1.png", client.label_room_headpicture4);
						break;
					case 2:
						lockedRole("lock_role_2.png", client.label_room_headpicture4);
						break;
					case 3:
						lockedRole("lock_role_3.png", client.label_room_headpicture4);
						break;
					case 4:
						lockedRole("lock_role_4.png", client.label_room_headpicture4);
						break;
					case 5:
						lockedRole("lock_role_5.png", client.label_room_headpicture4);
						break;
					}
					break;
				}

			case "go1":
				// 鎖定畫面

				break;

			case "go2":
				client.toGame(stage);
				break;

			case "initial":
				switch (dest) {
				// 觸發Tid
				// TODO: 依照Tid設定誰鎖定角色
				case 1:
					position[0][0] = X;
					position[0][1] = Y;
					break;
				case 2:
					position[1][0] = X;
					position[1][1] = Y;
					break;
				case 3:
					position[2][0] = X;
					position[2][1] = Y;
					break;
				case 4:
					position[3][0] = X;
					position[3][1] = Y;
					break;
				}
				break;
			}

		} else if (state == 1) {
			switch (function) {
			case "atk2":

				bulletlist.put(type, new Attack2(client, this.clientCenter, X, Y, client.return_game_backgroundground(),
						direction, type, bulletlist));
			case "atked":
				switch (type) {
				// 觸發Tid
				// TODO: 依照Tid設定誰被攻擊
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
				// 觸發Tid
				// TODO: 依照Tid設定誰死亡
				case 1:
					client.label_game_character1.setDisable(true);
					client.label_game_name1.setDisable(true);
					client.progressbar_game_characterblood1.setDisable(true);
					break;
				case 2:
					client.label_game_character2.setDisable(true);
					client.label_game_name2.setDisable(true);
					client.progressbar_game_characterblood2.setDisable(true);
					break;
				case 3:
					client.label_game_character1.setDisable(true);
					client.label_game_name1.setDisable(true);
					client.progressbar_game_characterblood1.setDisable(true);
					break;
				case 4:
					client.label_game_character1.setDisable(true);
					client.label_game_name1.setDisable(true);
					client.progressbar_game_characterblood1.setDisable(true);
					break;
				}
				break;
			case "movedup":
				switch (dest) {
				// 觸發Tid
				// TODO: 依照Tid設定誰往上
				case 1:

					client.setLocation(client.label_game_character1, client.label_game_name1,
							client.progressbar_game_characterblood1, X, Y);
					break;
				case 2:

					client.setLocation(client.label_game_character2, client.label_game_name2,
							client.progressbar_game_characterblood2, X, Y);
					break;
				case 3:
					client.setLocation(client.label_game_character3, client.label_game_name3,
							client.progressbar_game_characterblood3, X, Y);
					break;
				case 4:

					client.setLocation(client.label_game_character4, client.label_game_name4,
							client.progressbar_game_characterblood4, X, Y);
					break;
				}
				break;
			case "moveddown":
				switch (dest) {
				// 觸發Tid
				// TODO: 依照Tid設定誰往下
				case 1:
					client.setLocation(client.label_game_character1, client.label_game_name1,
							client.progressbar_game_characterblood1, X, Y);
					break;
				case 2:
					client.setLocation(client.label_game_character2, client.label_game_name2,
							client.progressbar_game_characterblood2, X, Y);
					break;
				case 3:
					client.setLocation(client.label_game_character3, client.label_game_name3,
							client.progressbar_game_characterblood3, X, Y);
					break;
				case 4:
					client.setLocation(client.label_game_character4, client.label_game_name4,
							client.progressbar_game_characterblood4, X, Y);
					break;
				}
				break;
			case "movedleft":
				switch (dest) {
				// 觸發Tid
				// TODO: 依照Tid設定誰往左
				case 1:
					client.setLocation(client.label_game_character1, client.label_game_name1,
							client.progressbar_game_characterblood1, X, Y);
					break;
				case 2:
					client.setLocation(client.label_game_character2, client.label_game_name2,
							client.progressbar_game_characterblood2, X, Y);
					break;
				case 3:
					client.setLocation(client.label_game_character3, client.label_game_name3,
							client.progressbar_game_characterblood3, X, Y);
					break;
				case 4:
					client.setLocation(client.label_game_character4, client.label_game_name4,
							client.progressbar_game_characterblood4, X, Y);
					break;
				}
				break;
			case "movedright":
				switch (dest) {
				// 觸發Tid
				// TODO: 依照Tid設定誰往右
				case 1:
					client.setLocation(client.label_game_character1, client.label_game_name1,
							client.progressbar_game_characterblood1, X, Y);
					break;
				case 2:
					client.setLocation(client.label_game_character2, client.label_game_name2,
							client.progressbar_game_characterblood2, X, Y);
					break;
				case 3:
					client.setLocation(client.label_game_character3, client.label_game_name3,
							client.progressbar_game_characterblood3, X, Y);
					break;
				case 4:
					client.setLocation(client.label_game_character4, client.label_game_name4,
							client.progressbar_game_characterblood4, X, Y);
					break;
				}
				break;
			}
		} else if (state == 2) {
			switch (function) {
			case "win":
				client.keyevent_game = null;
				client.scene_game.setOnKeyPressed(client.keyevent_game);
				break;
			case "back1":
				break;
			case "back2":
				refreshInst();
				client.toRoom(stage);
				break;
			}
		}
	}

	public void selecteRole(int role) {
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
				System.out.println("送出資料失敗");
			}
		});
	}

	public void selectedRole(String png, Label label) {

		ImageView image = new ImageView(png);

		label.setGraphic(image);

	}

	public void lockRole(Stage primaryStage) {
		stage = primaryStage;
		Platform.runLater(() -> {
			try {
				role_data[myTid - 1] = role;
				switch (myTid) {
				case 1:
					switch (role) {
					case 1:
						lock_role_method(client.image_room_player_1, "lock_role_1.png", client.label_room_headpicture1);
						break;
					case 2:
						lock_role_method(client.image_room_player_1, "lock_role_2.png", client.label_room_headpicture1);
						break;
					case 3:
						lock_role_method(client.image_room_player_1, "lock_role_3.png", client.label_room_headpicture1);
						break;
					case 4:
						lock_role_method(client.image_room_player_1, "lock_role_4.png", client.label_room_headpicture1);
						break;
					case 5:
						lock_role_method(client.image_room_player_1, "lock_role_5.png", client.label_room_headpicture1);
						break;
					}
					break;
				case 2:
					switch (role) {
					case 1:
						lock_role_method(client.image_room_player_2, "lock_role_1.png", client.label_room_headpicture2);
						break;
					case 2:
						lock_role_method(client.image_room_player_2, "lock_role_2.png", client.label_room_headpicture2);
						break;
					case 3:
						lock_role_method(client.image_room_player_2, "lock_role_3.png", client.label_room_headpicture2);
						break;
					case 4:
						lock_role_method(client.image_room_player_2, "lock_role_4.png", client.label_room_headpicture2);
						break;
					case 5:
						lock_role_method(client.image_room_player_2, "lock_role_5.png", client.label_room_headpicture2);
						break;
					}
					break;
				case 3:
					switch (role) {
					case 1:
						lock_role_method(client.image_room_player_3, "lock_role_1.png", client.label_room_headpicture3);
						break;
					case 2:
						lock_role_method(client.image_room_player_3, "lock_role_2.png", client.label_room_headpicture3);
						break;
					case 3:
						lock_role_method(client.image_room_player_3, "lock_role_3.png", client.label_room_headpicture3);
						break;
					case 4:
						lock_role_method(client.image_room_player_3, "lock_role_4.png", client.label_room_headpicture3);
						break;
					case 5:
						lock_role_method(client.image_room_player_3, "lock_role_5.png", client.label_room_headpicture3);
						break;
					}
					break;
				case 4:
					switch (role) {
					case 1:
						lock_role_method(client.image_room_player_4, "lock_role_1.png", client.label_room_headpicture4);
						break;
					case 2:
						lock_role_method(client.image_room_player_4, "lock_role_2.png", client.label_room_headpicture4);
						break;
					case 3:
						lock_role_method(client.image_room_player_4, "lock_role_3.png", client.label_room_headpicture4);
						break;
					case 4:
						lock_role_method(client.image_room_player_4, "lock_role_4.png", client.label_room_headpicture4);
						break;
					case 5:
						lock_role_method(client.image_room_player_4, "lock_role_5.png", client.label_room_headpicture4);
						break;
					}
					break;
				}
				setSpeed(role);
				setHP(role);
			} catch (Exception e) {

			}
		});
	}

	public void lockedRole(String png, Label label) {

		ImageView image = new ImageView(png);

		Platform.runLater(() -> {
			label.setGraphic(image);
		});
	}

	// endregion

	// method

	// region
	public void setSpeed(int role) {
		switch (myTid) {
		case 1:
			switch (role) {
			case 1:
				c1_speed = 2;
				break;
			case 2:
				c1_speed = 4;
				break;
			case 3:
				c1_speed = 6;
				break;
			case 4:
				c1_speed = 8;
				break;
			case 5:
				c1_speed = 10;
				break;
			}
			break;
		case 2:
			switch (role) {
			case 1:
				c2_speed = 2;
				break;
			case 2:
				c2_speed = 4;
				break;
			case 3:
				c2_speed = 6;
				break;
			case 4:
				c2_speed = 8;
				break;
			case 5:
				c2_speed = 10;
				break;
			}
			break;
		case 3:
			switch (role) {
			case 1:
				c3_speed = 2;
				break;
			case 2:
				c3_speed = 4;
				break;
			case 3:
				c3_speed = 6;
				break;
			case 4:
				c3_speed = 8;
				break;
			case 5:
				c3_speed = 10;
				break;
			}
			break;
		case 4:
			switch (role) {
			case 1:
				c4_speed = 2;
				break;
			case 2:
				c4_speed = 4;
				break;
			case 3:
				c4_speed = 6;
				break;
			case 4:
				c4_speed = 8;
				break;
			case 5:
				c4_speed = 10;
				break;
			}
			break;
		}
	}

	public void setHP(int role) {
		switch (myTid) {
		case 1:
			switch (role) {
			case 1:
				c1_hp = 10;
				break;
			case 2:
				c1_hp = 8;
				break;
			case 3:
				c1_hp = 6;
				break;
			case 4:
				c1_hp = 4;
				break;
			case 5:
				c1_hp = 2;
				break;
			}
			break;
		case 2:
			switch (role) {
			case 1:
				c2_hp = 10;
				break;
			case 2:
				c2_hp = 8;
				break;
			case 3:
				c2_hp = 6;
				break;
			case 4:
				c2_hp = 4;
				break;
			case 5:
				c2_hp = 2;
				break;
			}
			break;
		case 3:
			switch (role) {
			case 1:
				c3_hp = 10;
				break;
			case 2:
				c3_hp = 8;
				break;
			case 3:
				c3_hp = 6;
				break;
			case 4:
				c3_hp = 4;
				break;
			case 5:
				c3_hp = 2;
				break;
			}
			break;
		case 4:
			switch (role) {
			case 1:
				c4_hp = 10;
				break;
			case 2:
				c4_hp = 8;
				break;
			case 3:
				c4_hp = 6;
				break;
			case 4:
				c4_hp = 4;
				break;
			case 5:
				c4_hp = 2;
				break;
			}
			break;
		}
	}

	public void select_role_method(ImageView image, String png, Label label, int role) {
		image = new ImageView(png);
		label.setGraphic(image);
		initSelect(role);
		writer.println(encoder());
		writer.flush();
		refreshInst();
	}

	public void lock_role_method(ImageView image, String png, Label label) {
		image = new ImageView(png);
		label.setGraphic(image);
		initLock();
		writer.println(encoder());
		writer.flush();
		refreshInst();

	}

	public void setGameView() {
		String c1_png = "", c2_png = "", c3_png = "", c4_png = "", my_png = "";
		switch (role) {
		case 1:
			my_png = "role_1.png";
			break;
		case 2:
			my_png = "role_2.png";
			break;
		case 3:
			my_png = "role_3.png";
			break;
		case 4:
			my_png = "role_4.png";
			break;
		case 5:
			my_png = "role_5.png";
			break;
		}
		switch (role_data[0]) {
		case 1:
			c1_png = "img_game_role1.png";
			break;
		case 2:
			c1_png = "img_game_role2.png";
			break;
		case 3:
			c1_png = "img_game_role3.png";
			break;
		case 4:
			c1_png = "img_game_role4.png";
			break;
		case 5:
			c1_png = "img_game_role5.png";
			break;
		default:
			c1_png = "img_game_role1.png";
			break;
		}
		switch (role_data[1]) {
		case 1:
			c2_png = "img_game_role1.png";
			break;
		case 2:
			c2_png = "img_game_role2.png";
			break;
		case 3:
			c2_png = "img_game_role3.png";
			break;
		case 4:
			c2_png = "img_game_role4.png";
			break;
		case 5:
			c2_png = "img_game_role5.png";
			break;
		default:
			c2_png = "img_game_role1.png";
			break;
		}
		switch (role_data[2]) {
		case 1:
			c3_png = "img_game_role1.png";
			break;
		case 2:
			c3_png = "img_game_role2.png";
			break;
		case 3:
			c3_png = "img_game_role3.png";
			break;
		case 4:
			c3_png = "img_game_role4.png";
			break;
		case 5:
			c3_png = "img_game_role5.png";
			break;
		default:
			c3_png = "img_game_role1.png";
			break;
		}
		switch (role_data[3]) {
		case 1:
			c4_png = "img_game_role1.png";
			break;
		case 2:
			c4_png = "img_game_role2.png";
			break;
		case 3:
			c4_png = "img_game_role3.png";
			break;
		case 4:
			c4_png = "img_game_role4.png";
			break;
		case 5:
			c4_png = "img_game_role5.png";
			break;
		default:
			c4_png = "img_game_role1.png";
			break;
		}
		client.game_setupUI(position[0][0], position[0][1], position[1][0], position[1][1], position[2][0],
				position[2][1], position[3][0], position[3][1], c1_png, c2_png, c3_png, c4_png, my_png, name[0],
				name[1], name[2], name[3]);
	}

	public void attack2_method() {
		int Offset_bullet = myTid * 10000;
		bulletcounter++;
		initAttack2(Offset_bullet + bulletcounter);
		writer.println(encoder());
		writer.flush();
		Attack2 attack2 = new Attack2(client, this.clientCenter, Start_X, Start_Y,
				client.return_game_backgroundground(), direction_atk, Offset_bullet + bulletcounter, bulletlist);
		bulletlist.put(Offset_bullet + bulletcounter, attack2);
		System.out.println(Start_X + "," + Start_Y);
		attack2 = null;
		System.gc();
		if (bulletcounter == 9999) {
			bulletcounter = 0;
		}
	}

	public void win_method() {

	}

	public void death_method() {

	}

	public void moveup() {
		switch (myTid) {
		case 1:
			if (client.c1_y - c1_speed > 76) {
				client.c1_y -= c1_speed;
				Start_Y = client.c1_y;
			} else {
				client.c1_y = 76;
				Start_Y = client.c1_y;
			}
			client.label_game_character1.setTranslateX(client.c1_x);
			client.label_game_character1.setTranslateY(client.c1_y);
			client.progressbar_game_characterblood1.setTranslateX(client.c1_x);
			client.progressbar_game_characterblood1.setTranslateY(client.c1_y - 40);
			client.label_game_name1.setTranslateX(client.c1_x);
			client.label_game_name1.setTranslateY(client.c1_y + 40);
			initMoveUp(client.c1_x, client.c1_y);
			writer.println(encoder());
			writer.flush();
			break;
		case 2:
			if (client.c2_y - c2_speed > 76) {
				client.c2_y -= c2_speed;
				Start_Y = client.c2_y;
			} else {
				client.c2_y = 76;
				Start_Y = client.c2_y;
			}
			client.label_game_character2.setTranslateX(client.c2_x);
			client.label_game_character2.setTranslateY(client.c2_y);
			client.progressbar_game_characterblood2.setTranslateX(client.c2_x);
			client.progressbar_game_characterblood2.setTranslateY(client.c2_y - 40);
			client.label_game_name2.setTranslateX(client.c2_x);
			client.label_game_name2.setTranslateY(client.c2_y + 40);
			initMoveUp(client.c2_x, client.c2_y);
			writer.println(encoder());
			writer.flush();

			break;
		case 3:
			if (client.c3_y - c3_speed > 76) {
				client.c3_y -= c3_speed;
				Start_Y = client.c3_y;
			} else {
				client.c3_y = 76;
				Start_Y = client.c3_y;
			}
			client.label_game_character3.setTranslateX(client.c3_x);
			client.label_game_character3.setTranslateY(client.c3_y);
			client.progressbar_game_characterblood3.setTranslateX(client.c3_x);
			client.progressbar_game_characterblood3.setTranslateY(client.c3_y - 40);
			client.label_game_name3.setTranslateX(client.c3_x);
			client.label_game_name3.setTranslateY(client.c3_y + 40);
			initMoveUp(client.c3_x, client.c3_y);
			writer.println(encoder());
			writer.flush();

			break;
		case 4:
			if (client.c4_y - c4_speed > 76) {
				client.c4_y -= c4_speed;
				Start_Y = client.c4_y;
			} else {
				client.c4_y = 76;
				Start_Y = client.c4_y;
			}
			client.label_game_character4.setTranslateX(client.c4_x);
			client.label_game_character4.setTranslateY(client.c4_y);
			client.progressbar_game_characterblood4.setTranslateX(client.c4_x);
			client.progressbar_game_characterblood4.setTranslateY(client.c4_y - 40);
			client.label_game_name4.setTranslateX(client.c4_x);
			client.label_game_name4.setTranslateY(client.c4_y + 40);
			initMoveUp(client.c4_x, client.c4_y);
			writer.println(encoder());
			writer.flush();
			break;
		}

	}

	public void movedown() {
		switch (myTid) {
		case 1:
			if (client.c1_y + c1_speed < 362) {
				client.c1_y += c1_speed;
				Start_Y = client.c1_y;
			} else {
				client.c1_y = 362;
				Start_Y = client.c1_y;
			}
			client.label_game_character1.setTranslateX(client.c1_x);
			client.label_game_character1.setTranslateY(client.c1_y);
			client.progressbar_game_characterblood1.setTranslateX(client.c1_x);
			client.progressbar_game_characterblood1.setTranslateY(client.c1_y - 40);
			client.label_game_name1.setTranslateX(client.c1_x);
			client.label_game_name1.setTranslateY(client.c1_y + 40);
			initMoveDown(client.c1_x, client.c1_y);
			writer.println(encoder());
			writer.flush();
			break;
		case 2:
			if (client.c2_y + c2_speed < 362) {
				client.c2_y += c2_speed;
				Start_Y = client.c2_y;
			} else {
				client.c2_y = 362;
				Start_Y = client.c2_y;
			}
			client.label_game_character2.setTranslateX(client.c2_x);
			client.label_game_character2.setTranslateY(client.c2_y);
			client.progressbar_game_characterblood2.setTranslateX(client.c2_x);
			client.progressbar_game_characterblood2.setTranslateY(client.c2_y - 40);
			client.label_game_name2.setTranslateX(client.c2_x);
			client.label_game_name2.setTranslateY(client.c2_y + 40);
			initMoveDown(client.c2_x, client.c2_y);
			writer.println(encoder());
			writer.flush();
			break;
		case 3:
			if (client.c3_y + c3_speed < 362) {
				client.c3_y += c3_speed;
				Start_Y = client.c3_y;
			} else {
				client.c3_y = 362;
				Start_Y = client.c3_y;
			}
			client.label_game_character3.setTranslateX(client.c3_x);
			client.label_game_character3.setTranslateY(client.c3_y);
			client.progressbar_game_characterblood3.setTranslateX(client.c3_x);
			client.progressbar_game_characterblood3.setTranslateY(client.c3_y - 40);
			client.label_game_name3.setTranslateX(client.c3_x);
			client.label_game_name3.setTranslateY(client.c3_y + 40);
			initMoveDown(client.c3_x, client.c3_y);
			writer.println(encoder());
			writer.flush();
			break;
		case 4:
			if (client.c4_y + c4_speed < 362) {
				client.c4_y += c4_speed;
				Start_Y = client.c4_y;
			} else {
				client.c4_y = 362;
				Start_Y = client.c4_y;
			}
			client.label_game_character4.setTranslateX(client.c4_x);
			client.label_game_character4.setTranslateY(client.c4_y);
			client.progressbar_game_characterblood4.setTranslateX(client.c4_x);
			client.progressbar_game_characterblood4.setTranslateY(client.c4_y - 40);
			client.label_game_name4.setTranslateX(client.c4_x);
			client.label_game_name4.setTranslateY(client.c4_y + 40);
			initMoveDown(client.c4_x, client.c4_y);
			writer.println(encoder());
			writer.flush();
			break;
		}
	}

	public void moveleft() {
		switch (myTid) {
		case 1:
			if (client.c1_x - c1_speed > -492) {
				client.c1_x -= c1_speed;
				Start_X = client.c1_x;
			} else {
				client.c1_x = -492;
				Start_X = client.c1_x;
			}
			client.label_game_character1.setTranslateX(client.c1_x);
			client.label_game_character1.setTranslateY(client.c1_y);
			client.progressbar_game_characterblood1.setTranslateX(client.c1_x);
			client.progressbar_game_characterblood1.setTranslateY(client.c1_y - 40);
			client.label_game_name1.setTranslateX(client.c1_x);
			client.label_game_name1.setTranslateY(client.c1_y + 40);
			initMoveLeft(client.c1_x, client.c1_y);
			writer.println(encoder());
			writer.flush();
			break;
		case 2:
			if (client.c2_x - c2_speed > -492) {
				client.c2_x -= c2_speed;
				Start_X = client.c2_x;
			} else {
				client.c2_x = -492;
				Start_X = client.c2_x;
			}
			client.label_game_character2.setTranslateX(client.c2_x);
			client.label_game_character2.setTranslateY(client.c2_y);
			client.progressbar_game_characterblood2.setTranslateX(client.c2_x);
			client.progressbar_game_characterblood2.setTranslateY(client.c2_y - 40);
			client.label_game_name2.setTranslateX(client.c2_x);
			client.label_game_name2.setTranslateY(client.c2_y + 40);
			initMoveLeft(client.c2_x, client.c2_y);
			writer.println(encoder());
			writer.flush();
			break;
		case 3:
			if (client.c3_x - c3_speed > -492) {
				client.c3_x -= c3_speed;
				Start_X = client.c3_x;
			} else {
				client.c3_x = -492;
				Start_X = client.c3_x;
			}
			client.label_game_character3.setTranslateX(client.c3_x);
			client.label_game_character3.setTranslateY(client.c3_y);
			client.progressbar_game_characterblood3.setTranslateX(client.c3_x);
			client.progressbar_game_characterblood3.setTranslateY(client.c3_y - 40);
			client.label_game_name3.setTranslateX(client.c3_x);
			client.label_game_name3.setTranslateY(client.c3_y + 40);
			initMoveLeft(client.c3_x, client.c3_y);
			writer.println(encoder());
			writer.flush();
			break;
		case 4:
			if (client.c4_x - c4_speed > -492) {
				client.c4_x -= c4_speed;
				Start_X = client.c4_x;
			} else {
				client.c4_x = -492;
				Start_X = client.c4_x;
			}
			client.label_game_character4.setTranslateX(client.c4_x);
			client.label_game_character4.setTranslateY(client.c4_y);
			client.progressbar_game_characterblood4.setTranslateX(client.c4_x);
			client.progressbar_game_characterblood4.setTranslateY(client.c4_y - 40);
			client.label_game_name4.setTranslateX(client.c4_x);
			client.label_game_name4.setTranslateY(client.c4_y + 40);
			initMoveLeft(client.c4_x, client.c4_y);
			writer.println(encoder());
			writer.flush();
			break;
		}
		direction_atk = -1;
	}

	public void moveright() {
		switch (myTid) {
		case 1:
			if (client.c1_x + c1_speed < 492) {
				client.c1_x += c1_speed;
				Start_X = client.c1_x;
			} else {
				client.c1_x = 492;
				Start_X = client.c1_x;
			}
			client.label_game_character1.setTranslateX(client.c1_x);
			client.label_game_character1.setTranslateY(client.c1_y);
			client.progressbar_game_characterblood1.setTranslateX(client.c1_x);
			client.progressbar_game_characterblood1.setTranslateY(client.c1_y - 40);
			client.label_game_name1.setTranslateX(client.c1_x);
			client.label_game_name1.setTranslateY(client.c1_y + 40);
			initMoveRight(client.c1_x, client.c1_y);
			writer.println(encoder());
			writer.flush();
			break;
		case 2:
			if (client.c2_x + c2_speed < 492) {
				client.c2_x += c2_speed;
				Start_X = client.c2_x;
			} else {
				client.c2_x = 492;
				Start_X = client.c2_x;
			}
			client.label_game_character2.setTranslateX(client.c2_x);
			client.label_game_character2.setTranslateY(client.c2_y);
			client.progressbar_game_characterblood2.setTranslateX(client.c2_x);
			client.progressbar_game_characterblood2.setTranslateY(client.c2_y - 40);
			client.label_game_name2.setTranslateX(client.c2_x);
			client.label_game_name2.setTranslateY(client.c2_y + 40);
			initMoveRight(client.c2_x, client.c2_y);
			writer.println(encoder());
			writer.flush();
			break;
		case 3:
			if (client.c3_x + c3_speed < 492) {
				client.c3_x += c3_speed;
				Start_X = client.c3_x;
			} else {
				client.c3_x = 492;
				Start_X = client.c3_x;
			}
			client.label_game_character3.setTranslateX(client.c3_x);
			client.label_game_character3.setTranslateY(client.c3_y);
			client.progressbar_game_characterblood3.setTranslateX(client.c3_x);
			client.progressbar_game_characterblood3.setTranslateY(client.c3_y - 40);
			client.label_game_name3.setTranslateX(client.c3_x);
			client.label_game_name3.setTranslateY(client.c3_y + 40);
			initMoveRight(client.c3_x, client.c3_y);
			writer.println(encoder());
			writer.flush();
			break;
		case 4:
			if (client.c4_x + c4_speed < 492) {
				client.c4_x += c4_speed;
				Start_X = client.c4_x;
			} else {
				client.c4_x = 492;
				Start_X = client.c4_x;
			}
			client.label_game_character4.setTranslateX(client.c4_x);
			client.label_game_character4.setTranslateY(client.c4_y);
			client.progressbar_game_characterblood4.setTranslateX(client.c4_x);
			client.progressbar_game_characterblood4.setTranslateY(client.c4_y - 40);
			client.label_game_name4.setTranslateX(client.c4_x);
			client.label_game_name4.setTranslateY(client.c4_y + 40);
			initMoveRight(client.c4_x, client.c4_y);
			writer.println(encoder());
			writer.flush();
			break;
		}
		direction_atk = 1;
	}

	// endregion

	// coder

	// region
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
	// endregion

	// init

	// region
	public void initSelect(int role) {
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

	public void initMoveUp(double x, double y) {
		state = 1;
		myTid = myTid;
		function = "moveup";
		source = -1;
		dest = -1;
		type = -1;
		X = x;
		Y = y;
		direction = 0;
		Stype = "@";
	}

	public void initMoveDown(double x, double y) {
		state = 1;
		myTid = myTid;
		function = "movedown";
		source = -1;
		dest = -1;
		type = -1;
		X = x;
		Y = y;
		direction = 0;
		Stype = "@";
	}

	public void initMoveLeft(double x, double y) {
		state = 1;
		myTid = myTid;
		function = "moveleft";
		source = -1;
		dest = -1;
		type = -1;
		X = x;
		Y = y;
		direction = 0;
		Stype = "@";
	}

	public void initMoveRight(double x, double y) {
		state = 1;
		myTid = myTid;
		function = "moveright";
		source = -1;
		dest = -1;
		type = -1;
		X = x;
		Y = y;
		direction = 0;
		Stype = "@";
	}
	// endregion

	// refresh

	// region
	public void initAttack2(int bulletNumber) {
		state = 1;
		myTid = myTid;
		function = "atk2";
		source = -1;
		dest = -1;
		type = bulletNumber;
		X = Start_X;
		Y = Start_Y;
		direction = direction_atk;
		Stype = "@";
	}

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
	// endregion
}