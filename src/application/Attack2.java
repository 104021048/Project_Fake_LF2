package application;

import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;

public class Attack2 extends Thread {
	public Label ammo;
	public double startx, starty;
	public boolean boom = false;

	public Attack2(double x, double y, StackPane sp, int direction) {
		// TODO Auto-generated constructor stub
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
			/*
			 * if (ammo.getTranslateX() == 300) { boom = true; }
			 */
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
