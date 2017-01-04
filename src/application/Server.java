package application;

import java.io.IOException;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class Server extends Application {
	// region
	public Scene scene_main;
	public GridPane gridpane_main_root;
	public ImageView image_main_logo;
	public Button button_main_client, button_main_server;

	private void main_init() {
		gridpane_main_root = new GridPane();
		gridpane_main_root.setPadding(new Insets(25, 25, 25, 25));
		image_main_logo = new ImageView("logo.png");
		button_main_client = new Button("Client");
		button_main_server = new Button("Server");
	}

	private void main_setupUI() {

		button_main_client.setPrefSize(100, 10);
		button_main_server.setPrefSize(100, 10);
		gridpane_main_root.setHgap(10);
		gridpane_main_root.setVgap(10);
		gridpane_main_root.add(image_main_logo, 1, 1, 10, 1);
		gridpane_main_root.add(button_main_client, 1, 10, 5, 1);
		gridpane_main_root.add(button_main_server, 10, 10, 5, 1);
		scene_main = new Scene(gridpane_main_root, 400, 300);
		scene_main.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
	}

	private void main_setupListener(Stage primaryStage) {
		button_main_client.setOnAction(e -> {
			Client client = new Client(primaryStage);
		});
		button_main_server.setOnAction(e -> {
			primaryStage.setTitle("Server is running..當機是正常現象請不要關閉");
			Thread serverThread = new Thread(new Out());
			serverThread.start();
			button_main_client.setDisable(true);
			button_main_server.setDisable(true);
			primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
		          public void handle(WindowEvent we) {
		              System.out.println("Server is closing");
		              System.exit(0);
		              try {
						Out.closeSocket();
					} catch (IOException e) {
					}
		              primaryStage.close();
		          }
		    });
			
		});
		
	}

	// endregion
	public void start(Stage primaryStage) {
		main_init();
		main_setupUI();
		main_setupListener(primaryStage);
		primaryStage.setTitle("Project_Fake_LF2");
		primaryStage.setScene(scene_main);
		primaryStage.setResizable(false);
		primaryStage.show();
		        
		

	}

	private static void main(String[] args) {

		Application.launch(args);
	}
	// endregion
}