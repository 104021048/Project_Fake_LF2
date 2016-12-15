package application;

import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

/* �ϥΤ�k:
 * �ͦ�:
 * �ۤv-������L�����𤧫�Anew �X�@��Attack2 �[�J�l�uMap�ðe�X�T����Server�C
 * 
 * �O�H-�bClientCenter������l�u�X�{���T������Anew�X�@��Attack2�C
 * 		��l�u�[�J�@��Map<int,Attack2>�̭��A�e����int �񦬨쪺�l�u�s���C
 * 
 * ���`:
 * �ۤv-��l�u�o�͸I���A�o�X�ۤv���˸�l�u���`���T���A��l�u�qMap���M��
 * �O�H-����l�u���`���T�����ɭԡA�qMap�h��Key=���`�l�u�и�������A�I�s����Attack2.bulletDeath();
 *		 �M��������Attack2=null;(Option: System.gc();��ĳ�t�Υh�M���U��)
 * 		��<Key,Value>=<���`�l�u�s��,�l�u>�qMap�̭��h���C
 */

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

	public Attack2(Client client, ClientCenter clientCenter, double x, double y, StackPane sp, int direction,
			int bulletID) {
		this.client = client;
		this.clientCenter = clientCenter;
		startx = x;
		starty = y;
		this.sp = sp;
		this.direction = direction;
		this.bulletID = bulletID;
		setGraph(sp, direction);
		// ���l�u���@�|��
		bulletFly();
	}

	public void bulletFly() {
		timeline = new Timeline();
		// �o�Ӱʵe��������
		timeline.setCycleCount(55);
		// Duration�C���h�֬�,�M��Ĳ�o�ƥ�
		final KeyFrame kf = new KeyFrame(Duration.millis(30), new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				// ����collision
				collision(imv.getTranslateX(), imv.getTranslateY());
				if (imv.getTranslateX() > (sizex * -1) && imv.getTranslateX() < sizex && !boom) {
					// �b��V��ɤ����ӥB���z��
					switch (direction) {
					case 1:
						imv.setTranslateX(imv.getTranslateX() + range);
						break;
					case -1:
						imv.setTranslateX(imv.getTranslateX() - range);
						break;
					}
				} else {
					// �[�t�ʵe����
					timeline.setRate(100);
					System.out.println(bulletID + "collision");
				}
			}
		});
		timeline.getKeyFrames().add(kf);
		timeline.play();
		timeline.setOnFinished(e -> {
			System.out.println(imv.getTranslateX());
			FadeTransition fade = new FadeTransition(Duration.millis(100.0D), imv);
			fade.setFromValue(1.0D);
			fade.setToValue(0.0D);
			fade.setOnFinished(new EventHandler<ActionEvent>() {
				public void handle(ActionEvent t) {
					// �C�C�����������ۤv�qsp����
					sp.getChildren().remove(imv);
				}
			});
			fade.play();
			// TODO: meassage to Server �i�DServer�l�u���`
			// ClientCenter���@��public function
		});

	}

	public void setGraph(StackPane sp, int getdirection) {
		if (getdirection == 1) {
			imv = new ImageView();
			image2 = new Image("ammoright.png");
			imv.setImage(image2);
		} else if (getdirection == -1) {
			imv = new ImageView();
			image2 = new Image("ammoleft.png");
			imv.setImage(image2);
		}

		imv.setTranslateX(startx);// �_�l��m
		imv.setTranslateY(starty);// �_�l��m
		sp.getChildren().add(imv);
	}

	public void collision(double x, double y) {
		// TODO: �����I��
		// �o�䪺�Ѽ�xy�O�l�u����m�A�]�����ܡAClient��m�ݭn�I�s
		// Client�x�s�ۤv����m�Ӹ�l�u�i��P�_
		// if(){
		// boom=true;
		// }else{
		// boom=false;
		// }

	}

	public void bulletDeath() {
		// ��ClientCenter�եΤl�u���`
		// �[�t���ʵe����

		timeline.setRate(100);
	}
}