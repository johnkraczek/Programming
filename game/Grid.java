package game;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Random;

import javax.swing.JPanel;

public class Grid extends JPanel implements MouseListener {

	private static final long serialVersionUID = -9165676032115582474L;

	protected boolean gridSelect;
	protected int[][] grid = new int[Common.rowColLength][Common.rowColLength];
	protected boolean[][] selected = new boolean[Common.rowColLength][Common.rowColLength];
	protected boolean[][] swap = new boolean[Common.rowColLength][Common.rowColLength];

	public Grid() {
		this.setBounds(Common.gridLeft, Common.gridTop, Common.rowColLength
				* Common.jewelWidth, Common.rowColLength * Common.jewelWidth);
		this.addMouseListener(this);
		Common.setJewelType();
		Random rand = new Random();
		for (int i = 0; i < Common.rowColLength; i++) {
			for (int j = 0; j < Common.rowColLength; j++) {
				grid[i][j] = rand.nextInt(Common.jewelTypes);
			}
		}
	}

	public void switchJewel(int row, int col, int swpRow, int swpCol) {
		int temp = grid[row][col];
		grid[row][col] = grid[swpRow][swpCol];
		grid[swpRow][swpCol] = temp;
			
		
	}
	
	private void checkBroken(int row, int col){
		int type = grid[row][col];
		int rowLength;
		int colLength;
		
	/*	if(row+1<Common.rowColLength && (grid[row+1][col] == type)){
			checkDirection(row)
		}
		*/
	}
	
	private int checkDirection(int row, int col, int dir, int type){
		/* 	 dir:
			1 left
			2 up
			3 right
			4 down
		*/
		
		
		return 0;
	}

	public void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		for (int i = 0; i < Common.rowColLength; i++) {
			for (int j = 0; j < Common.rowColLength; j++) {

				if (!selected[i][j]) {

					g2.drawImage(Common.jewelType[grid[i][j]], i
							* Common.jewelWidth, j * Common.jewelWidth, null);

				} else {
					g2.drawImage(Common.jewelType[grid[i][j]], i
							* Common.jewelWidth, j * Common.jewelWidth - 10,
							null);
				}
			}
		}
	}

	public void callTimer() {
		
		
//logic to check if a jewel is selected and then switch with another if next to. 
		for (int i = 0; i < Common.rowColLength; i++) {
			for (int j = 0; j < Common.rowColLength; j++) {
				if (selected[i][j]) {
					if (i + 1 < Common.rowColLength && swap[i + 1][j]) {
						this.switchJewel(i, j, i + 1, j);
						gridSelect = false;
						selected[i][j] = false;
					}
					if (i - 1 >= 0 && swap[i - 1][j]) {
						this.switchJewel(i, j, i - 1, j);
						gridSelect = false;
						selected[i][j] = false;
					}
					if (j + 1 < Common.rowColLength && swap[i][j + 1]) {
						this.switchJewel(i, j, i, j + 1);
						gridSelect = false;
						selected[i][j] = false;
					}
					if (j - 1 >= 0 && swap[i][j - 1]) {
						this.switchJewel(i, j, i, j - 1);
						gridSelect = false;
						selected[i][j] = false;
					}
				}
			}
		}
		// if you selected somewhere on the board that was not next to a selected jewel, then reset swap location. 
		for (int i = 0; i < Common.rowColLength; i++) {
			for (int j = 0; j < Common.rowColLength; j++) {
				swap[i][j] = false;
			}
		}	
		
		
		//check if jewels are of the same type
	}

	public void mouseClicked(MouseEvent e) {
		int col = e.getPoint().x / Common.jewelWidth;
		int row = e.getPoint().y / Common.jewelWidth;
		if (!gridSelect) {
			gridSelect = true;
			selected[col][row] = true;
		} else {
			swap[col][row] = true;
		}
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
