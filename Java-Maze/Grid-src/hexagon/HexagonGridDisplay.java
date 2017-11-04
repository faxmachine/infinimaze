package hexagon;

import grid.Location;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class HexagonGridDisplay {

	public int numRows = 7;
	public int numCols = 20;

	public int width = 1000;
	public int height = 500;

	public int size = 50;

	public int initX = 0;
	public int initY = 0;

	int x;
	int y;

	int[] shapePointsX;
	int[] shapePointsY;

	private List<Location> list;
	
	private boolean flat = true;

	public HexagonGridDisplay(List<Location> hexList) {
		this();

		this.list = hexList;
	}

	public HexagonGridDisplay() {
		init();

	}

	public void init() {
		int x1 = initX;
		int x2 = initX + size / 2;
		int x3 = initX + size * 3 / 2;
		int x4 = initX + size * 2;

		int y1 = initY;
		int y2 = initY + (int) (size * Math.sqrt(3) / 2);
		int y3 = initY + (int) (size * Math.sqrt(3));

		shapePointsX = new int[] { x1, x2, x3, x4, x3, x2 };
		shapePointsY = new int[] { y2, y1, y1, y2, y3, y3 };

		x = 3 * size;
		y = (int) (size * Math.sqrt(3));

	}
	
	public Location getLocation(int hex)
	{
		return list.get(hex);
	}

	public void paintComponent(Graphics g) {

		Graphics2D g2 = (Graphics2D) g;

		// drawLines(g2);
		g2.setColor(Color.black);
		drawLocations(g2, list);
	}

	

	public void drawLocations(Graphics2D g, List<Location> locations) {

		for (Location loc : locations) {
			Point center = pointForHex(new Hex(loc.getRow(), loc.getCol()), flat);
			drawHex(g, center, size, flat);
		}
	}
	
	public int getWidth(boolean flat) 
	{
		if (list.size() < 1)
			return 0; 
		int x_min = Integer.MAX_VALUE, x_max = Integer.MIN_VALUE;
		for (Location hex : list) {
			Point center = pointForHex(hex, flat);
			Point[] corner = hexCorners(center, size, flat);
			if (corner[5].x < x_min)
				x_min = corner[5].x;
			if (corner[2].x > x_max)
				x_max = corner[2].x; 
		}
		return x_max - x_min;
		
		
	}
	
	public int getHeight(boolean flat) 
	{
		if (list.size() < 1)
			return 0; 
		int y_min = Integer.MAX_VALUE, y_max = Integer.MIN_VALUE;
		for (Location hex : list) {
			Point center = pointForHex(hex, flat);
			Point[] corner = hexCorners(center, size, flat);
			if (corner[5].y < y_min)
				y_min = corner[5].y;
			if (corner[2].y > y_max)
				y_max = corner[2].y; 
		}
		return y_max - y_min;
	}

	public Hex hexForPoint(Point p, boolean flat) {
		int x = p.x - initX;
		int y = p.y - initY;

		int q, r;
		if (flat) {
			q = (int)(x * 2.0 / 3 / size);
			r = (int)((-x / 3.0 + Math.sqrt(3) / 3 * y) / size);
		}
		else {
			q = (int)((x * Math.sqrt(3)/3.0 - y / 3.0) / size);
			r = (int)(y * 2.0/3 / size);
		}
		return new Hex(q, r).round();

	}
	
	public Point pointForHex(Location hex, boolean flat) {
		int q = hex.getRow();
		int r = hex.getCol();
		
		int x, y;
		if (flat) {
			x = (int)(size * 3.0/2.0 * q);
			y = (int)(size * Math.sqrt(3) * (r + q/2.0));
		}
		else {
			x = (int)(size * Math.sqrt(3) * (q + r/2.0));
			y = (int)(size * 3.0/2.0 * r);
		}
		return new Point(x + initX, y + initY);
	}
	
	public Point pointForHex(Hex hex, boolean flat) {
		int q = hex.q;
		int r = hex.r;
		
		int x, y;
		if (flat) {
			x = (int)(size * 3.0/2.0 * q);
			y = (int)(size * Math.sqrt(3) * (r + q/2.0));
		}
		else {
			x = (int)(size * Math.sqrt(3) * (q + r/2.0));
			y = (int)(size * 3.0/2.0 * r);
		}
		return new Point(x + initX, y + initY);
	}
	

	public Point[] hexCorners(Point center, int size, boolean flat)
	{
		Point[] hexCorners = new Point[6];
		for (int i = 0; i < 6; i++) 
		{
			hexCorners[i] = hexCorner(center, size, i, flat);
		}
		return hexCorners; 
	}

	public Point hexCorner(Point center, int size, int i, boolean flat) {
		double angleDegree = i * 60 + (flat ? 0 : 30);
		double angleRad = Math.PI / 180 * angleDegree;
		return new Point((int) (center.x + size * Math.cos(angleRad)), (int) (center.y + size * Math.sin(angleRad)));
	}

	public void drawHex(Graphics2D g, Point center, int size, boolean flat) {
		Point corner = hexCorner(center, size, 0, flat);
		for (int i = 1; i <= 6; i++) {
			Point nextCorner = hexCorner(center, size, i, flat);
			g.drawLine(corner.x, corner.y, nextCorner.x, nextCorner.y);
			corner = nextCorner;
		}
	}


	public void drawGrid(Graphics2D g, int row, int col, Point origin, int size, boolean flat, boolean evenLayOut) {
		int width, height;
		int horz, vert;
		if (flat) {
			width = size * 2;
			horz = width * 3 / 4;
			height = (int) (Math.sqrt(3) / 2) * width;
			vert = height;
		} else {
			height = size * 2;
			vert = height * 3 / 4;
			width = (int) (Math.sqrt(3) / 2) * height;
			horz = width;
		}

		
	}

	public static void main(String args[]) {
		
		List<Location> hexList = new ArrayList();

		hexList.add(new Location(3, -1));
		hexList.add(new Location(4, -1));
		hexList.add(new Location(5, -1));
		
		hexList.add(new Location(2, 0));
		hexList.add(new Location(3, 0));
		hexList.add(new Location(4, 0));
		hexList.add(new Location(5, 0));
		
		hexList.add(new Location(1, 1));
		hexList.add(new Location(2, 1));
		hexList.add(new Location(3, 1));
		hexList.add(new Location(4, 1));
		hexList.add(new Location(5, 1));
		
		hexList.add(new Location(1, 2));
		hexList.add(new Location(2, 2));
		hexList.add(new Location(3, 2));
		hexList.add(new Location(4, 2));

		hexList.add(new Location(1, 3));
		hexList.add(new Location(2, 3));
		hexList.add(new Location(3, 3));
	

		HexagonGridDisplay hexDisplay = new HexagonGridDisplay(hexList);

		JFrame window = new JFrame();
		//window.setContentPane();
		window.setDefaultCloseOperation(3);
		window.setResizable(false);
		window.pack();
		window.setVisible(true);
	}

	private class Hex {
		int q, r;

		public Hex(int q, int r) {
			this.q = q;
			this.r = r;
		}

		public Cube toCube() {
			int x = q;
			int z = r;
			int y = -x - z;
			return new Cube(x, y, z);
		}
		
		public Hex round() {
			return toCube().round().toHex();
		}
		
		public String toString() {
			return "(" + q + ", " + r + ")"; 
		}
	}

	private class Cube {
		int x, y, z;

		public Cube(int x, int y, int z) {
			this.x = x;
			this.y = y;
			this.z = z;
		}

		public Hex toHex() {
			int q = x;
			int r = z;
			return new Hex(q, r);
		}

		public Cube round() {
			
			int rx = Math.round(x);
			int ry = Math.round(y);
			int rz = Math.round(z);

			int x_diff = Math.abs(rx - x);
			int y_diff = Math.abs(ry - y);
			int z_diff = Math.abs(rz - z);

			if (x_diff > y_diff && x_diff > z_diff) 
				rx = -ry-rz;
			else if (y_diff > z_diff)
				ry = -rx-rz;
			else
			    rz = -rx-ry;

			return new Cube(rx, ry, rz);
		}
		
		public String toString() {
			return "(" + x + ", " + y + ", " + z + ")"; 
		}

	}

}
