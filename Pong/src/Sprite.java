import java.awt.Color;
import java.awt.Rectangle;

public class Sprite {
	
	private int xPosition, yPosition;
	private int xVelocity, yVelocity;
	private int width, height;
	private Color colour;
	private int initialxPosition;
	private int initialyPosition;
	
	public int getxPosition() {
		return xPosition;
	}
	
	public void setxPosition(int newxPosition) {
		this.xPosition = newxPosition;
	}
	
	public void setxPosition(int newxPosition, int panelWidth) {
		this.xPosition = newxPosition;
		if(xPosition + width > panelWidth) {
			xPosition = panelWidth - width;
		} else if (xPosition < 0){
			xPosition = 0;
		}
	}
	
	public int getyPosition() {
		return yPosition;
	}
	
	public void setyPosition(int newyPosition) {
		this.yPosition = newyPosition;
	}
	
	public void setyPosition(int newyPosition, int panelHeight) {
		this.yPosition = newyPosition;
		if (yPosition + height > panelHeight) {
			yPosition = panelHeight - height;
		} else if (yPosition < 0) {
			yPosition = 0;
		}
	}
	
	public int getxVelocity() {
		return xVelocity;
	}
	
	public void setxVelocity(int newxVelocity) {
		this.xVelocity = newxVelocity;
	}
	
	public int getyVelocity() {
		return yVelocity;
	}
	
	public void setyVelocity(int newyVelocity) {
		this.yVelocity = newyVelocity;
	}
	
	public int getWidth() {
		return width;
	}
	
	public void setWidth(int width) {
		this.width = width;
	}
	
	public int getHeight() {
		return height;
	}
	
	public void setHeight(int height) {
		this.height = height;
	}
	
	public Color getColour() {
		return colour;
	}
	
	public void setColour(Color newColour) {
		this.colour = newColour;
	}
	
	public void setInitialPosition(int newInitialxPosition, int newInitialyPosition) {
		initialxPosition = newInitialxPosition;
		initialyPosition = newInitialyPosition;
	}
	
	public void resetToInitialPosition() {
		setxPosition(initialxPosition);
		setyPosition(initialyPosition);
	}
	
	public Rectangle getRectangle() {
		return new Rectangle(getxPosition(), getyPosition(), getWidth(), getHeight());
	}

}
