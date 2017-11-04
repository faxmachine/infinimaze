package grid;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;

public class GridDisplay {

	public static final int MIN_CELL_SIZE = 12;
	public static final int DEFAULT_CELL_SIZE = 48;
	
	public final int width;
	public final int height; 
	
	private int cellSize = 12;
	private int initX = 0;
	private int initY = 0;
	private int numRows;
	private int numCols;
	private int originRow;
	private int originCol;
	private int lineLength = 1;
	
	private Color lineColor; 
	
	public GridDisplay(int numRows, int numCols, int cellSize, int lineLength)
	{
		this.numRows = numRows; 
		this.numCols = numCols; 
		this.cellSize = cellSize; 
		this.lineLength = lineLength;
		
		this.width = lineLength + (numCols * (cellSize + lineLength));
		this.height = lineLength + (numRows * (cellSize + lineLength));
	}
	
	public void drawGridlines(Graphics2D g) {
		g.setColor(lineColor);
		int boxLength = this.cellSize + lineLength;
		for (int y = initY; y <= initY + height; y += boxLength) {
			g.fillRect(initX, y, width, lineLength);
		}
		for (int x = initX; x <= initX + width; x += boxLength) {
			g.fillRect(x, initY, lineLength, height);
		}
	}
	
	public Location locationForPoint(Point p) {
		return new Location(yCoordToRow(p.y), xCoordToCol(p.x));
	}
	
	public Point pointForLocation(Location loc) {
		int x = colToXCoord(loc.getCol()); // + this.cellSize / 2;
		int y = rowToYCoord(loc.getRow()); // + this.cellSize / 2;
		return new Point(x, y);
	}
	
	private int xCoordToCol(int xCoord) {
		return (xCoord - lineLength - initX) / (this.cellSize + this.lineLength)
				+ this.originCol;
	}

	private int yCoordToRow(int yCoord) {
		return (yCoord - lineLength - initY) / (this.cellSize + this.lineLength)
				+ this.originRow;
	}

	private int colToXCoord(int col) {
		return (col - this.originCol)
				* (this.cellSize + this.lineLength) + lineLength + initX;
	}

	private int rowToYCoord(int row) {
		return (row - this.originRow) 
				* (this.cellSize + this.lineLength) + lineLength + initY;
	}
	
	public void recenter(Location loc) {
		
		originRow = loc.getRow();
		originCol = loc.getCol();
		initX = this.colToXCoord(loc.getCol()) - lineLength;
		initY = this.rowToYCoord(loc.getRow()) - lineLength;
	}

}
