package display;

import java.awt.Graphics2D;
import java.awt.Image;
import java.util.HashMap;
import java.util.Map;

public class DisplayMap<E> {
	
	private Map<E, Display> map = new HashMap();
	
	public void draw(Graphics2D g, E toDraw, int x, int y, int width, int height) {
		map.get(toDraw).draw(g, x, y, width, height);
	}
	
	public void put(E key, Display value) {
		map.put(key, value);
	}
	
	public void put(E key, Image displayImage) {
		this.put(key, new Display(displayImage));
	}
}
