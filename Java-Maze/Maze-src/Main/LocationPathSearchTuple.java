package Main;

import Maze.Maze;
import grid.Location;

public class LocationPathSearchTuple {

	public final Location loc;
	public final Maze maze; 
	public LocationPathSearchTuple prev;
	
	public int depth = 0;

	public LocationPathSearchTuple(Location curr, LocationPathSearchTuple prev, Maze maze) {
		this.loc = curr;
		this.maze = maze; 
		this.prev = prev;
		this.depth = prev == null ? 0 : prev.depth + 1;
	}
	
	public String toString()
	{
		if (prev == null)
			return loc + ""; 
		return loc + " -> " + prev;
	}

}
