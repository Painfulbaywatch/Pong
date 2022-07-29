import java.awt.Color;

public class Ball extends Sprite{
	
	private final static Color colour = Color.white;
	private final static int width = 25;
	private final static int height = 25;
	
	public Ball(int panelWidth, int panelHeight) {
		setColour(colour);
		setHeight(height);
		setWidth(width);
		
		setInitialPosition(panelWidth / 2 - (getWidth() / 2), panelHeight / 2 - (getHeight() / 2));
		resetToInitialPosition();
	}

}
