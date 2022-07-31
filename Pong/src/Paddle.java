import java.awt.Color;

public class Paddle extends Sprite {

	static final int PADDLE_WIDTH = 10;
	static final int PADDLE_HEIGHT = 100;
	static final Color PADDLE_COLOUR = Color.black;
	static final int DISTANCE_FROM_EDGE = 40;
	
	public Paddle(Player PLAYER, int PANEL_WIDTH, int PANEL_HEIGHT) {
		
		setWidth(PADDLE_WIDTH);
		setHeight(PADDLE_HEIGHT);
		setColour(PADDLE_COLOUR);
		if(PLAYER == Player.One) {
			setInitialPosition(DISTANCE_FROM_EDGE, PANEL_HEIGHT / 2 - (getHeight() / 2));
		} else if (PLAYER == Player.Two) {
			setInitialPosition(PANEL_WIDTH - (DISTANCE_FROM_EDGE + getWidth()), PANEL_HEIGHT / 2 - (getHeight() / 2));
		}
		resetToInitialPosition();
	}
	
}
