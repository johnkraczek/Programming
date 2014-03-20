package game;

import java.awt.EventQueue;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Game extends JPanel {
	private static final long serialVersionUID = 1835291333729651376L;

	private final Grid grid = new Grid();
	private Hud hud = new Hud();
	private GameBG bg = new GameBG();

	public Game() {
		this.setLayout(null);

		add(grid);
		add(hud);
		add(bg);

		Timer timer = new Timer();
		TimerTask task = new TimerTask() {
			public void run() {
				Random rand = new Random();
				int col = rand.nextInt(Common.rowColLength);
				int row = rand.nextInt(Common.rowColLength);
				int jewel = rand.nextInt(Common.jewelTypes);

				grid.SwitchJewel(row, col, jewel);
				repaint();
			}
		};
		timer.schedule(task, 1, 10);
	}

	public static void main(String[] args) {

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				JFrame frame = new JFrame();
				frame.setTitle("MyMouse");
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.setLocationByPlatform(true);
				frame.add(new Game());
				frame.setSize(Common.frameWidth, Common.frameHeight);
				frame.setVisible(true);
			}
		});

	}
}
