package application;

<<<<<<< HEAD
import java.util.Map;

import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
=======
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
>>>>>>> origin/master
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;
<<<<<<< HEAD

/* 使用方法:
 * 生成:
 * 自己-收到鍵盤的遠攻之後，new 出一個Attack2 加入子彈Map並送出訊息給Server。
 * 
 * 別人-在ClientCenter接受到子彈出現的訊息之後，new出一個Attack2。
 * 		把子彈加入一個Map<int,Attack2>裡面，前面的int 放收到的子彈編號。
 * 
 * 死亡:
 * 自己-跟子彈發生碰撞，發出自己受傷跟子彈死亡的訊息，把子彈從Map中清掉
 * 別人-收到子彈死亡的訊息的時候，從Map去找Key=死亡子彈標號的那格，呼叫那個Attack2.bulletDeath();
 *		 然後讓那個Attack2=null;(Option: System.gc();建議系統去清掃垃圾)
 * 		把<Key,Value>=<死亡子彈編號,子彈>從Map裡面去掉。
 */

=======

/* 使用方法:
 * 生成:
 * 自己-收到鍵盤的遠攻之後，new 出一個Attack2 加入子彈Map並送出訊息給Server。
 * 
 * 別人-在ClientCenter接受到子彈出現的訊息之後，new出一個Attack2。
 * 		把子彈加入一個Map<int,Attack2>裡面，前面的int 放收到的子彈編號。
 * 
 * 死亡:
 * 自己-跟子彈發生碰撞，發出自己受傷跟子彈死亡的訊息，把子彈從Map中清掉
 * 別人-收到子彈死亡的訊息的時候，從Map去找Key=死亡子彈標號的那格，呼叫那個Attack2.bulletDeath();
 *		 然後讓那個Attack2=null;(Option: System.gc();建議系統去清掃垃圾)
 * 		把<Key,Value>=<死亡子彈編號,子彈>從Map裡面去掉。
 */

>>>>>>> origin/master
public class Attack2 {
	public double startx, starty;
	public boolean boom = false;
	public Client client;
	public ClientCenter clientCenter;
	private int range = 20;
	ImageView imv;
	Image image2;
	Timeline timeline;
	int direction;
	private double sizex = 500;
	private int bulletID = 0;
	private StackPane sp;
<<<<<<< HEAD
	private Map<Integer, Attack2> bulletlist;
	private double clientCenterStart_X, clientCenterStart_Y;

	public Attack2(Client client, ClientCenter clientCenter, double x, double y, StackPane sp, int direction,
			int bulletID, Map<Integer, Attack2> bulletlist) {
=======

	public Attack2(Client client, ClientCenter clientCenter, double x, double y, StackPane sp, int direction,
			int bulletID) {
>>>>>>> origin/master
		this.client = client;
		this.clientCenter = clientCenter;
		startx = x;
		starty = y;
		this.sp = sp;
		this.direction = direction;
		this.bulletID = bulletID;
<<<<<<< HEAD
		this.bulletlist = bulletlist;
		clientCenterStart_X = clientCenter.Start_X;
		clientCenterStart_Y = clientCenter.Start_Y;
		timeline = new Timeline();
		setGraph(sp, direction);
		// 讓子彈飛一會兒
		bulletFly();

	}

	public void bulletFly() {

=======
		setGraph(sp, direction);
		// 讓子彈飛一會兒
		bulletFly();
	}

	public void bulletFly() {
		timeline = new Timeline();
>>>>>>> origin/master
		// 這個動畫播的次數
		timeline.setCycleCount(55);
		// Duration每次多少秒,然後觸發事件
		final KeyFrame kf = new KeyFrame(Duration.millis(30), new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				// 偵測collision
				collision(imv.getTranslateX(), imv.getTranslateY());
				if (imv.getTranslateX() > (sizex * -1) && imv.getTranslateX() < sizex && !boom) {
					// 在橫向邊界之內而且未爆炸
					switch (direction) {
					case 1:
						imv.setTranslateX(imv.getTranslateX() + range);
						break;
					case -1:
						imv.setTranslateX(imv.getTranslateX() - range);
						break;
					}
				} else {
					// 加速動畫播完
					timeline.setRate(100);
<<<<<<< HEAD

=======
					System.out.println(bulletID + "collision");
>>>>>>> origin/master
				}
			}
		});
		timeline.getKeyFrames().add(kf);
		timeline.play();
		timeline.setOnFinished(e -> {
			System.out.println(imv.getTranslateX());
<<<<<<< HEAD
			FadeTransition fade = new FadeTransition(Duration.millis(10.0D), imv);
=======
			FadeTransition fade = new FadeTransition(Duration.millis(100.0D), imv);
>>>>>>> origin/master
			fade.setFromValue(1.0D);
			fade.setToValue(0.0D);
			fade.setOnFinished(new EventHandler<ActionEvent>() {
				public void handle(ActionEvent t) {
					// 慢慢消失結束後把自己從sp移除
					sp.getChildren().remove(imv);
<<<<<<< HEAD
					System.out.println(bulletID + " death");
					bulletlist.remove(bulletID);
					// TODO: boom?
					if (boom == true) {

					}
				}
			});
			fade.play();
=======
				}
			});
			fade.play();
			// TODO: meassage to Server 告訴Server子彈死亡
			// ClientCenter給一個public function
