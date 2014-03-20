package game;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.Random;

import javax.swing.JPanel;

public class Grid extends JPanel {
	

	private static final long serialVersionUID = -9165676032115582474L;

	protected int[][] grid = new int[Common.rowColLength][Common.rowColLength];

	public Grid() {
		this.setBounds(Common.gridLeft, Common.gridTop, Common.rowColLength*Common.jewelWidth, Common.rowColLength*Common.jewelWidth);
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
}
