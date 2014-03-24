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

		//System.out.println("Before Switch at " + row + " " + col + " Which is a " + grid[row][col]);

		int temp = grid[row][col];
		grid[row][col] = grid[swpRow][swpCol];
		grid[swpRow][swpCol] = temp;
		checkBroken(swpRow, swpCol);
		checkBroken(row,col);

	}

	private void checkBroken(int row, int col) {
		int type = grid[row][col];
		int rowLength = 0;
		int colLength = 0;

		if (row + 1 < Common.rowColLength && (grid[row + 1][col] == type)) {
			rowLength = checkDirection(row, col, 1, type);
		}

		if (row - 1 >= 0 && (grid[row - 1][col] == type)) {
			if (rowLength == 0) {
				rowLength += checkDirection(row, col, 2, type);
			} else {
				rowLength += checkDirection(row, col, 2, type) - 1;
			}
		}

		if (col + 1 < Common.rowColLength && (grid[row][col + 1] == type)) {
			colLength = checkDirection(row, col, 3, type);
		}

		if (col - 1 >= 0 && (grid[row][col - 1] == type)) {
			if (colLength == 0) {
				colLength += checkDirection(row, col, 4, type);
			} else {
				colLength += checkDirection(row, col, 4, type) - 1;
			}
		}
		
		if(rowLength>=3){
			breakJewels(row,col,1);
		}
		if(colLength>3){
			breakJewels(row,col,2);
		}
	}

	private int checkDirection(int row, int col, int dir, int type) {
		/*
		 * dir: 1 right 2 left 3 down 4 up
		 */

		switch (dir) {
		case 1:
			if (row + 1 < Common.rowColLength && (grid[row + 1][col] == type)) {
				return checkDirection(row + 1, col, 1, type) + 1;
			}
			break;
		case 2:
			if (row - 1 >= 0 && (grid[row - 1][col] == type)) {
				return checkDirection(row - 1, col, 2, type) + 1;
			}
			break;
		case 3:
			if (col + 1 < Common.rowColLength && (grid[row][col + 1] == type)) {
				return checkDirection(row, col + 1, 3, type) + 1;
			}
			break;
		case 4:
			if (col - 1 >= 0 && (grid[row][col - 1] == type)) {
				return checkDirection(row, col - 1, 4, type) + 1;
			}
			break;
		}

		return 1;
	}

	private void breakJewels(int row, int col, int dir){
		/* row (grid row) 
		 * col (grid col)
		 * dir (direction)
		 *  1 = row
		 *  2 = col
		 */
		
		
		
		
		
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

		// logic to check if a jewel is selected and then switch with another if
		// next to.
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
		// if you selected somewhere on the board that was not next to a
		// selected jewel, then reset swap location.
		for (int i = 0; i < Common.rowColLength; i++) {
			for (int j = 0; j < Common.rowColLength; j++) {
				swap[i][j] = false;
			}
		}

		// check if jewels are of the same type
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