>>>>>>> origin/master
		});

	}

	public void setGraph(StackPane sp, int getdirection) {
<<<<<<< HEAD

=======
>>>>>>> origin/master
		if (getdirection == 1) {
			imv = new ImageView();
			image2 = new Image("ammoright.png");
			imv.setImage(image2);
		} else if (getdirection == -1) {
			imv = new ImageView();
			image2 = new Image("ammoleft.png");
			imv.setImage(image2);
		}
<<<<<<< HEAD
		Platform.runLater(() -> {
			if (direction == 1) {
				imv.setTranslateX(startx + 38.5);// 起始位置
				System.out.println("bullet startx: "+startx +" bias: +37.5");
			} else if (direction == -1) {
				imv.setTranslateX(startx -38.5);// 起始位置
				System.out.println("bullet startx: "+startx +" bias: -37.5");
			}

			imv.setTranslateY(starty);// 起始位置
			
			sp.getChildren().add(imv);
		});
	}

	public void collision(double x, double y) {
		// TODO: 偵測碰撞
		// 這邊的參數xy是子彈的位置，因為時變，Client位置需要呼叫
		// Client儲存自己的位置來跟子彈進行判斷
		if (x<clientCenterStart_X &&x + 12.5 >= clientCenterStart_X - 25 && direction == 1 && Math.abs(clientCenterStart_Y - y) <= 37.5) {
			System.out.println("collision1" + clientCenterStart_X);
			System.out.println("collision1" + x);
			boom = true;
		} else if (x>clientCenterStart_X && x - 12.5 <= clientCenterStart_X + 25 && direction == -1
				&& Math.abs(clientCenterStart_Y - y) <= 37.5) {
			System.out.println("collision2" + clientCenterStart_X);
			System.out.println("collision2" + x);
			boom = true;
		} else {
			System.out.println("collision else");
			boom = false;
		}

=======

		imv.setTranslateX(startx);// 起始位置
		imv.setTranslateY(starty);// 起始位置
		sp.getChildren().add(imv);
	}

	public void collision(double x, double y) {
		// TODO: 偵測碰撞
		// 這邊的參數xy是子彈的位置，因為時變，Client位置需要呼叫
		// Client儲存自己的位置來跟子彈進行判斷
		// if(){
		// boom=true;
		// }else{
		// boom=false;
		// }

>>>>>>> origin/master
	}

	public void bulletDeath() {
		// 讓ClientCenter調用子彈死亡
		// 加速讓動畫播完

		timeline.setRate(100);
	}
}