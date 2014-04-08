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
	protected boolean animating;
	protected Jewel[][] grid = new Jewel[Common.rowColLength][Common.rowColLength];
	protected Random rand = new Random();

	public Grid() {
		this.setBounds(Common.gridLeft, Common.gridTop, Common.rowColLength
				* Common.jewelWidth, Common.rowColLength * Common.jewelWidth);
		this.addMouseListener(this);
		Common.setJewelType();

		for (int i = 0; i < Common.rowColLength; i++) {
			for (int j = 0; j < Common.rowColLength; j++) {
				grid[i][j] = new Jewel();
				grid[i][j].type = rand.nextInt(Common.jewelTypes - 1) + 1;
			}
		}

		do {
			for (int i = 0; i < Common.rowColLength; i++) {
				for (int j = 0; j < Common.rowColLength; j++) {
					checkBroken(i, j);
				}
			}
		} while (initialchkEmpty());
	}

	public boolean initialchkEmpty() {
		for (int i = Common.rowColLength - 1; i >= 0; i--) {
			for (int j = Common.rowColLength - 1; j >= 0; j--) {
				if (grid[i][j].type == 0) {
					fillDownInit(i, j);
					return true;
				}
			}
		}
		return false;
	}

	private void fillDownInit(int row, int col) {
		int i = 1;
		while (col - i >= 0) {
			if (grid[row][col - i].type != 0) {
				int temp = grid[row][col].type;
				grid[row][col].type = grid[row][col - i].type;
				grid[row][col - i].type = temp;
				return;
			} else {
				i++;
			}
		}

		grid[row][col].type = rand.nextInt(Common.jewelTypes - 1) + 1;
		return;
	}

	public void switchJewel(int row, int col, int swpRow, int swpCol) {
		// System.out.println("Entering Switch Jewel");
		if (grid[row][col].moveTo[0] != 0) {

			grid[row][col].moveTo[0]--;
			grid[swpRow][swpCol].moveTo[0]--;

			if (grid[row][col].moveTo[0] == 0) {
				int temp = grid[row][col].type;
				grid[row][col].type = grid[swpRow][swpCol].type;
				grid[swpRow][swpCol].type = temp;
				// checkEmpty()

				gridSelect = false;
				grid[row][col].selected = false;

				for (int i = 0; i < Common.rowColLength; i++) {
					for (int j = 0; j < Common.rowColLength; j++) {
						grid[i][j].swap = false;
					}
				}
			}
		} else {
			grid[row][col].moveTo[0] = Common.animationDur;
			grid[row][col].moveTo[1] = swpRow;
			grid[row][col].moveTo[2] = swpCol;
			grid[swpRow][swpCol].moveTo[0] = Common.animationDur;
			grid[swpRow][swpCol].moveTo[1] = row;
			grid[swpRow][swpCol].moveTo[2] = col;
		}
		// System.out.println(grid[row][col].moveTo[0]);
	}

	private void checkBroken(int row, int col) {
		int type = grid[row][col].type;
		int rowLength = 0;
		int colLength = 0;

		if (row + 1 < Common.rowColLength && (grid[row + 1][col].type == type)) {
			rowLength = checkDirection(row, col, 1, type);
		}

		if (row - 1 >= 0 && (grid[row - 1][col].type == type)) {
			if (rowLength == 0) {
				rowLength += checkDirection(row, col, 2, type);
			} else {
				rowLength += checkDirection(row, col, 2, type) - 1;
			}
		}

		if (col + 1 < Common.rowColLength && (grid[row][col + 1].type == type)) {
			colLength = checkDirection(row, col, 3, type);
		}

		if (col - 1 >= 0 && (grid[row][col - 1].type == type)) {
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
			if (row + 1 < Common.rowColLength
					&& (grid[row + 1][col].type == type)) {
				return checkDirection(row + 1, col, 1, type) + 1;
			}
			break;
		case 2:
			if (row - 1 >= 0 && (grid[row - 1][col].type == type)) {
				return checkDirection(row - 1, col, 2, type) + 1;
			}
			break;
		case 3:
			if (col + 1 < Common.rowColLength
					&& (grid[row][col + 1].type == type)) {
				return checkDirection(row, col + 1, 3, type) + 1;
			}
			break;
		case 4:
			if (col - 1 >= 0 && (grid[row][col - 1].type == type)) {
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
			if (row + 1 < Common.rowColLength
					&& (grid[row + 1][col].type == type)) {
				breakJewels(row + 1, col, 1, type);
			}
		case 2:

			if (row - 1 >= 0 && (grid[row - 1][col].type == type)) {
				breakJewels(row - 1, col, 2, type);
			}
			break;
		case 3:
			if (col + 1 < Common.rowColLength
					&& (grid[row][col + 1].type == type)) {
				breakJewels(row, col + 1, 3, type);
			}
			break;
		case 4:
			if (col - 1 >= 0 && (grid[row][col - 1].type == type)) {
				breakJewels(row, col - 1, 4, type);
			}
			break;
		}
		grid[row][col].type = 0;
	}

	private void checkEmpty() {
		for (int i = Common.rowColLength - 1; i >= 0; i--) {
			for (int j = Common.rowColLength - 1; j >= 0; j--) {
				if (grid[i][j].type == 0) {
					fillDown(i, j);
					j = 0;
				}
			}
		}
	}

	private void fillDown(int row, int col) {
		int i = 1;
		while (col - i >= 0) {
			if (grid[row][col - i].type != 0) {

				if (grid[row][col].moveTo[0] != 0) {
					grid[row][col].moveTo[0]--;

					if (grid[row][col].moveTo[0] == 0) {
						int temp = grid[row][col].type;
						grid[row][col].type = grid[row][col - i].type;
						grid[row][col - i].type = temp;
					}
				} else {
					grid[row][col].moveTo[0] = Common.animationDur;
					grid[row][col].moveTo[1] = row;
					grid[row][col].moveTo[2] = col - i;
				}
				return;
			} else {
				i++;
			}
		}
		grid[row][col].type = rand.nextInt(Common.jewelTypes - 1) + 1;
		return;
	}

	public void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		for (int i = 0; i < Common.rowColLength; i++) {
			for (int j = 0; j < Common.rowColLength; j++) {

				if (grid[i][j].moveTo[0] > 0) {

					int x = i * Common.jewelWidth;
					int y = j * Common.jewelWidth;

					int swpx = grid[i][j].moveTo[1];
					int swpy = grid[i][j].moveTo[2];

					int type = grid[i][j].type;
					int mvtm = grid[i][j].moveTo[0];

					int mvToX = swpx * Common.jewelWidth;
					int mvToY = swpy * Common.jewelWidth;

					float offsetX = 0;
					float offsetY = 0;

					offsetX = ((x - mvToX) * ((float) mvtm / Common.animationDur));
					offsetY = ((y - mvToY) * ((float) mvtm / Common.animationDur));

					g2.drawImage(Common.jewelType[type],
							(int) (mvToX + offsetX), (int) (mvToY + offsetY),
							null);

				} else {

					if (!grid[i][j].selected) {
						g2.drawImage(Common.jewelType[grid[i][j].type], i
								* Common.jewelWidth, j * Common.jewelWidth,
								null);
					} else {
						g2.drawImage(Common.jewelType[grid[i][j].type], i
								* Common.jewelWidth,
								j * Common.jewelWidth - 10, null);
					}
				}
			}
		}
	}

	public void callTimer() {

		for (int i = 0; i < Common.rowColLength; i++) {
			for (int j = 0; j < Common.rowColLength; j++) {
				checkBroken(i, j);
			}
		}

		this.animating = false;

		for (int i = 0; i < Common.rowColLength; i++) {
			for (int j = 0; j < Common.rowColLength; j++) {
				if (grid[i][j].moveTo[0] != 0) {
					this.animating = true;
				}
			}
		}
		checkEmpty();
		// logic to check if a jewel is selected and then switch with another if
		// next to.
		for (int i = 0; i < Common.rowColLength; i++) {
			for (int j = 0; j < Common.rowColLength; j++) {
				if (grid[i][j].selected) {
					if (i + 1 < Common.rowColLength && grid[i + 1][j].swap) {
						this.switchJewel(i, j, i + 1, j);
					}
					if (i - 1 >= 0 && grid[i - 1][j].swap) {
						this.switchJewel(i, j, i - 1, j);
					}
					if (j + 1 < Common.rowColLength && grid[i][j + 1].swap) {
						this.switchJewel(i, j, i, j + 1);
					}
					if (j - 1 >= 0 && grid[i][j - 1].swap) {
						this.switchJewel(i, j, i, j - 1);
					}
				}
			}
		}

	}

	public void mouseClicked(MouseEvent e) {
		if (!animating) {
			int col = e.getPoint().x / Common.jewelWidth;
			int row = e.getPoint().y / Common.jewelWidth;
			if (!gridSelect) {
				gridSelect = true;
				grid[col][row].selected = true;
			} else {
				grid[col][row].swap = true;
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
