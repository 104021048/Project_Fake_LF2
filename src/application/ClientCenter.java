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
	private static Map<Integer, Attack> attacklist = new HashMap<Integer, Attack>();
	private static Map<Integer, Attack2> attack2list = new HashMap<Integer, Attack2>();
	private static Map<Integer, Attack3> attack3list = new HashMap<Integer, Attack3>();
	private static Map<Integer, Attack4> attack4list = new HashMap<Integer, Attack4>();
	private static Map<Integer, Attack5> bulletlist = new HashMap<Integer, Attack5>();
	private int bulletcounter;
	private int Offset_bullet;
	public int state;
	public int Tid, myTid;
	public String function;
	public int source;
	public int dest;
	public int type;
	public int role, my_role, role_data[] = new int[4];
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
	public double my_speed;
	public int my_hp;
	public double Start_X = 0, Start_Y = 0; // 會跟著腳色移動即時更新X，Y
	public int direction_atk = 1;
	private Role_Capoo_1 my_Capoo_1 = null;
	private Role_Capoo_2 my_Capoo_2 = null;
	private Role_Capoo_3 my_Capoo_3 = null;
	private Role_Capoo_4 my_Capoo_4 = null;
	private Role_Capoo_5 my_Capoo_5 = null;
	private String c1_png = "", c2_png = "", c3_png = "", c4_png = "", my_png = "";
	private int tmp = 0;

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
						Offset_bullet = myTid * 10000;
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
	public void handle() {
		if (state == 0) {
			switch_function_to_case_state_0();
		} else if (state == 1) {
			switch_function_to_case_state_1();
		} else if (state == 2) {
			switch_function_to_case_state_2();
		}
	}

	public void selecteRole(int role) {
		Platform.runLater(() -> {
			try {
				this.role = role;
				my_role = role;
				switch_myTid_to_switch_role_to_select();
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
				switch_myTid_to_switch_role_to_lock();
			} catch (Exception e) {
				e.printStackTrace();
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

	public void select_role_method(ImageView image, String png, Label label, int role) {
		image = new ImageView(png);
		label.setGraphic(image);
	}

	public void setGameView() {

		switch_myRole_to_get_GameMyImage();
		switch_roledata_to_set_other_game_image();
		try {
			client.game_setupUI(position[0][0], position[0][1], position[1][0], position[1][1], position[2][0],
					position[2][1], position[3][0], position[3][1], c1_png, c2_png, c3_png, c4_png, my_png, name[0],
					name[1], name[2], name[3]);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public void bulletdeath(int bulletID, double bulletx, double bullety) {
		initbulletdeath(bulletID, bulletx, bullety);
		writer.println(encoder());
		writer.flush();
	}

	public void attack_method() {
		bulletcounter++;
		initAttack(Offset_bullet + bulletcounter);
		writer.println(encoder());
		writer.flush();
		switch (my_role) {
		case 1:
			Attack attack = new Attack(client, this.clientCenter, Start_X, Start_Y,
					client.return_game_backgroundground(), direction_atk, Offset_bullet + bulletcounter, attacklist);
			attacklist.put(Offset_bullet + bulletcounter, attack);
			attack = null;
			break;
		case 2:
			Attack2 attack2 = new Attack2(client, this.clientCenter, Start_X, Start_Y,
					client.return_game_backgroundground(), direction_atk, Offset_bullet + bulletcounter, attack2list);
			attack2list.put(Offset_bullet + bulletcounter, attack2);
			attack = null;
			break;
		case 3:
			Attack3 attack3 = new Attack3(client, this.clientCenter, Start_X, Start_Y,
					client.return_game_backgroundground(), direction_atk, Offset_bullet + bulletcounter, attack3list);
			attack3list.put(Offset_bullet + bulletcounter, attack3);
			attack = null;
			break;
		case 4:
			Attack4 attack4 = new Attack4(client, this.clientCenter, Start_X, Start_Y,
					client.return_game_backgroundground(), direction_atk, Offset_bullet + bulletcounter, attack4list);
			attack4list.put(Offset_bullet + bulletcounter, attack4);
			attack4 = null;
			break;
		case 5:
			Attack attack5 = new Attack(client, this.clientCenter, Start_X, Start_Y,
					client.return_game_backgroundground(), direction_atk, Offset_bullet + bulletcounter, attacklist);
			attacklist.put(Offset_bullet + bulletcounter, attack5);
			attack = null;
			break;
		}

		System.gc();
		if (bulletcounter == 9999) {
			bulletcounter = 0;
		}

	}

	public void attack2_method() {
		bulletcounter++;
		initAttack2(Offset_bullet + bulletcounter);
		writer.println(encoder());
		writer.flush();
		Attack5 attack2 = new Attack5(client, this.clientCenter, Start_X, Start_Y,
				client.return_game_backgroundground(), direction_atk, Offset_bullet + bulletcounter, bulletlist);
		bulletlist.put(Offset_bullet + bulletcounter, attack2);
		attack2 = null;
		System.gc();
		if (bulletcounter == 9999) {
			bulletcounter = 0;
		}
	}

	public void atked_method(double x, double y) {
		my_hp -= 50;
		switch (myTid) {
		case 1:
			client.game_setMyHP(my_role, type, client.progressbar_game_characterblood1);
			break;
		case 2:
			client.game_setMyHP(my_role, type, client.progressbar_game_characterblood2);
			break;
		case 3:
			client.game_setMyHP(my_role, type, client.progressbar_game_characterblood3);
			break;
		case 4:
			client.game_setMyHP(my_role, type, client.progressbar_game_characterblood4);
			break;
		}
		initAtked();
		writer.println(encoder());
		writer.flush();

	}

	public void atk2ed_method(double x, double y, int bulletID) {
		bulletdeath(bulletID, x, y);
		my_hp -= 50;
		switch (myTid) {
		case 1:
			client.game_setMyHP(my_role, type, client.progressbar_game_characterblood1);
			break;
		case 2:
			client.game_setMyHP(my_role, type, client.progressbar_game_characterblood2);
			break;
		case 3:
			client.game_setMyHP(my_role, type, client.progressbar_game_characterblood3);
			break;
		case 4:
			client.game_setMyHP(my_role, type, client.progressbar_game_characterblood4);
			break;
		}

		initAtk2ed();
		writer.println(encoder());
		writer.flush();

	}

	public void win_method() {

	}

	public void death_method() {
		switch (myTid) {
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
			client.label_game_character3.setDisable(true);
			client.label_game_name3.setDisable(true);
			client.progressbar_game_characterblood3.setDisable(true);
			break;
		case 4:
			client.label_game_character4.setDisable(true);
			client.label_game_name4.setDisable(true);
			client.progressbar_game_characterblood4.setDisable(true);
			break;
		}
		initDeath();
		writer.println(encoder());
		writer.flush();
	}

	public void moveup() {
		switch (myTid) {
		case 1:
			if (client.c1_y - my_speed > 76) {
				client.c1_y -= my_speed;
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
			if (client.c2_y - my_speed > 76) {
				client.c2_y -= my_speed;
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
			if (client.c3_y - my_speed > 76) {
				client.c3_y -= my_speed;
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
			if (client.c4_y - my_speed > 76) {
				client.c4_y -= my_speed;
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
			if (client.c1_y + my_speed < 362) {
				client.c1_y += my_speed;
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
			if (client.c2_y + my_speed < 362) {
				client.c2_y += my_speed;
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
			if (client.c3_y + my_speed < 362) {
				client.c3_y += my_speed;
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
			if (client.c4_y + my_speed < 362) {
				client.c4_y += my_speed;
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
			if (client.c1_x - my_speed > -492) {
				client.c1_x -= my_speed;
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
			if (client.c2_x - my_speed > -492) {
				client.c2_x -= my_speed;
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
			if (client.c3_x - my_speed > -492) {
				client.c3_x -= my_speed;
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
			if (client.c4_x - my_speed > -492) {
				client.c4_x -= my_speed;
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
			if (client.c1_x + my_speed < 492) {
				client.c1_x += my_speed;
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
			if (client.c2_x + my_speed < 492) {
				client.c2_x += my_speed;
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
			if (client.c3_x + my_speed < 492) {
				client.c3_x += my_speed;
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
			if (client.c4_x + my_speed < 492) {
				client.c4_x += my_speed;
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
	// New Role
	// region

	private void new_Role_1(Client client, ClientCenter clientCenter, ImageView img_room,
			Label label_room_headpicture) {
		my_Capoo_1 = new Role_Capoo_1(client, clientCenter, img_room, label_room_headpicture);
		my_hp = my_Capoo_1.getHP();
		client.myHp_max = my_Capoo_1.getHP();
		my_speed = my_Capoo_1.getSpeed();
	}

	private void new_Role_2(Client client, ClientCenter clientCenter, ImageView img_room,
			Label label_room_headpicture) {
		my_Capoo_2 = new Role_Capoo_2(client, clientCenter, img_room, label_room_headpicture);
		my_hp = my_Capoo_2.getHP();
		client.myHp_max = my_Capoo_2.getHP();
		my_speed = my_Capoo_2.getSpeed();
	}

	private void new_Role_3(Client client, ClientCenter clientCenter, ImageView img_room,
			Label label_room_headpicture) {
		my_Capoo_3 = new Role_Capoo_3(client, clientCenter, img_room, label_room_headpicture);
		my_hp = my_Capoo_3.getHP();
		client.myHp_max = my_Capoo_3.getHP();
		my_speed = my_Capoo_3.getSpeed();
	}

	private void new_Role_4(Client client, ClientCenter clientCenter, ImageView img_room,
			Label label_room_headpicture) {
		my_Capoo_4 = new Role_Capoo_4(client, clientCenter, img_room, label_room_headpicture);
		my_hp = my_Capoo_4.getHP();
		client.myHp_max = my_Capoo_4.getHP();
		my_speed = my_Capoo_4.getSpeed();
	}

	private void new_Role_5(Client client, ClientCenter clientCenter, ImageView img_room,
			Label label_room_headpicture) {
		my_Capoo_5 = new Role_Capoo_5(client, clientCenter, img_room, label_room_headpicture);
		my_hp = my_Capoo_5.getHP();
		client.myHp_max = my_Capoo_5.getHP();
		my_speed = my_Capoo_5.getSpeed();
	}

	// endregion
	// Switch Function To Case
	// region

	private void switch_function_to_case_state_0() {
		switch (function) {
		case "connect":
			switch_myTid_to_connect();
			break;
		case "connected":
			switch_type_to_connected();
			break;
		case "disconnected":
			switch_type_to_disconnected();
			break;
		case "choosed":
			switch_dest_to_switch_type_to_choosed();
			break;
		case "locked":
			role_data[dest - 1] = type;
			switch_dest_to_switch_type_to_locked();
			break;
		case "go1":
			break;
		case "initial":
			switch_dest_to_initial();
			break;
		case "go2":
			client.toGame(stage);
			break;
		}
	}

	private void switch_function_to_case_state_1() {
		switch (function) {
		case "bulletdeath":
			bulletlist.get(type).bulletDeath(X, Y);
			bulletlist.remove(type);
			break;
		case "atk":
			switch_dest_to_other_atk();
			break;
		case "atk2":
			bulletlist.put(type, new Attack5(client, this.clientCenter, X, Y, client.return_game_backgroundground(),
					direction, type, bulletlist));
			break;
		case "atked":
			switch_dest_to_other_atked();
			break;
		case "death":
			switch_dest_to_other_death();
			break;
		case "movedup":
			switch_dest_to_movedup();
			break;
		case "moveddown":
			switch_dest_to_moveddown();
			break;
		case "movedleft":
			switch_dest_to_movedleft();
			break;
		case "movedright":
			switch_dest_to_movedright();
			break;
		}
	}

	private void switch_function_to_case_state_2() {
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
	// endregion

	// endregion
	// Switch To Connect
	// region

	private void switch_myTid_to_connect() {
		Platform.runLater(() -> {
			try {
				switch (myTid) {
				case 1:
					client.label_room_name1.setText(myName);
					name[0] = myName;
					break;
				case 2:
					client.label_room_name2.setText(myName);
					name[1] = myName;
					break;
				case 3:
					client.label_room_name3.setText(myName);
					name[2] = myName;
					break;
				case 4:
					client.label_room_name4.setText(myName);
					name[3] = myName;
					break;
				}
				selecteRole(1);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		});
	}

	private void switch_type_to_connected() {
		Platform.runLater(() -> {
			try {
				switch (type) {
				case 1:
					client.label_room_name1.setText(Stype);
					name[0] = Stype;
					break;
				case 2:
					client.label_room_name2.setText(Stype);
					name[1] = Stype;
					break;
				case 3:
					client.label_room_name3.setText(Stype);
					name[2] = Stype;
					break;
				case 4:
					client.label_room_name4.setText(Stype);
					name[3] = Stype;
					break;
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		});
	}

	private void switch_type_to_disconnected() {

	}

	// endregion
	// Switch To Select
	// region

	private void switch_myTid_to_switch_role_to_select() {
		try {
			switch (myTid) {
			case 1:
				switch_role_to_select_1();
				break;
			case 2:
				switch_role_to_select_2();
				break;
			case 3:
				switch_role_to_select_3();
				break;
			case 4:
				switch_role_to_select_4();
				break;
			}
			initSelect(role);
			writer.println(encoder());
			writer.flush();
			refreshInst();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	private void switch_role_to_select_1() {
		switch (role) {
		case 1:
			select_role_method(client.image_room_player_1, "role_1.png", client.label_room_headpicture1, role);
			break;
		case 2:
			select_role_method(client.image_room_player_1, "role_2.png", client.label_room_headpicture1, role);
			break;
		case 3:
			select_role_method(client.image_room_player_1, "role_3.png", client.label_room_headpicture1, role);
			break;
		case 4:
			select_role_method(client.image_room_player_1, "role_4.png", client.label_room_headpicture1, role);
			break;
		case 5:
			select_role_method(client.image_room_player_1, "role_5.png", client.label_room_headpicture1, role);
			break;
		}
	}

	private void switch_role_to_select_2() {
		switch (role) {
		case 1:
			select_role_method(client.image_room_player_2, "role_1.png", client.label_room_headpicture2, role);
			break;
		case 2:
			select_role_method(client.image_room_player_2, "role_2.png", client.label_room_headpicture2, role);
			break;
		case 3:
			select_role_method(client.image_room_player_2, "role_3.png", client.label_room_headpicture2, role);
			break;
		case 4:
			select_role_method(client.image_room_player_2, "role_4.png", client.label_room_headpicture2, role);
			break;
		case 5:
			select_role_method(client.image_room_player_2, "role_5.png", client.label_room_headpicture2, role);
			break;
		}
	}

	private void switch_role_to_select_3() {
		switch (role) {
		case 1:
			select_role_method(client.image_room_player_3, "role_1.png", client.label_room_headpicture3, role);
			break;
		case 2:
			select_role_method(client.image_room_player_3, "role_2.png", client.label_room_headpicture3, role);
			break;
		case 3:
			select_role_method(client.image_room_player_3, "role_3.png", client.label_room_headpicture3, role);
			break;
		case 4:
			select_role_method(client.image_room_player_3, "role_4.png", client.label_room_headpicture3, role);
			break;
		case 5:
			select_role_method(client.image_room_player_3, "role_5.png", client.label_room_headpicture3, role);
			break;
		}
	}

	private void switch_role_to_select_4() {
		switch (role) {
		case 1:
			select_role_method(client.image_room_player_4, "role_1.png", client.label_room_headpicture4, role);
			break;
		case 2:
			select_role_method(client.image_room_player_4, "role_2.png", client.label_room_headpicture4, role);
			break;
		case 3:
			select_role_method(client.image_room_player_4, "role_3.png", client.label_room_headpicture4, role);
			break;
		case 4:
			select_role_method(client.image_room_player_4, "role_4.png", client.label_room_headpicture4, role);
			break;
		case 5:
			select_role_method(client.image_room_player_4, "role_5.png", client.label_room_headpicture4, role);
			break;
		}
	}

	private void switch_dest_to_switch_type_to_choosed() {
		try {
			switch (dest) {
			case 1:
				switch_type_to_choosed_1();
				break;
			case 2:
				switch_type_to_choosed_2();
				break;
			case 3:
				switch_type_to_choosed_3();
				break;
			case 4:
				switch_type_to_choosed_4();
				break;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	private void switch_type_to_choosed_1() {
		Platform.runLater(() -> {
			switch (type) {
			case 1:
				selectedRole("role_1.png", client.label_room_headpicture1);
				client.c1Hp_max = 1000;
				break;
			case 2:
				selectedRole("role_2.png", client.label_room_headpicture1);
				client.c1Hp_max = 900;
				break;
			case 3:
				selectedRole("role_3.png", client.label_room_headpicture1);
				client.c1Hp_max = 800;
				break;
			case 4:
				selectedRole("role_4.png", client.label_room_headpicture1);
				client.c1Hp_max = 700;
				break;
			case 5:
				selectedRole("role_5.png", client.label_room_headpicture1);
				client.c1Hp_max = 600;
				break;
			}
		});
	}

	private void switch_type_to_choosed_2() {
		Platform.runLater(() -> {
			switch (type) {
			case 1:
				selectedRole("role_1.png", client.label_room_headpicture2);
				client.c2Hp_max = 1000;
				break;
			case 2:
				selectedRole("role_2.png", client.label_room_headpicture2);
				client.c2Hp_max = 900;
				break;
			case 3:
				selectedRole("role_3.png", client.label_room_headpicture2);
				client.c2Hp_max = 800;
				break;
			case 4:
				selectedRole("role_4.png", client.label_room_headpicture2);
				client.c2Hp_max = 700;
				break;
			case 5:
				selectedRole("role_5.png", client.label_room_headpicture2);
				client.c2Hp_max = 600;
				break;
			}
		});
	}

	private void switch_type_to_choosed_3() {
		Platform.runLater(() -> {
			switch (type) {
			case 1:
				selectedRole("role_1.png", client.label_room_headpicture2);
				client.c3Hp_max = 1000;
				break;
			case 2:
				selectedRole("role_2.png", client.label_room_headpicture2);
				client.c3Hp_max = 900;
				break;
			case 3:
				selectedRole("role_3.png", client.label_room_headpicture2);
				client.c3Hp_max = 800;
				break;
			case 4:
				selectedRole("role_4.png", client.label_room_headpicture2);
				client.c3Hp_max = 700;
				break;
			case 5:
				selectedRole("role_5.png", client.label_room_headpicture2);
				client.c3Hp_max = 600;
				break;
			}
		});
	}

	private void switch_type_to_choosed_4() {
		Platform.runLater(() -> {
			switch (type) {
			case 1:
				selectedRole("role_1.png", client.label_room_headpicture1);
				client.c4Hp_max = 1000;
				break;
			case 2:
				selectedRole("role_2.png", client.label_room_headpicture1);
				client.c4Hp_max = 900;
				break;
			case 3:
				selectedRole("role_3.png", client.label_room_headpicture1);
				client.c4Hp_max = 800;
				break;
			case 4:
				selectedRole("role_4.png", client.label_room_headpicture1);
				client.c4Hp_max = 700;
				break;
			case 5:
				selectedRole("role_5.png", client.label_room_headpicture1);
				client.c4Hp_max = 600;
				break;
			}
		});
	}

	// endregion
	// Switch To Lock
	// region

	private void switch_myTid_to_switch_role_to_lock() {
		try {
			switch (myTid) {
			case 1:
				switch_role_to_lock_1();
				break;
			case 2:
				switch_role_to_lock_2();
				break;
			case 3:
				switch_role_to_lock_3();
				break;
			case 4:
				switch_role_to_lock_4();
				break;
			}

			initLock();
			writer.println(encoder());
			writer.flush();
			refreshInst();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	private void switch_role_to_lock_1() {
		switch (role) {
		case 1:
			new_Role_1(client, this, client.image_room_player_1, client.label_room_headpicture1);
			break;
		case 2:
			new_Role_2(client, this, client.image_room_player_1, client.label_room_headpicture1);
			break;
		case 3:
			new_Role_3(client, this, client.image_room_player_1, client.label_room_headpicture1);
			break;
		case 4:
			new_Role_4(client, this, client.image_room_player_1, client.label_room_headpicture1);
			break;
		case 5:
			new_Role_5(client, this, client.image_room_player_1, client.label_room_headpicture1);
			break;
		}
	}

	private void switch_role_to_lock_2() {
		switch (role) {
		case 1:
			new_Role_1(client, this, client.image_room_player_2, client.label_room_headpicture2);
			break;
		case 2:
			new_Role_2(client, this, client.image_room_player_2, client.label_room_headpicture2);
			break;
		case 3:
			new_Role_3(client, this, client.image_room_player_2, client.label_room_headpicture2);
			break;
		case 4:
			new_Role_4(client, this, client.image_room_player_2, client.label_room_headpicture2);
			break;
		case 5:
			new_Role_5(client, this, client.image_room_player_2, client.label_room_headpicture2);
			break;
		}
	}

	private void switch_role_to_lock_3() {
		switch (role) {
		case 1:
			new_Role_1(client, this, client.image_room_player_3, client.label_room_headpicture3);
			break;
		case 2:
			new_Role_2(client, this, client.image_room_player_3, client.label_room_headpicture3);
			break;
		case 3:
			new_Role_3(client, this, client.image_room_player_3, client.label_room_headpicture3);
			break;
		case 4:
			new_Role_4(client, this, client.image_room_player_3, client.label_room_headpicture3);
			break;
		case 5:
			new_Role_5(client, this, client.image_room_player_3, client.label_room_headpicture3);
			break;
		}
	}

	private void switch_role_to_lock_4() {
		switch (role) {
		case 1:
			new_Role_1(client, this, client.image_room_player_4, client.label_room_headpicture4);
			break;
		case 2:
			new_Role_2(client, this, client.image_room_player_4, client.label_room_headpicture4);
			break;
		case 3:
			new_Role_3(client, this, client.image_room_player_4, client.label_room_headpicture4);
			break;
		case 4:
			new_Role_4(client, this, client.image_room_player_4, client.label_room_headpicture4);
			break;
		case 5:
			new_Role_5(client, this, client.image_room_player_4, client.label_room_headpicture4);
			break;
		}
	}

	private void switch_dest_to_switch_type_to_locked() {
		try {
			switch (dest) {
			case 1:
				switch_type_to_locked_1();
				break;
			case 2:
				switch_type_to_locked_2();
				break;
			case 3:
				switch_type_to_locked_3();
				break;
			case 4:
				switch_type_to_locked_4();
				break;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	private void switch_type_to_locked_1() {
		switch (type) {
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
	}

	private void switch_type_to_locked_2() {
		switch (type) {
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
	}

	private void switch_type_to_locked_3() {
		switch (type) {
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
	}

	private void switch_type_to_locked_4() {
		switch (type) {
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
	}

	// endregion

	// endregion
	// Switch To initial
	// region

	private void switch_dest_to_initial() {
		try {
			switch (dest) {
			case 1:
				position[0][0] = X;
				position[0][1] = Y;
				if (dest == myTid) {
					Start_X = X;
					Start_Y = Y;
				}
				break;
			case 2:
				position[1][0] = X;
				position[1][1] = Y;
				if (dest == myTid) {
					Start_X = X;
					Start_Y = Y;
				}
				break;
			case 3:
				position[2][0] = X;
				position[2][1] = Y;
				if (dest == myTid) {
					Start_X = X;
					Start_Y = Y;
				}
				break;
			case 4:
				position[3][0] = X;
				position[3][1] = Y;
				if (dest == myTid) {
					Start_X = X;
					Start_Y = Y;
				}
				break;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	// endregion

	// endregion
	// Switch To Move
	// region

	private void switch_dest_to_movedup() {
		try {
			switch (dest) {
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
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	private void switch_dest_to_moveddown() {
		try {
			switch (dest) {
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
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	private void switch_dest_to_movedleft() {
		try {
			switch (dest) {
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
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	private void switch_dest_to_movedright() {
		try {
			switch (dest) {
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
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	// endregion
	// endregion
	// Switch To Atk
	// region

	private void switch_dest_to_other_atk() {
		try {
			switch (dest) {
			case 1:
				switch (role_data[0]) {
				case 1:
					attacklist.put(type, new Attack(client, this.clientCenter, X, Y,
							client.return_game_backgroundground(), direction, type, attacklist));
					break;
				case 2:
					attack2list.put(type, new Attack2(client, this.clientCenter, X, Y,
							client.return_game_backgroundground(), direction, type, attack2list));
					break;
				case 3:
					attack3list.put(type, new Attack3(client, this.clientCenter, X, Y,
							client.return_game_backgroundground(), direction, type, attack3list));
					break;
				case 4:
					attack4list.put(type, new Attack4(client, this.clientCenter, X, Y,
							client.return_game_backgroundground(), direction, type, attack4list));
					break;
				case 5:
					break;
				}
				break;
			case 2:
				switch (role_data[1]) {
				case 1:
					attacklist.put(type, new Attack(client, this.clientCenter, X, Y,
							client.return_game_backgroundground(), direction, type, attacklist));
					break;
				case 2:
					attack2list.put(type, new Attack2(client, this.clientCenter, X, Y,
							client.return_game_backgroundground(), direction, type, attack2list));
					break;
				case 3:
					attack3list.put(type, new Attack3(client, this.clientCenter, X, Y,
							client.return_game_backgroundground(), direction, type, attack3list));
					break;
				case 4:
					attack4list.put(type, new Attack4(client, this.clientCenter, X, Y,
							client.return_game_backgroundground(), direction, type, attack4list));
					break;
				case 5:
					break;
				}
				break;
			case 3:
				switch (role_data[2]) {
				case 1:
					attacklist.put(type, new Attack(client, this.clientCenter, X, Y,
							client.return_game_backgroundground(), direction, type, attacklist));
					break;
				case 2:
					attack2list.put(type, new Attack2(client, this.clientCenter, X, Y,
							client.return_game_backgroundground(), direction, type, attack2list));
					break;
				case 3:
					attack3list.put(type, new Attack3(client, this.clientCenter, X, Y,
							client.return_game_backgroundground(), direction, type, attack3list));
					break;
				case 4:
					attack4list.put(type, new Attack4(client, this.clientCenter, X, Y,
							client.return_game_backgroundground(), direction, type, attack4list));
					break;
				case 5:
					break;
				}
				break;
			case 4:
				switch (role_data[3]) {
				case 1:
					attacklist.put(type, new Attack(client, this.clientCenter, X, Y,
							client.return_game_backgroundground(), direction, type, attacklist));
					break;
				case 2:
					attack2list.put(type, new Attack2(client, this.clientCenter, X, Y,
							client.return_game_backgroundground(), direction, type, attack2list));
					break;
				case 3:
					attack3list.put(type, new Attack3(client, this.clientCenter, X, Y,
							client.return_game_backgroundground(), direction, type, attack3list));
					break;
				case 4:
					attack4list.put(type, new Attack4(client, this.clientCenter, X, Y,
							client.return_game_backgroundground(), direction, type, attack4list));
					break;
				case 5:
					break;
				}
				break;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	private void switch_dest_to_other_atked() {
		try {
			switch (dest) {
			case 1:
				client.game_setOtherHP(dest, type, client.progressbar_game_characterblood1);
				break;
			case 2:
				client.game_setOtherHP(dest, type, client.progressbar_game_characterblood2);
				break;
			case 3:
				client.game_setOtherHP(dest, type, client.progressbar_game_characterblood3);
				break;
			case 4:
				client.game_setOtherHP(dest, type, client.progressbar_game_characterblood4);
				break;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	// endregion

	// endregion
	// Switch To Death
	// region

	private void switch_dest_to_other_death() {
		try {
			switch (dest) {
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
				client.label_game_character3.setDisable(true);
				client.label_game_name3.setDisable(true);
				client.progressbar_game_characterblood3.setDisable(true);
				break;
			case 4:
				client.label_game_character4.setDisable(true);
				client.label_game_name4.setDisable(true);
				client.progressbar_game_characterblood4.setDisable(true);
				break;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	// endregion
	// endregion
	// Switch MyRole To Get Value
	// region

	private void switch_myRole_to_get_GameMyImage() {
		try {
			switch (my_role) {
			case 1:
				my_png = my_Capoo_1.getGameMyImage();
				break;
			case 2:
				my_png = my_Capoo_2.getGameMyImage();
				break;
			case 3:
				my_png = my_Capoo_3.getGameMyImage();
				break;
			case 4:
				my_png = my_Capoo_4.getGameMyImage();
				break;
			case 5:
				my_png = my_Capoo_5.getGameMyImage();
				break;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public int switch_myRole_to_get_HP() {
		try {
			switch (my_role) {
			case 1:
				tmp = my_Capoo_1.getHP();
				break;
			case 2:
				tmp = my_Capoo_2.getHP();
				break;
			case 3:
				tmp = my_Capoo_3.getHP();
				break;
			case 4:
				tmp = my_Capoo_4.getHP();
				break;
			case 5:
				tmp = my_Capoo_5.getHP();
				break;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return tmp;
	}

	public void switch_myRole_to_set_HP(int hp) {
		try {
			switch (my_role) {
			case 1:
				my_Capoo_1.setHp(hp);
				break;
			case 2:
				my_Capoo_2.setHp(hp);
				break;
			case 3:
				my_Capoo_3.setHp(hp);
				break;
			case 4:
				my_Capoo_4.setHp(hp);
				break;
			case 5:
				my_Capoo_5.setHp(hp);
				break;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	// endregion
	// Switch RoleData To Set Other Image
	// region

	private void switch_roledata_to_set_other_game_image() {
		try {
			switch_roledata_to_set_other_game_image_0();
			switch_roledata_to_set_other_game_image_1();
			switch_roledata_to_set_other_game_image_2();
			switch_roledata_to_set_other_game_image_3();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	private void switch_roledata_to_set_other_game_image_0() {
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
	}

	private void switch_roledata_to_set_other_game_image_1() {
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
	}

	private void switch_roledata_to_set_other_game_image_2() {
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
	}

	private void switch_roledata_to_set_other_game_image_3() {
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
	}
	// endregion

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

	public void initAttack(int bulletNumber) {
		state = 1;
		myTid = myTid;
		function = "atk";
		source = -1;
		dest = -1;
		type = bulletNumber;
		X = Start_X;
		Y = Start_Y;
		direction = direction_atk;
		Stype = "@";
	}

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

	public void initAtked() {
		state = 1;
		myTid = myTid;
		function = "atked";
		source = -1;
		dest = -1;
		type = my_hp;
		X = Start_X;
		Y = Start_Y;
		direction = 0;
		Stype = "@";
	}

	public void initAtk2ed() {
		state = 1;
		myTid = myTid;
		function = "atked";
		source = -1;
		dest = -1;
		type = my_hp;
		X = Start_X;
		Y = Start_Y;
		direction = 0;
		Stype = "@";
	}

	public void initbulletdeath(int bulletID, double bulletX, double bulletY) {

		state = 1;
		Tid = -1;
		function = "bulletdeath";
		source = -1;
		dest = -1;
		type = bulletID;
		X = bulletX;
		Y = bulletY;
		direction = 0;
		Stype = "@";
	}

	public void initDeath() {
		state = 1;
		myTid = myTid;
		function = "death";
		source = -1;
		dest = -1;
		type = -1;
		X = -1;
		Y = -1;
		direction = 0;
		Stype = "@";
	}

	// endregion
	// refresh
	// region

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

	// endregion
}