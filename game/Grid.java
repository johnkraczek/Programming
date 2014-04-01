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
	protected int[][] animated = new int[Common.rowColLength][Common.rowColLength];
	protected boolean animating = false;
	protected boolean[][] selected = new boolean[Common.rowColLength][Common.rowColLength];
	protected boolean[][] swap = new boolean[Common.rowColLength][Common.rowColLength];

	protected Random rand = new Random();

	public Grid() {
		this.setBounds(Common.gridLeft, Common.gridTop, Common.rowColLength
				* Common.jewelWidth, Common.rowColLength * Common.jewelWidth);
		this.addMouseListener(this);
		Common.setJewelType();
		for (int i = 0; i < Common.rowColLength; i++) {
			for (int j = 0; j < Common.rowColLength; j++) {
				grid[i][j] = rand.nextInt(Common.jewelTypes - 1) + 1;
			}
		}
	}

	public void switchJewel(int row, int col, int swpRow, int swpCol) {

		int temp = grid[row][col];
		grid[row][col] = grid[swpRow][swpCol];
		grid[swpRow][swpCol] = temp;

		checkBroken(swpRow, swpCol);
		checkBroken(row, col);
		checkEmpty();

		gridSelect = false;
		selected[row][col] = false;
		reset();

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

		if (rowLength > 2) {
			breakJewels(row, col, 1, type);
			breakJewels(row, col, 2, type);
		}
		if (colLength > 2) {
			breakJewels(row, col, 3, type);
			breakJewels(row, col, 4, type);
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

	private void breakJewels(int row, int col, int dir, int type) {
		/*
		 * dir: 1+2 Row(left/right) 3+4 Col(down/up)
		 */

		switch (dir) {
		case 1:
			if (row + 1 < Common.rowColLength && (grid[row + 1][col] == type)) {
				breakJewels(row + 1, col, 1, type);
			}
		case 2:

			if (row - 1 >= 0 && (grid[row - 1][col] == type)) {
				breakJewels(row - 1, col, 2, type);
			}
			break;
		case 3:
			if (col + 1 < Common.rowColLength && (grid[row][col + 1] == type)) {
				breakJewels(row, col + 1, 3, type);
			}
			break;
		case 4:
			if (col - 1 >= 0 && (grid[row][col - 1] == type)) {
				breakJewels(row, col - 1, 4, type);
			}
			break;
		}

		grid[row][col] = 0;
	}

	private boolean checkEmpty() {
		boolean temp = false;
		for (int i = Common.rowColLength - 1; i >= 0; i--) {
			for (int j = Common.rowColLength - 1; j >= 0; j--) {

				if (grid[i][j] == 0) {
					fillDown(i, j);
					temp = true;
				}
			}
		}
		return temp;
	}

	private void fillDown(int row, int col) {
		int i = 1;
		while (col - i >= 0) {
			if (grid[row][col - i] != 0) {
				int temp = grid[row][col];
				grid[row][col] = grid[row][col - i];
				grid[row][col - i] = temp;
				return;
			} else {
				i++;
			}
		}

		grid[row][col] = rand.nextInt(Common.jewelTypes - 1) + 1;
		return;
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
						if(!animating){
							animating = true;
							animated[i][j] = 30;
							//System.out.println("Setting animated to 30");
						} else {
							if(animated[i][j]>0){
								animated[i][j]--;
								//System.out.print(animated[i][j]);
							}else{
								//System.out.print("bad"+animated[i][j]);
							switchJewel(i, j, i + 1, j);
							animating = false;
							}
						}
					}
					if (i - 1 >= 0 && swap[i - 1][j]) {
						if(!animating){
							animating = true;
							animated[i][j] = 30;
							//System.out.println("Setting animated to 30");
						} else {
							if(animated[i][j]>0){
								animated[i][j]--;
								//System.out.print(animated[i][j]);
							}else{
								//System.out.print("bad"+animated[i][j]);
								switchJewel(i, j, i - 1, j);
								animating = false;
							}
						}
					}
					if (j + 1 < Common.rowColLength && swap[i][j + 1]) {
						
						if(!animating){
							animating = true;
							animated[i][j] = 30;
							//System.out.println("Setting animated to 30");
						} else {
							if(animated[i][j]>0){
								animated[i][j]--;
								//System.out.print(animated[i][j]);
							}else{
								//System.out.print("bad"+animated[i][j]);
								switchJewel(i, j, i, j + 1);
								animating = false;
							}
						}
					}
					if (j - 1 >= 0 && swap[i][j - 1]) {
						if(!animating){
							animating = true;
							animated[i][j] = 30;
							//System.out.println("Setting animated to 30");
						} else {
							if(animated[i][j]>0){
								animated[i][j]--;
								//System.out.print(animated[i][j]+" ");
							}else{
								//System.out.print("bad"+animated[i][j]);
								switchJewel(i, j, i, j - 1);
								animating = false;
							}
						}
					}
				}
			}
		}

		// if you selected somewhere on the board that was not next to a
		// selected jewel, then reset swap location.
		/*

		}*/
	}
	public void reset(){
		for (int i = 0; i < Common.rowColLength; i++) {
			for (int j = 0; j < Common.rowColLength; j++) {
				swap[i][j] = false;
			}
		}
	}

	public void mouseClicked(MouseEvent e) {
		if (!animating) {
			int col = e.getPoint().x / Common.jewelWidth;
			int row = e.getPoint().y / Common.jewelWidth;
			if (!gridSelect) {
				gridSelect = true;
				selected[col][row] = true;
			} else {
				swap[col][row] = true;
			}
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
