import javax.swing.JPanel;
import java.awt.Color;
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
	private final static int BALL_MOVEMENT_SPEED = 2;

	public PongPanel(){
		
		setBackground(PANEL_COLOR);
		Timer timer = new Timer (TIMER_DELAY, this);
		timer.start();
	}
	

	@Override
	public void keyTyped(KeyEvent event) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent event) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(KeyEvent event) {
		// TODO Auto-generated method stub
		
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
        	moveObject(ball);
        	checkWallBounce();
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
		} else if(ball.getxPosition() >= getWidth() - ball.getWidth()) {
			ball.setxVelocity(-ball.getxVelocity());
		}
		if (ball.getyPosition() <= 0 || ball.getyPosition() >= getHeight() - ball.getHeight()) {
			ball.setyVelocity( -ball.getyVelocity());
		}
	}

}


