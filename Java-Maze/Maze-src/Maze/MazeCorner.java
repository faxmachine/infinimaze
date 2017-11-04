package Maze;
import grid.Location;

public class MazeCorner {

	public static final int UP = 0; 
	public static final int RIGHT = 1;
	public static final int DOWN = 2;
	public static final int LEFT = 3; 
	
	public enum CornerType {
		PATH, END, VISABLE, VISITED, TELEPORT; 
	}
	
	public CornerType  type = CornerType.PATH; 
	
	private Location location;  
	
	int walls[] = new int[4];
	
	public MazeCorner(Location location)
	{
		this.location = location;
		
		addAllWalls();
	}
	
	public MazeCorner(Location locaction, int num)
	{
		this.location = location;
		for (int i = 3; i >= 0; i--)
		{
			int temp = (int)Math.pow(2, i);
			if (num > temp) 
			{
				addWall(i);
				num -= temp; 
			}
		}
	}
	
	public void addWall(int side) 
	{
		walls[side] = 1; 
	}
	
	public void addAllWalls()
	{
		for (int i = 0; i < 4; i++)
		{
			addWall(i);
		}
	}
	
	public void removeWall(int side)
	{
		walls[side] = 0;
	}
	
	public boolean hasWall(int side)
	{
		return walls[side] > 0; 
	}
	
	public int numWalls()
	{
		int count = 0; 
		for (int i = 0; i < 4; i++)
		{
			if (hasWall(i))
				count++;
		}
		return count; 
	}
	
	public int getValue()
	{
		int num = 0; 
		for (int i = 0; i < 4; i++)
		{
			num += hasWall(i) ? Math.pow(2, i) : 0;
		}
		return num; 
	}
	
	
}
