package game;

import java.awt.Image;

import javax.swing.ImageIcon;

public class Common {
	
	public static final int fps = 30;
	public static final int animationDur = 5; 
	
	public static final int jewelWidth = 50;
	public static final int jewelTypes = 6;
	public static final int rowColLength = 8;

	public static final int frameHeight = 515;
	public static final int frameWidth = 775;
	
	public static final int gridLeft =325;
	public static final int gridTop = 35;
	
	public static final int scoreLeft = 100;
	public static final int scoreTop = 325;
	public static final int scoreWidth = 150;
	public static final int scoreHeight = 40;
	
	public static final int scoreFontSize = 40;
	public static final String scoreFont = "SansSerif";

	public static final String background = "res/gameBackground.jpg";

	public static final String[] jewelPath = new String[] { "res/Jewel0.png","res/Jewel1.png",
			"res/Jewel2.png", "res/Jewel3.png", "res/jewel4.png",
			"res/Jewel5.png" };

	public static Image[] jewelType = new Image[Common.jewelTypes];

	static void setJewelType() {
		for (int i = 0; i < jewelTypes; i++) {
			jewelType[i] = new ImageIcon(new ImageIcon(Common.class.getResource(jewelPath[i])).getImage()
					.getScaledInstance(jewelWidth, jewelWidth,
							Image.SCALE_SMOOTH)).getImage();
		}
	}
}
