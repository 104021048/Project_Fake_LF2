package application;

import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;

public class Attack2 extends Thread {
	public Label ammo;
	public double startx, starty;
	public boolean boom = false;
	public Client client;
	public ClientCenter clientCenter;
	private int atk_range = 20;

	public Attack2(Client client, ClientCenter clientCenter, double x, double y, StackPane sp, int direction) {
		// TODO Auto-generated constructor stub
		this.client = client;
		this.clientCenter = clientCenter;
		startx = x;
		starty = y;
		ammo = new Label("a");
		ammo.setPrefSize(10, 5);
		setGraph(sp);
		while (!boom) {
			if (direction == 1)
				MoveRight();
			else
				MoveLeft();
			try {
				Thread.sleep(300);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void setGraph(StackPane sp) {
		Platform.runLater(new Runnable() {
			public void run() {
				sp.getChildren().add(ammo);
				ammo.setTranslateX(startx);
				ammo.setTranslateY(starty);
			}
		});
	}

	public void MoveRight() {
		if (!boom) {
			ammo.setTranslateX(ammo.getTranslateX() + 50);
			if (ammo.getTranslateX() >= client.c1_x - atk_range && ammo.getTranslateX() <= client.c1_x + atk_range) {
				boom = true;
				this.ammo.setDisable(true);
				clientCenter.writer.println(clientCenter.myTid);
			}

		} else {

		}
	}

	public void MoveLeft() {
		if (!boom) {
			ammo.setTranslateX(ammo.getTranslateX() - 50);
			/*
			 * if (ammo.getTranslateX() == 300) { boom = true; }
			 */
		} else {

		}
	}
}
