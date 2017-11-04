package display;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Image;

public class Display {
	
	protected int width = 0; 
	protected int height = 0;
	protected int x = 0;
	protected int y = 0;
	
	private Image image;
	
	public Display(Image image) {
		this.image = image;
		setDimension(image.getWidth(null), image.getHeight(null));
	}
	
	public Display() {
		this.image = null;
	}

	public void draw(Graphics2D g, int x, int y, int width, int height) {
		g.drawImage(image, x, y, width, height, null);
	}
	
	public void draw(Graphics2D g) {
		g.drawImage(image, x, y, null);
	}
	
	public Dimension getDimension() {
		return new Dimension(width, height);
	}
	
	public void setDimension(Dimension dim) {
		setDimension(dim.width, dim.height);
	}
	
	public void setDimension(int width, int height) {
		this.width = width;
		this.height = height;
	}
}
