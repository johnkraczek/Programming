package game;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.Rectangle2D;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class GameBG extends JPanel {

	private static final long serialVersionUID = 1L;

	Image bg;

	public GameBG() {
		bg = new ImageIcon(GameBG.class.getResource(Common.background)).getImage();
		bg = new ImageIcon(bg.getScaledInstance(bg.getWidth(null),
				bg.getHeight(null), Image.SCALE_SMOOTH)).getImage();
		this.setBounds(0, 0, Common.frameWidth, Common.frameHeight);
		//System.out.println(bg.getHeight(null)+ " " + bg.getWidth(null));
	}

	public void paintComponent(Graphics g) {
		//System.out.println("Im Getting Painted");
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.drawImage(bg, 0, 0, null);
		Rectangle2D rect = new Rectangle2D.Double(Common.gridLeft,Common.gridTop, Common.jewelWidth*Common.rowColLength ,Common.jewelWidth*Common.rowColLength);
		g2.setColor(new Color(0, 75, 0, 100)); // With 50% alpha
		g2.fill(rect);
		g2.setColor(new Color(0, 0, 0, 255)); // With 50% alpha
		g2.setStroke(new BasicStroke(2));
		g2.draw(rect);
		
	}
}
