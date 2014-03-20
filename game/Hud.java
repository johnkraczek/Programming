package game;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.Rectangle2D;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

	
	public class Hud extends JPanel {

		private static final long serialVersionUID = 1L;

		Image bg;

		public Hud() {
			this.setBounds(Common.scoreLeft,Common.scoreTop,Common.scoreWidth,Common.scoreHeight*2);

			bg = new ImageIcon("res/Jewel1.png").getImage();
			bg = new ImageIcon(bg.getScaledInstance(bg.getWidth(null),
					bg.getHeight(null), Image.SCALE_SMOOTH)).getImage();
			//this.setPreferredSize(new Dimension(bg.getWidth(null), bg.getHeight(null)));
		}

		public void paintComponent(Graphics g) {
			//System.out.println("Test Image Getting Painted");
			//super.paintComponent(g);
			Graphics2D g2 = (Graphics2D) g;
			Rectangle2D rect = new Rectangle2D.Double(0, Common.scoreHeight-1, Common.scoreWidth-1 , Common.scoreHeight);
			g2.setColor(new Color(0, 200, 0, 200)); // With 50% alpha
			g2.fill(rect);
			
			g2.setColor(new Color(0, 0, 0, 255)); // With 50% alpha
			g2.setStroke(new BasicStroke(2));
			g2.draw(rect);
			
			Font font = new Font(Common.scoreFont, Font.PLAIN, Common.scoreFontSize);
			g2.setFont(font);
			g2.setColor(Color.RED);
			g2.drawString("Score", (float)Common.scoreHeight, (float)Common.scoreHeight/2+10);
			
			g2.setFont(font);
			g2.setColor(Color.BLUE);
			g2.drawString("400", (float)Common.scoreWidth/2, (float)Common.scoreHeight*2-5);
			
		}
	}
