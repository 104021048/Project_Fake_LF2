package application;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class Client extends Application {
	private ClientCenter clientCenter;
	public Scene scene_login , scene_room;
	public Socket sock;
	public BufferedReader reader;
	public PrintStream writer;
	public GridPane gridpane_login_root , gridpane_room_root;
	public TextField textfield_login_ip , textfield_login_name;
	public Label label_login_ip , label_login_name;
	public Button button_login_connect , button_login_exit , button_room_character1 , button_room_character2 , button_room_character3 , button_room_character4 , 
	button_room_character5 , button_room_ready;
	public ImageView image_login_logo , image_room_player_1 , image_room_player_2 , image_room_player_3 , image_room_player_4;
	public Label label_room_headpicture1 , label_room_headpicture2 , label_room_headpicture3 , label_room_headpicture4 ,
	label_room_name1 , label_room_name2 , label_room_name3 , label_room_name4 , label_room_systemmessage;
	public HBox hbox_room_button;
	public String name;
	public void login_init(){
		gridpane_login_root = new GridPane();
		gridpane_login_root.setPadding(new Insets(25, 25, 25, 25));
		image_login_logo = new ImageView("logo.png");
		textfield_login_ip = new TextField("127.0.0.1");
		textfield_login_name = new TextField();
		label_login_ip = new Label("IP: ");
		label_login_name = new Label("Name: ");
		button_login_connect = new Button("Connect");
		button_login_exit = new Button("Exit");
	}
	
	public void login_setupUI() {
		label_login_ip.setPrefSize(50,10);
		textfield_login_ip.setPrefSize(150, 10);
		label_login_name.setPrefSize(50,10);
		textfield_login_name.setPrefSize(150,10);
		button_login_connect.setPrefSize(100,10);
		button_login_exit.setPrefSize(100,10);
		gridpane_login_root.setHgap(10);
		gridpane_login_root.setVgap(10);
		gridpane_login_root.add(image_login_logo, 1, 1, 10, 1);
		gridpane_login_root.add(label_login_ip, 1, 2, 5, 1);
		gridpane_login_root.add(textfield_login_ip, 1, 3, 5, 1);
		gridpane_login_root.add(label_login_name, 6, 2, 5, 1);
		gridpane_login_root.add(textfield_login_name, 6, 3, 5, 1);
		gridpane_login_root.add(button_login_connect, 1, 10, 5, 1);
		gridpane_login_root.add(button_login_exit, 10, 10, 5, 1);
		scene_login = new Scene(gridpane_login_root, 400, 300);
		scene_login.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
	}

	public void login_setupListener(Stage primaryStage) {
		button_login_connect.setOnAction(e -> {
			name = textfield_login_name.getText().toString();
			String ip = textfield_login_ip.getText().toString();
			Thread t = new Thread(clientCenter = new ClientCenter(primaryStage,sock,ip,name));
			t.start();
			room_init();
			room_setupUI();
			room_setupListener(primaryStage);
			primaryStage.setScene(scene_room);
		});
		button_login_exit.setOnAction(e -> {
			System.exit(1);
		});
	}

	public void room_init(){
		image_room_player_1 = new ImageView("role_1.png");
		image_room_player_2 = new ImageView("role_1.png");
		image_room_player_3 = new ImageView("role_1.png");
		image_room_player_4 = new ImageView("role_1.png");
		label_room_headpicture1 = new Label();
		label_room_headpicture2 = new Label(); 
		label_room_headpicture3 = new Label();
		label_room_headpicture4 = new Label();
		label_room_name1 = new Label(name);
		label_room_name2 = new Label("name2");
		label_room_name3 = new Label("name3");
		label_room_name4 = new Label("name4");
		label_room_systemmessage = new Label("you are 87");
		hbox_room_button = new HBox();
		button_room_character1 = new Button();
		button_room_character2 = new Button();
		button_room_character3 = new Button();
		button_room_character4 = new Button();
		button_room_character5 = new Button();
		button_room_ready = new Button("Ready");
		gridpane_room_root = new GridPane();
		gridpane_room_root.setPadding(new Insets(25, 25, 25, 25));
	}
	
	public void room_setupUI() {
		gridpane_room_root.setHgap(10);
		gridpane_room_root.setVgap(10);
		label_room_headpicture1.setGraphic(image_room_player_1);
		label_room_headpicture2.setGraphic(image_room_player_2);
		label_room_headpicture3.setGraphic(image_room_player_3);
		label_room_headpicture4.setGraphic(image_room_player_4);
		label_room_headpicture1.setPrefSize(150,100);
		label_room_headpicture2.setPrefSize(150,100);
		label_room_headpicture3.setPrefSize(150,100);
		label_room_headpicture4.setPrefSize(150,100);
		label_room_name1.setPrefSize(150,30);
		label_room_name2.setPrefSize(150,30);
		label_room_name3.setPrefSize(150,30);
		label_room_name4.setPrefSize(150,30);
		button_room_character1.setPrefSize(50,50);
		button_room_character2.setPrefSize(50,50);
		button_room_character3.setPrefSize(50,50);
		button_room_character4.setPrefSize(50,50);
		button_room_character5.setPrefSize(50,50);
		button_room_ready.setPrefSize(100,30);
		label_room_systemmessage.setPrefSize(300,30);

		button_room_character1.setGraphic(new ImageView("button_role_1.png"));
		button_room_character2.setGraphic(new ImageView("button_role_2.png"));
		button_room_character3.setGraphic(new ImageView("button_role_3.png"));
		button_room_character4.setGraphic(new ImageView("button_role_4.png"));
		button_room_character5.setGraphic(new ImageView("button_role_5.png"));
		
		hbox_room_button.setPadding(new Insets(25, 25, 25, 25));
		hbox_room_button.setSpacing(10);
		hbox_room_button.getChildren().add(button_room_character1);
		hbox_room_button.getChildren().add(button_room_character2);
		hbox_room_button.getChildren().add(button_room_character3);
		hbox_room_button.getChildren().add(button_room_character4);
		hbox_room_button.getChildren().add(button_room_character5);

		gridpane_room_root.add(label_room_headpicture1, 1, 1,1,1);
		gridpane_room_root.add(label_room_headpicture2, 3, 1,1,1);
		gridpane_room_root.add(label_room_headpicture3, 5, 1,1,1);
		gridpane_room_root.add(label_room_headpicture4, 7, 1,1,1);
		gridpane_room_root.add(label_room_name1, 1, 2,1,1);
		gridpane_room_root.add(label_room_name2, 3, 2,1,1);
		gridpane_room_root.add(label_room_name3, 5, 2,1,1);
		gridpane_room_root.add(label_room_name4, 7, 2,1,1);
		gridpane_room_root.add(hbox_room_button, 3, 5, 8, 1);
		gridpane_room_root.add(button_room_ready, 1, 6, 3, 1);
		gridpane_room_root.add(label_room_systemmessage, 4,6,6,1);
		
		scene_room = new Scene(gridpane_room_root , 800 , 600);
		scene_room.getStylesheets().add(getClass().getResource("application.css").toExternalForm());

	}

	public void room_setupListener(Stage primaryStage) {
		 button_room_character1.setOnAction(e -> {
			 image_room_player_1 = new ImageView("role_1.png");
			 label_room_headpicture1.setGraphic(image_room_player_1);
			clientCenter.SelectedRole_1();
		 });
		 button_room_character2.setOnAction(e -> {
			 image_room_player_1 = new ImageView("role_2.png");
			 label_room_headpicture1.setGraphic(image_room_player_1);
			 clientCenter.SelectedRole_2();
		 });
		 button_room_character3.setOnAction(e -> {
			 image_room_player_1 = new ImageView("role_3.png");
			 label_room_headpicture1.setGraphic(image_room_player_1);
			 clientCenter.SelectedRole_3();
		 });
		 button_room_character4.setOnAction(e -> {
			 image_room_player_1 = new ImageView("role_4.png");
			 label_room_headpicture1.setGraphic(image_room_player_1);
			 clientCenter.SelectedRole_4();
		 });
		 button_room_character5.setOnAction(e -> {
			 image_room_player_1 = new ImageView("role_5.png");
			 label_room_headpicture1.setGraphic(image_room_player_1);
			 clientCenter.SelectedRole_5();
		 });
		 button_room_ready.setOnAction(e -> {});
	}
	
	public void start(Stage primaryStage) {
		login_init();
		login_setupUI();
		login_setupListener(primaryStage);
		primaryStage.setTitle("Client");
		primaryStage.setScene(scene_login);
		primaryStage.setResizable(false);
		primaryStage.show();
		
	}

	public static void main(String[] args) {
		launch(args);
	}
	

}
