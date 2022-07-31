import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.Timer;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.BasicStroke;

public class PongPanel extends JPanel implements ActionListener, KeyListener {
	
	private final static Color PANEL_COLOR = Color.BLACK;
	private final static int TIMER_DELAY = 5;
	Ball ball;
	GameState gameState = GameState.Initialising;
	Paddle paddle1;
	Paddle paddle2;
	private final static int BALL_MOVEMENT_SPEED = 6;
	private final static int POINTS_TO_WIN = 3;
	int player1score = 0, player2score = 0;
	Player gameWinner;
	private final static int XPADDING = 100;
	private final static int YPADDING = 100;
	private final static int FONT_SIZE = 50;
	private final static String SCORE_FONT_FAMILY = "Serif";
	private final static String WIN_TEXT = "WIN!";

	public PongPanel(){
		
		setBackground(PANEL_COLOR);
		Timer timer = new Timer (TIMER_DELAY, this);
		timer.start();
		addKeyListener(this);
		setFocusable(true);
	}
	

	@Override
	public void keyTyped(KeyEvent event) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent event) {
		// TODO Auto-generated method stub
		if(event.getKeyCode() == KeyEvent.VK_UP) {
			paddle2.setyVelocity(-5);
		} else if(event.getKeyCode() == KeyEvent.VK_DOWN) {
			paddle2.setyVelocity(5);
		}
		if(event.getKeyCode() == KeyEvent.VK_W) {
			paddle1.setyVelocity(-5);
		} else if(event.getKeyCode() == KeyEvent.VK_S) {
			paddle1.setyVelocity(5);
		}
	}

	@Override
	public void keyReleased(KeyEvent event) {
		// TODO Auto-generated method stub
		if(event.getKeyCode() == KeyEvent.VK_UP || event.getKeyCode() == KeyEvent.VK_DOWN) {
			paddle2.setyVelocity(0);
		}
		if(event.getKeyCode() == KeyEvent.VK_W || event.getKeyCode() == KeyEvent.VK_S) {
			paddle1.setyVelocity(0);
		}
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		update();
		repaint();
		
	}
	
	private void update() {
		switch(gameState) {
        case Initialising: {
            createObjects();
           gameState = GameState.Playing;
           ball.setxVelocity(BALL_MOVEMENT_SPEED);
           ball.setyVelocity(BALL_MOVEMENT_SPEED);
           
            break;
        }
        case Playing: {
        	moveObject(paddle1);
        	moveObject(paddle2);
        	moveObject(ball);
        	checkWallBounce();
        	checkPaddleBounce();
        	checkWin();
            break;
       }
       case GameOver: {
           break;
       }
   }
		
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		paintDottedLine(g);
		if(gameState != GameState.Initialising) {
			paintSprite(g, ball);
			paintSprite(g, paddle1);
			paintSprite(g, paddle2);
			paintScores(g);
		}
		if(gameState == GameState.GameOver) {
			paintWin(g, gameWinner);
		}
	}
	
	private void paintDottedLine(Graphics g) {
		Graphics2D g2d = (Graphics2D) g.create();
		Stroke dashed = new BasicStroke(3, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[] {9}, 0);
		g2d.setStroke(dashed);
		g2d.setPaint(Color.white);
		g2d.drawLine(getWidth() / 2,  0,  getWidth() / 2,  getHeight());
		g2d.dispose();
	}
	
	public void createObjects() {
		ball = new Ball(getWidth(), getHeight());
		paddle1 = new Paddle(Player.One, getWidth(), getHeight());
		paddle2 = new Paddle(Player.Two, getWidth(), getHeight());
	}
	
	public void paintSprite(Graphics g, Sprite sprite) {
		g.setColor(sprite.getColour());
		g.fillRect(sprite.getxPosition(), sprite.getyPosition(), sprite.getWidth(), sprite.getHeight());
	}
	
	private void moveObject(Sprite sprite) {
		sprite.setxPosition(sprite.getxPosition() + sprite.getxVelocity(), getWidth());
		sprite.setyPosition(sprite.getyPosition() + sprite.getyVelocity(), getHeight());
	}
	
	private void checkWallBounce() {
		if (ball.getxPosition() <= 0) {
			ball.setxVelocity( -ball.getxVelocity());
			addScore(Player.Two);
			resetBall();
		} else if(ball.getxPosition() >= getWidth() - ball.getWidth()) {
			ball.setxVelocity(-ball.getxVelocity());
			addScore(Player.One);
			resetBall();
		}
		if (ball.getyPosition() <= 0 || ball.getyPosition() >= getHeight() - ball.getHeight()) {
			ball.setyVelocity( -ball.getyVelocity());
		}
	}
	
	private void resetBall() {
		ball.resetToInitialPosition();
	}
	
	private void checkPaddleBounce() {
		if(ball.getxVelocity() < 0 && ball.getRectangle().intersects(paddle1.getRectangle())) {
			ball.setxVelocity(BALL_MOVEMENT_SPEED);
		}
		if(ball.getxVelocity() > 0 && ball.getRectangle().intersects(paddle2.getRectangle())) {
			ball.setxVelocity(-BALL_MOVEMENT_SPEED);
		}
	}
	
	private void addScore(Player player) {
		if (player == Player.One) {
			player1score++;
		}
		if (player == Player.Two) {
			player2score++;
		}
	}
	
	private void checkWin() {
		if(player1score == POINTS_TO_WIN) {
			gameWinner = Player.One;
			gameState = GameState.GameOver;
		}
		if(player2score == POINTS_TO_WIN) {
			gameWinner = Player.Two;
			gameState = GameState.GameOver;
		}
	}
	
	private void paintScores(Graphics g) {
		Font scoreFont = new Font(SCORE_FONT_FAMILY, Font.BOLD, FONT_SIZE);
		String leftScore = Integer.toString(player1score);
		String rightScore = Integer.toString(player2score);
		g.setFont(scoreFont);
		g.drawString(leftScore, XPADDING, YPADDING);
		g.drawString(rightScore, getWidth()-XPADDING, YPADDING);
	}
	
	private void paintWin(Graphics g, Player winner) {
		Font scoreFont = new Font(SCORE_FONT_FAMILY, Font.BOLD, FONT_SIZE);
		g.setFont(scoreFont);
		if (winner == Player.One) {
		g.drawString(WIN_TEXT, getWidth() / 4, (getHeight() / 2) + FONT_SIZE / 2);
		}
		if (winner == Player.Two) {
		g.drawString(WIN_TEXT, getWidth() / 4 * 3, (getHeight() / 2) + FONT_SIZE / 2);
		}
	}

}


