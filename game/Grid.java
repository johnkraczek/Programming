package game;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Random;

import javax.swing.JPanel;

public class Grid extends JPanel implements MouseListener {
	
	private static final long serialVersionUID = -9165676032115582474L;

	protected int[][] grid = new int[Common.rowColLength][Common.rowColLength];

	public Grid() {
		this.setBounds(Common.gridLeft, Common.gridTop, Common.rowColLength*Common.jewelWidth, Common.rowColLength*Common.jewelWidth);
		this.addMouseListener(this);
		Common.setJewelType();
		Random rand = new Random();
		for (int i = 0; i < Common.rowColLength; i++) {
			for (int j = 0; j < Common.rowColLength; j++) {
				grid[i][j] = rand.nextInt(Common.jewelTypes);
			}
		}
    }
	
	public void SwitchJewel(int row, int col, int jewel){
		grid[row][col] = jewel;
	}

	public void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		for (int i = 0; i < Common.rowColLength; i++) {
			for (int j = 0; j < Common.rowColLength; j++) {
				
				g2.drawImage(Common.jewelType[grid[i][j]], i* Common.jewelWidth, j * Common.jewelWidth, null);
			}
		}
	}
	
	public void mouseClicked(MouseEvent e) {
		int x = e.getPoint().x;
		int y = e.getPoint().y;
		System.out.println("Mouse Pressed(" + x + "," + y + ")");
	}
	public void mouseEntered(MouseEvent e) {
	}
	public void mouseExited(MouseEvent e) {
	}
	public void mousePressed(MouseEvent e) {
	}
	public void mouseReleased(MouseEvent e) {
	}
	
}
