package Maze;

import Main.LocationPathSearchTuple;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import grid.Location;

public class TeleMaze {
	
	public static final int NUM_TELE_SPOTS = 4; 
	
	public String name; 
	
	public final Maze maze; 
	public final int depth; 
	public boolean expanded = false;
	
	public Map<Location, TeleMaze> teleSpots = new HashMap(); 
	
	public TeleMaze(int row, int col, long seed)
	{	
		this.maze = new Maze(row, col, seed); 
		this.depth = 0; 
	}
	
	public TeleMaze(int row, int col)
	{	
		this.maze = new Maze(row, col); 
		this.depth = 0; 
	}
	
	public TeleMaze(Maze maze)
	{
		this.maze = maze;
		this.depth = 0; 
	}
	
	private TeleMaze(Location loc, TeleMaze fromMaze)
	{
		this.maze = new Maze(fromMaze.maze.getNumRows(), fromMaze.maze.getNumCols());
		
		this.depth = fromMaze.depth + 1;
		
		teleSpots.put(loc, fromMaze);
		joinMazesAtLocation(fromMaze.maze, maze, loc);
	}
	
	public void expand(int numTeleSpots, Location startLoc)
	{
		List<Location> endLocations = maze.getEndLocations(); 
		endLocations.remove(startLoc);
		
		if (numTeleSpots > endLocations.size())
			numTeleSpots = endLocations.size();
		
		while (teleSpots.size() < numTeleSpots)
		{
			int randIndex = maze.random.nextInt(endLocations.size());
			Location loc = endLocations.remove(randIndex);
			if (loc.getDistanceTo(startLoc) < 5)
				continue; 
			boolean close = false; 
			for (Location teles: teleSpots.keySet()) {
				if (loc.getDistanceTo(teles) < 5) {	
					close = true;
					break; 
				}
			}
			if (close)
				continue;
			
			teleSpots.put(loc, new TeleMaze(loc, this));		
		}
		expanded = true; 
	}
	

	private void joinMazesAtLocation(Maze originMaze, Maze teleMaze, Location teleport)
	{
		List<Location> visibleLocationsFromOriginMaze = originMaze.getAccessableLocationsInRange(teleport, 5); 
		for (Location loc: visibleLocationsFromOriginMaze)
		{
			teleMaze.put(loc, new MazeCorner(loc)); 
		}
		teleMaze.generateMaze(teleport);
		
		originMaze.get(teleport).type = MazeCorner.CornerType.TELEPORT;
		teleMaze.get(teleport).type = MazeCorner.CornerType.TELEPORT;
			
	}
	
	public String toString()
	{
		return name;
	}
	
	public Map<Location, TeleMaze> getMap()
	{
		return this.teleSpots;
	}
	
}
