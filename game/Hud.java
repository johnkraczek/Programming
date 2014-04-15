package game;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Rectangle2D;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

	
	public class Hud extends JPanel implements ActionListener {

		private static final long serialVersionUID = 1L;

		Image bg;
		private static int score = 0;
		JButton newGame;

		public Hud() {
			this.setBounds(Common.scoreLeft,Common.scoreTop,Common.scoreWidth,Common.scoreHeight*3);
			
			bg = new ImageIcon("res/Jewel1.png").getImage();
			bg = new ImageIcon(bg.getScaledInstance(bg.getWidth(null),
			bg.getHeight(null), Image.SCALE_SMOOTH)).getImage();
			newGame = new JButton("New Game");
			newGame.addActionListener(this);
			
			this.setLayout(null);
			
			newGame.setBounds(50, 85 , 100, 30);
			this.add(newGame);	
		}
		
		public void scored(int i){
			score+=i;
		}
		
		public void resetScore(){
			score=0;
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
			g2.drawString(Integer.toString(score), (float)Common.scoreTxtLeft, (float)Common.scoreTxtTop);
			
		}

		@Override
		public void actionPerformed(ActionEvent event) {
			Object source = event.getSource();
			if(source == newGame){
				Grid.newGame = true;
			}
			
		}
	}
