package Maze;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;

import javax.swing.JPanel;

import grid.Grid;
import grid.GridDisplay;
import grid.Location;

public class MazeDisplay extends JPanel{

	private Maze maze; 
	private GridDisplay display; 
	
	private int wallLength = 2;
	private int cellSize = 24;
	
	public MazeDisplay(Maze maze, int wallLength, int cellSize)
	{
		this.maze = maze; 
		this.wallLength = wallLength; 
		this.cellSize = cellSize; 
		
		this.display = new GridDisplay(maze.getNumRows(), maze.getNumCols(), cellSize, 0);
		
		this.setPreferredSize(new Dimension(display.width, display.height));
	}
	
	public void paintComponent(Graphics g)
	{
		Graphics2D g2 = (Graphics2D)g;
		
		g.setColor(Color.white);
		g.fillRect(0, 0, display.width, display.height);
		
		g.setColor(Color.black);
		display.drawGridlines(g2);
		paintWalls(g2);
	}
	
	private void paintWalls(Graphics2D g)
	{
		
		for (int r = 0; r < maze.getNumRows(); r++)
		{
			for (int c = 0; c < maze.getNumCols(); c++)
			{
				Location loc = new Location(r, c); 
				Point p = display.pointForLocation(loc);
				if (maze.isEmpty(loc))
				{
					g.setColor(Color.black);
					g.fillRect(p.x, p.y, cellSize, cellSize);
					continue; 
				}
				
				MazeCorner corner = maze.get(loc);
			
				switch (corner.type) {
				case END: 
					g.setColor(Color.red);
					break;
				case VISABLE: 
					g.setColor(Color.green);
					break;
				case VISITED:
					g.setColor(Color.blue);
					break;
				case TELEPORT:
					g.setColor(Color.yellow);
					break;
				default:
					g.setColor(Color.WHITE);
					break;
				}
				g.fillRect(p.x, p.y, cellSize, cellSize);
				
				for (int i = 0; i < 4; i++)
				{
					if (corner.hasWall(i))
					{
						g.setColor(Color.black);
						if (i == MazeCorner.UP)
							g.fillRect(p.x, p.y, cellSize, wallLength);
						else if (i == MazeCorner.RIGHT)
							g.fillRect(p.x + cellSize - wallLength, p.y, wallLength, cellSize);
						else if (i == MazeCorner.DOWN)
							g.fillRect(p.x, p.y + cellSize - wallLength, cellSize, wallLength);
						else if (i == MazeCorner.LEFT)
							g.fillRect(p.x, p.y, wallLength, cellSize);
					}
				}
				
				if (corner.numWalls() == 4)
				{
					g.setColor(Color.black);
					g.fillRect(p.x, p.y, cellSize, cellSize);
				}
				
			}
		}
	}
}
