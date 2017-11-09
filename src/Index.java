import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.util.Random;

import skltl.GClock;
import skltl.Game;
import skltl.media.ContentManager;

public class Index extends Game implements KeyListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2939994154574407894L;
	public final int width = 1280 / 2, height = 720 / 2;
	boolean gliderAdded = false;
	BufferedImage ship;
	boolean[][] map;

	public Index(String title) {
		super(title);

	}

	@Override
	public void loadContent(ContentManager content) {
		ship = (BufferedImage) content.loadImage("res/Ship_Spider.png");
	}

	@Override
	public void unloadContent(ContentManager content) {
		content.unload();
	}

	@Override
	public void initialize() {
		this.addKeyListener(this);
		this.setTargetFPS(8);
		setWidth(width * 2);
		setHeight(height * 2);
		this.willUpdateOnPause = false;
		map = new boolean[width][height];
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[0].length; j++) {
				map[i][j] = false;
			}
		}
		addPatternFromPicture(ship, 500, 300);
		// infiniteGrowth(610,325);
		this.isPaused = true;
	}

	// TODO does this work tho?
	private void addPatternFromPicture(BufferedImage img, int x, int y) {
		for (int a = 0; a < img.getWidth(); a++) {
			for (int b = 0; b < img.getHeight(); b++) {
				map[x + a][y + b] = (img.getRGB(a, b) != Color.white.getRGB());
			}
		}
	}

	private void randomMap() {
		Random r = new Random();
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[0].length; j++) {
				map[i][j] = r.nextDouble() > .8;
			}
		}
	}

	private void addGlider(int x, int y) {
		map[x + 1][y] = true;
		map[x + 2][y + 1] = true;
		map[x][y + 2] = true;
		map[x + 1][y + 2] = true;
		map[x + 2][y + 2] = true;
	}

	private void addGliderGun(int x, int y) {
		/*
		 * 000000000000000000000000100000000000 000000000000000000000010100000000000
		 * 000000000000110000001100000000000011 000000000001000100001100000000000011
		 * 110000000010000010001100000000000000 110000000010001011000010100000000000
		 * 000000000010000010000000100000000000 000000000001000100000000000000000000
		 * 000000000000110000000000000000000000
		 */
		map[x][y + 4] = true;
		map[x][y + 5] = true;
		map[x + 1][y + 4] = true;
		map[x + 1][y + 5] = true;

		map[x + 13][y + 2] = true;
		map[x + 12][y + 2] = true;
		map[x + 11][y + 3] = true;
		map[x + 10][y + 4] = true;
		map[x + 10][y + 5] = true;
		map[x + 10][y + 6] = true;
		map[x + 11][y + 7] = true;
		map[x + 12][y + 8] = true;
		map[x + 13][y + 8] = true;

		map[x + 14][y + 5] = true;
		map[x + 15][y + 3] = true;
		map[x + 15][y + 7] = true;
		map[x + 16][y + 4] = true;
		map[x + 16][y + 5] = true;
		map[x + 16][y + 6] = true;
		map[x + 17][y + 5] = true;

		map[x + 20][y + 2] = true;
		map[x + 20][y + 3] = true;
		map[x + 20][y + 4] = true;
		map[x + 21][y + 2] = true;
		map[x + 21][y + 3] = true;
		map[x + 21][y + 4] = true;
		map[x + 22][y + 1] = true;
		map[x + 22][y + 5] = true;

		map[x + 24][y] = true;
		map[x + 24][y + 1] = true;
		map[x + 24][y + 5] = true;
		map[x + 24][y + 6] = true;

		map[x + 34][y + 2] = true;
		map[x + 34][y + 3] = true;
		map[x + 35][y + 2] = true;
		map[x + 35][y + 3] = true;
	}

	private void infiniteGrowth(int x, int y) {
		map[x][y] = true;
		map[x][y + 1] = true;
		map[x + 1][y] = true;
		map[x + 2][y] = true;
		map[x + 4][y] = true;
		map[x][y + 4] = true;
		map[x + 1][y + 3] = true;
		map[x + 2][y + 3] = true;
		map[x + 2][y + 4] = true;
		map[x + 3][y + 2] = true;
		map[x + 4][y + 2] = true;
		map[x + 4][y + 3] = true;
		map[x + 4][y + 4] = true;

	}

	@Override
	public void update(GClock clock) {
		/*
		 * RULES 1) Any live cell with fewer than two live neighbours dies, as if caused
		 * by underpopulation. 2) Any live cell with two or three live neighbours lives
		 * on to the next generation. 3) Any live cell with more than three live
		 * neighbours dies, as if by overpopulation. 4) Any dead cell with exactly three
		 * live neighbours becomes a live cell, as if by reproduction.
		 * 
		 * neighbors = the 8 cells surrounding any other cell.
		 * 
		 * 012 3c4 567
		 * 
		 */
		// if statement to disable update when checking a pattern's initial state
		if (true) {
			boolean[][] newMap = new boolean[width][height];
			for (int i = 0; i < map.length; i++) {

				int a = i == 0 ? map.length - 1 : i - 1;
				int b = i;
				int c = i == map.length - 1 ? 0 : i + 1;
				for (int j = 0; j < map[0].length; j++) {
					int x = j == 0 ? map[i].length - 1 : j - 1;
					int y = j;
					int z = j == map[i].length - 1 ? 0 : j + 1;

					boolean[] n = new boolean[8];
					n[0] = map[a][x];
					n[1] = map[b][x];
					n[2] = map[c][x];
					n[3] = map[a][y];
					n[4] = map[c][y];
					n[5] = map[a][z];
					n[6] = map[b][z];
					n[7] = map[c][z];

					int numAlive = 0;
					for (boolean nbr : n) {
						numAlive += nbr ? 1 : 0;
					}
					// dunno if this is the correct implementation of the rules but it looks metal
					// af with a small starting population
					// re: it's not the correct rules.
					// if(numAlive<2 || numAlive>3)
					// newMap[i][j]=false;
					// else
					// newMap[i][j]=true;
					if (numAlive < 2 || numAlive > 3)
						newMap[i][j] = false;
					else if ((numAlive == 2 || numAlive == 3) && map[i][j])
						newMap[i][j] = true;
					else if (numAlive == 3 && !map[i][j])
						newMap[i][j] = true;

				}

			}
			map = newMap;
		}
	}

	@Override
	public void draw(Graphics2D g, GClock clock) {

		g.setColor(Color.white);
		g.fillRect(0, 0, getWidth(), getHeight());
		BufferedImage img = new BufferedImage(width * 2, height * 2, BufferedImage.TYPE_INT_RGB);
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[0].length; j++) {
				try {
					img.setRGB(i * 2, j * 2, map[i][j] ? Color.black.getRGB() : Color.white.getRGB());
					img.setRGB(i * 2 + 1, j * 2, map[i][j] ? Color.black.getRGB() : Color.white.getRGB());
					img.setRGB(i * 2, j * 2 + 1, map[i][j] ? Color.black.getRGB() : Color.white.getRGB());
					img.setRGB(i * 2 + 1, j * 2 + 1, map[i][j] ? Color.black.getRGB() : Color.white.getRGB());
				} catch (ArrayIndexOutOfBoundsException e) {
					System.err.println("i: " + i + ", j: " + j);
					System.err.println(map.length);
					System.err.println(map[i].length);
					e.printStackTrace();
					System.exit(1);
				}
			}
		}
		g.drawImage(img, 0, 0, null);

	}

	public static void main(String[] args) {

		new Index("Conway's Game of Life");
	}

	@Override
	public void keyPressed(KeyEvent e) {

	}

	@Override
	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			//exits the simulation
			this.stop();
		}
		if (e.getKeyCode() == KeyEvent.VK_SPACE) {
			//pauses/unpauses the simulation
			this.isPaused ^= true;
		}
		if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			//speeds up the simulation
			this.setTargetFPS(this.getTargetFPS() + 1f);
		}
		if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			//slows down the simulation
			this.setTargetFPS(this.getTargetFPS() - 1f);
		}

	}

	@Override
	public void keyTyped(KeyEvent e) {

	}

}
